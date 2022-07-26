package com.lin.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.seckill.entity.SeckillOrder;
import com.lin.seckill.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author star
 * @since 2022-07-15
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    Long getResult(User user, Long goodsId);
}
