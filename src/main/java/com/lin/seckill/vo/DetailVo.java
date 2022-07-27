package com.lin.seckill.vo;

import com.lin.seckill.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>详情返回对象</p>
 *
 * @author : star
 * @date : 2022/7/26 23:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVo {

    private User user;

    private GoodsVO goodsVo;

    private int secKillStatus;

    private int remainSeconds;
}