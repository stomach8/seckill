package com.lin.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.seckill.entity.Order;
import com.lin.seckill.entity.SeckillOrder;
import com.lin.seckill.entity.User;
import com.lin.seckill.service.IGoodsService;
import com.lin.seckill.service.IOrderService;
import com.lin.seckill.service.ISeckillOrderService;
import com.lin.seckill.vo.GoodsVO;
import com.lin.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>秒杀</p>
 *
 * @author : star
 * @date : 2022/7/18 22:22
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, User user, Long goodsId) {
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        //判断库存
        GoodsVO goods = goodsService.findGoodsVOByGoodsId(goodsId);
        if (goods.getStockCount() < 1) {
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK);
            return "secKillFail";
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if (seckillOrder != null) {
            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR);
            return "secKillFail";
        }
        Order order = orderService.seckill(user, goods);
        model.addAttribute("order", order);
        model.addAttribute("goods", goods);
        return "orderDetail";
    }
}
