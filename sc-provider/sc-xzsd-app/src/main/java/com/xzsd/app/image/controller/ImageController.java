package com.xzsd.app.image.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.image.service.ImageService;
import com.xzsd.app.upload.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description增删改查Image
 * @Author SwordKun.
 * @Date 2020-04-13
 */
@RestController
@RequestMapping("/image")

public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Resource
    private ImageService imageService;

    @Autowired
    private UploadService uploadService;


    /**
     * image 查询轮播图
     *
     * @param
     * @return AppResponse
     * @author SwordKun.
     * @Date 2020-04-13
     */
    @PostMapping("listImage")
    public AppResponse listImage() {
        try {
            AppResponse appResponse = imageService.listImage();
            return appResponse;
        } catch (Exception e) {
            logger.error("查询轮播图失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
