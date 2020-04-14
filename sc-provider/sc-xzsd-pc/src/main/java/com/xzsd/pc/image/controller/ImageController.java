package com.xzsd.pc.image.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.AuthUtils;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.entity.ImageInfo;
import com.xzsd.pc.image.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
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


    /**
     * image 新增用户
     *
     * @param imageInfo
     * @return AppResponse
     * @author SwordKun.
     * @Date 2020-04-13
     */
    @PostMapping("saveImage")
    public AppResponse saveImage(@RequestBody ImageInfo imageInfo, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "biz_msg", required = false) String biz_msg) throws Exception {
        try {
            //获取用户id
            String imageId = AuthUtils.getCurrentImageId();
            imageInfo.setCreateSer(imageId);
            imageInfo.setImageId(UUIDUtils.getUUID());
            AppResponse appResponse = imageService.saveImage(imageInfo, biz_msg, file);
            return appResponse;
        } catch (Exception e) {
            logger.error("用户新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * image 删除用户
     *
     * @param imageInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    @PostMapping("deleteImage")
    public AppResponse deleteImage(@RequestBody ImageInfo imageInfo) {
        try {
            return imageService.deleImage(imageInfo);
        } catch (Exception e) {
            logger.error("轮播图删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * image 修改用户
     *
     * @param imageInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    @PostMapping("updateImage")
    public AppResponse updateImage(@RequestBody ImageInfo imageInfo) {
        try {
            //获取用户id
            String imageId = AuthUtils.getCurrentImageId();
            imageInfo.setCreateSer(imageId);
            imageInfo.setUpdateUser(imageId);
            return imageService.updateImage(imageInfo);
        } catch (Exception e) {
            logger.error("修改用户信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * image 查询用户详情
     *
     * @param imageInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    @RequestMapping(value = "getImageByImageCode")
    public AppResponse getImageByImageCode(@RequestBody ImageInfo imageInfo, String biz_id) {
        try {
            return imageService.getImageByImageCode(imageInfo, biz_id);
        } catch (Exception e) {
            logger.error("用户查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * image 用户列表(分页)
     *
     * @param imageInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    @RequestMapping(value = "listImages")
    public AppResponse listImages(@RequestBody ImageInfo imageInfo) {
        try {
            return imageService.listImages(imageInfo);
        } catch (Exception e) {
            logger.error("查询用户列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }


}
