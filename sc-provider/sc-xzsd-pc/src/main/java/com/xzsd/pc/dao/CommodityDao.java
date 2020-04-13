package com.xzsd.pc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.pc.entity.CommodityInfo;
import org.mapstruct.Mapper;

/**
 * @ClassName CommodityDao
 * @Description Commodity
 * @Author SwordKun.
 * @Date 2020-03-29
 */
@Mapper
public interface CommodityDao extends BaseMapper<CommodityInfo> {
}
