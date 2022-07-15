package com.lin.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

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
public class Goods {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 商品图片
     */
    private String goodsImg;

    /**
     * 商品描述
     */
    private String goodsDetail;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 商品库存,-1表示没有限制
     */
    private Integer goodsStock;


}
