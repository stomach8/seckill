package com.lin.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.seckill.entity.Order;
import com.lin.seckill.entity.User;
import com.lin.seckill.vo.GoodsVO;
import com.lin.seckill.vo.OrderDetailVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author star
 * @since 2022-07-15
 */
public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVO goods);

    OrderDetailVO detail(Long orderId);

    String createPath(User user, Long goodsId);

    boolean checkPath(User user, Long goodsId, String path);

    boolean checkCaptcha(User user, Long goodsId, String captcha);

}
