package com.xzsd.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.CommodityClassInfo;
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
     * commodity 查询上级分类列表
     *
     * @param
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    List<CommodityClassInfo> listParentCode();

    /**
     * commodity 查询二级分类列表
     *
     * @param commodityClassInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    List<CommodityClassInfo> listSecondCode(CommodityClassInfo commodityClassInfo);
}
