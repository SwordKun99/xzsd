package com.xzsd.pc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.pc.entity.CommodityClassInfo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @ClassName CommodityClassDao
 * @Description ComodityClass
 * @Author SwordKun.
 * @Date 2020-03-29
 */
@Mapper
public interface CommodityClassDao extends BaseMapper<CommodityClassInfo> {

    /**
     * 查询一级分类商品列表
     *
     * @param
     * @return
     */
    List<CommodityClassInfo> listParentCode();

    /**
     * 查询二级分类商品列表
     *
     * @param
     * @return commodityClassInfo
     */
    List<CommodityClassInfo> listSecondCode(CommodityClassInfo commodityClassInfo);
}
