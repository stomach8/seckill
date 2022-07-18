package com.lin.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.seckill.entity.Order;
import com.lin.seckill.entity.User;
import com.lin.seckill.vo.GoodsVO;

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
}
