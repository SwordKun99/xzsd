package com.xzsd.pc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.pc.entity.ImageInfo;
import com.xzsd.pc.entity.VO.ImageInfoVO;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @ClassName ImageDao
 * @Description Image
 * @Author SwordKun.
 * @Date 2020-03-29
 */
@Mapper
public interface ImageDao extends BaseMapper<ImageInfo> {
    /**
     * 查询轮播图信息
     *
     * @param imageInfo 轮播图
     * @return 修改结果
     */
    List<ImageInfoVO> getImageByImageType(ImageInfoVO imageInfo);

    /**
     * 查询商品信息
     *
     * @param imageId 商品id
     * @return 修改结果
     */
    ImageInfoVO getComByCommodityInfo(@Param("imageId") String imageId);
}
