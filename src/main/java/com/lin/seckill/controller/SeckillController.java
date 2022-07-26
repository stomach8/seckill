package com.lin.seckill.controller;

import com.lin.seckill.entity.SeckillMessage;
import com.lin.seckill.entity.SeckillOrder;
import com.lin.seckill.entity.User;
import com.lin.seckill.rabbitmq.MQSender;
import com.lin.seckill.service.IGoodsService;
import com.lin.seckill.service.IOrderService;
import com.lin.seckill.service.ISeckillOrderService;
import com.lin.seckill.utils.JsonUtil;
import com.lin.seckill.vo.GoodsVO;
import com.lin.seckill.vo.RespBean;
import com.lin.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>秒杀</p>
 *
 * @author : star
 * @date : 2022/7/18 22:22
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MQSender mqSender;

    private Map<Long, Boolean> emptyStockMap = new HashMap<>();

    /**
     * win10 qps: 1501
     * linux qps:
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/doSeckill")
    public RespBean doSeckill(Model model, User user, Long goodsId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //内存标记， 减少 redis 的访问
        if (emptyStockMap.get(goodsId)) {
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        //判断当前用户是否已经秒杀过
        SeckillOrder seckillOrder = (SeckillOrder) valueOperations.get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }

        //redis预减库存
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        if (stock == 0) {
            emptyStockMap.put(goodsId, true);
            valueOperations.increment("seckillGoods:" + goodsId);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        //下订单
        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessage));
        return RespBean.success(0);


//        //判断库存
//        GoodsVO goods = goodsService.findGoodsVOByGoodsId(goodsId);
//        if (goods.getStockCount() < 1) {
//            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK);
//            return "secKillFail";
//        }
//        //判断是否重复抢购
////        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
//        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
//        if (seckillOrder != null) {
//            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR);
//            return "secKillFail";
//        }
//        Order order = orderService.seckill(user, goods);
//        model.addAttribute("order", order);
//        model.addAttribute("goods", goods);
    }

    /**
     * 获取秒杀结果
     * orderId: 成功， -1：秒杀失败，0：排队中
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public RespBean getResult(User user, Long goodsId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        Long orderId = seckillOrderService.getResult(user, goodsId);
        return RespBean.success(orderId);
    }


    /**
     * 系统初始化，将商品库存数量加载到 Redis
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> list = goodsService.findGoodsVO();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(goodsVO -> {
            redisTemplate.opsForValue().set("seckillGoods:" + goodsVO.getId(), goodsVO.getStockCount());
            emptyStockMap.put(goodsVO.getId(), false);
        });

    }
}
