package com.lin.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

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
@TableName("t_seckill_goods")
public class SeckillGoods {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    /**
     * 商品ID
     */

    private Long goodsId;

    /**
     * 秒杀家
     */
    private BigDecimal seckillPrice;

    /**
     * 库存数量
     */
    private Integer stockCount;

    /**
     * 秒杀开始时间
     */
    private Date startDate;

    /**
     * 秒杀结束时间
     */
    private Date endDate;


}
