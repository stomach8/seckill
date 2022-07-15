package com.lin.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.seckill.entity.Goods;
import com.lin.seckill.vo.GoodsVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author star
 * @since 2022-07-15
 */
public interface IGoodsService extends IService<Goods> {

    List<GoodsVO> findGoodsVO();

    GoodsVO findGoodsVOByGoodsId(Long goodsId);
}
