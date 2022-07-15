package com.lin.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author star
 * @since 2022-07-15
 */
@Data
@Accessors(chain = true)
@TableName("t_seckill_order")
public class SeckillOrder {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId("id")
    private Long userId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 商品ID
     */
    private Long goodsId;


}
