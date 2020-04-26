package com.xzsd.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.GoodsHotInfo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @ClassName GoodsHotDao
 * @Description GoodsHot
 * @Author SwordKun.
 * @Date 2020-04-01
 */
@Mapper
public interface GoodsHotDao extends BaseMapper<GoodsHotInfo> {

    /**
     * 查询热门商品列表
     *
     * @param goodsHotInfo
     * @return
     */
    List<GoodsHotInfo> listGoodsHot(GoodsHotInfo goodsHotInfo);
}
