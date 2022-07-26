package com.lin.seckill.vo;

import com.lin.seckill.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>订单详情返回对象</p>
 *
 * @author : star
 * @date : 2022/7/26 23:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVO {
    private Order order;

    private GoodsVO goodsVo;
}

