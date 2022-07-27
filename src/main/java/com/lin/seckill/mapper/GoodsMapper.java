package com.lin.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.seckill.entity.Goods;
import com.lin.seckill.vo.GoodsVO;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author star
 * @since 2022-07-15
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVO> findGoodsVO();

    GoodsVO findGoodsVOByGoodsId(Long goodsId);
}
