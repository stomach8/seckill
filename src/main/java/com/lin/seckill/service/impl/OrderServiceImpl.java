package com.lin.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.seckill.entity.Order;
import com.lin.seckill.entity.SeckillGoods;
import com.lin.seckill.entity.SeckillOrder;
import com.lin.seckill.entity.User;
import com.lin.seckill.mapper.OrderMapper;
import com.lin.seckill.service.IGoodsService;
import com.lin.seckill.service.IOrderService;
import com.lin.seckill.service.ISeckillGoodsService;
import com.lin.seckill.service.ISeckillOrderService;
import com.lin.seckill.utils.MD5Util;
import com.lin.seckill.utils.UUIDUtil;
import com.lin.seckill.vo.GoodsVO;
import com.lin.seckill.vo.OrderDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author star
 * @since 2022-07-15
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * <p>秒杀订单</p>
     * <p style="text-decoration:line-through">这里如果 if 不加括号就会出现并发问题</p>
     *
     * @param user  用户
     * @param goods 商品
     * @return 订单
     */
    @Transactional
    @Override
    public Order seckill(User user, GoodsVO goods) {
        ValueOperations valueOperations = redisTemplate.opsForValue();

        //秒杀减库存

        /**
         * 这里 - 1 再执行update的话，是可能出现这样的情况，
         * 两个请求都到同时到 - 1，操作，这样这两个请求就相当于一个 - 1 操作失效
         */
//        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
//        seckillGoodsService.updateById(seckillGoods);
        boolean result = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().setSql("stock_count = stock_count - 1").eq("goods_id", goods.getId()).gt("stock_count", 0));

        /**
         * 这里如果 if 不加括号就会出现并发问题
         * 说明：将getOne语句移到下面也可以解决
         */
        if (!result) {
            valueOperations.set("isStockEmpty:" + goods.getId(), "0");
            return null;
        }


        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
//        //总库存减一
//        goodsService.update(new UpdateWrapper<Goods>().eq("id", goods.getId()).set("goods_stock", goods.getGoodsStock() - 1));
        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + goods.getId(), seckillGoods);
        return order;
    }

    @Override
    public OrderDetailVO detail(Long orderId) {
        Order order = baseMapper.selectById(orderId);
        Long goodsId = order.getGoodsId();
        GoodsVO goodsVo = goodsService.findGoodsVOByGoodsId(goodsId);
        OrderDetailVO res = new OrderDetailVO();
        res.setOrder(order);
        res.setGoodsVo(goodsVo);
        return res;
    }

    @Override
    public String createPath(User user, Long goodsId) {
        String s = MD5Util.md5(UUIDUtil.uuid() + "123456");
        redisTemplate.opsForValue().set("seckillPath:" + user.getId() + ":" + goodsId, s, 60, TimeUnit.SECONDS);
        return s;
    }

    /**
     * 校验地址
     *
     * @param user
     * @param goodsId
     * @param path
     * @return
     */
    @Override
    public boolean checkPath(User user, Long goodsId, String path) {
        if (user == null || goodsId < 0 || StringUtils.isEmpty(path)) {
            return false;
        }
        String redisPath = (String) redisTemplate.opsForValue().get("seckillPath:" + user.getId() + ":" + goodsId);
        return path.equals(redisPath);
    }

    /**
     * 校验验证码
     *
     * @param user
     * @param goodsId
     * @param captcha
     * @return
     */
    @Override
    public boolean checkCaptcha(User user, Long goodsId, String captcha) {
        if (StringUtils.isEmpty(captcha) || user == null || goodsId < 0) {
            return false;
        }
        String redisCaptcha = (String) redisTemplate.opsForValue().get("captcha:" + user.getId() + ":" + goodsId);
        return captcha.equals(redisCaptcha);
    }
}
