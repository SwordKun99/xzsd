package com.xzsd.pc.image.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.AuthUtils;
import com.xzsd.pc.entity.ImageInfo;
import com.xzsd.pc.entity.VO.ImageInfoVO;
import com.xzsd.pc.image.service.ImageService;
import com.xzsd.pc.upload.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
     * image 新增轮播图
     *
     * @param imageInfo
     * @return AppResponse
     * @author SwordKun.
     * @Date 2020-04-13
     */
    @PostMapping("saveImage")
    public AppResponse saveImage(ImageInfo imageInfo, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "biz_msg", required = false) String biz_msg) throws Exception {
        try {
            AppResponse appResponse = imageService.saveImage(imageInfo, biz_msg, file);
            return appResponse;
        } catch (Exception e) {
            logger.error("轮播图新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * image 删除轮播图
     *
     * @param imageId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    @PostMapping("deleteImage")
    public AppResponse deleteImage(String imageId) {
        try {
            return imageService.deleImage(imageId);
        } catch (Exception e) {
            logger.error("轮播图删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * image 修改轮播图
     *
     * @param imageInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    @PostMapping("updateImage")
    public AppResponse updateImage(ImageInfoVO imageInfoVO) {
        try {
            return imageService.updateImage(imageInfoVO);
        } catch (Exception e) {
            logger.error("修改轮播图信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * image 分页查询轮播图详情
     *
     * @param imageInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    @RequestMapping(value = "getImageByImageType")
    public AppResponse getImageByImageType(ImageInfo imageInfo) {
        try {
            return imageService.getImageByImageType(imageInfo);
        } catch (Exception e) {
            logger.error("轮播图查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * commodity 查询商品详情
     *
     * @param imageId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    @RequestMapping(value = "getComByCommodityInfo")
    public AppResponse getComByCommodityInfo(String imageId) {
        try {
            return imageService.getComByCommodityInfo(imageId);
        } catch (Exception e) {
            logger.error("商品查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

}
