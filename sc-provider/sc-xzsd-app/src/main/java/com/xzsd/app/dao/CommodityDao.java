package com.xzsd.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.CommodityInfo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @ClassName CommodityDao
 * @Description Commodity
 * @Author SwordKun.
 * @Date 2020-03-29
 */
@Mapper
public interface CommodityDao extends BaseMapper<CommodityInfo> {

    /**
     * 查询商品列表
     *
     * @param commodityInfo
     * @return
     */
    List<CommodityInfo> getGoodsList(CommodityInfo commodityInfo);
}
