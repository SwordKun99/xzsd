package com.xzsd.app.image.service;


import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.dao.ImageDao;
import com.xzsd.app.entity.ImageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Resource
    private ImageDao imageDao;


    /**
     * image 查询首页轮播图
     *
     * @param
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse listImage() {
        ImageInfo imageInfo = imageDao.listImage();
        return AppResponse.success("查询成功！", imageInfo);
    }
}

