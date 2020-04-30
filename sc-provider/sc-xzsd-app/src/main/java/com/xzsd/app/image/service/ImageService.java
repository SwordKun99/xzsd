package com.xzsd.app.image.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.dao.ImageDao;
import com.xzsd.app.entity.ImageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
        QueryWrapper<ImageInfo> queryWrapper = new QueryWrapper<>();
        List<ImageInfo> list = imageDao.selectList(queryWrapper);
        return AppResponse.success("查询成功！", list);
    }
}

