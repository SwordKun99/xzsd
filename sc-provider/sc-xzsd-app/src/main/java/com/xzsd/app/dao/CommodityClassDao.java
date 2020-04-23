package com.xzsd.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.CommodityClassInfo;
import org.mapstruct.Mapper;

/**
 * @ClassName CommodityClassDao
 * @Description ComodityClass
 * @Author SwordKun.
 * @Date 2020-03-29
 */
@Mapper
public interface CommodityClassDao extends BaseMapper<CommodityClassInfo> {
}
