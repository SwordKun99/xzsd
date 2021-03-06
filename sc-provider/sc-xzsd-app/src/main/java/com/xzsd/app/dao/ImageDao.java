package com.xzsd.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.ImageInfo;
import org.mapstruct.Mapper;

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
     * @param
     * @return
     */
    ImageInfo listImage();
}
