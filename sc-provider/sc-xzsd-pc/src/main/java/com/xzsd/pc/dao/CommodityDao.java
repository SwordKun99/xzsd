package com.xzsd.pc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.pc.entity.CommodityInfo;
import com.xzsd.pc.entity.VO.CommodityInfoVO;
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
    /**
     * 查询商品信息
     *
     * @param commodityId 商品id
     * @return 修改结果
     */
    CommodityInfo getCommodityByInfo(@Param("commodityId") String commodityId);

    /**
     * 获取所有商品信息
     *
     * @param commodityInfo 商品信息
     * @return 所有商品信息
     */
    List<CommodityInfoVO> listCommodity(CommodityInfoVO commodityInfo);
}
