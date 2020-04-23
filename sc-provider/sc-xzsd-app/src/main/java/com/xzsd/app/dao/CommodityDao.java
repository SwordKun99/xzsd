package com.xzsd.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.CommodityInfo;
import com.xzsd.app.entity.VO.CommodityInfoVO;
import org.apache.ibatis.annotations.Param;
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

    List<CommodityInfo> getGoodsList(CommodityInfo commodityInfo);
}
