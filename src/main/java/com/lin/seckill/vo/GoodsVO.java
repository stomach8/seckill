package com.lin.seckill.vo;

import com.lin.seckill.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>商品返回对象</p>
 *
 * @author : star
 * @date : 2022/7/16 0:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVO extends Goods {
    private BigDecimal seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
