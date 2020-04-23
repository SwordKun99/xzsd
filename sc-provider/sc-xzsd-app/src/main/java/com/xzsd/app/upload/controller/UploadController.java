package com.xzsd.app.upload.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.upload.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description Upload公用（图片）
 * @Author SwordKun.
 * @Date 2020-04-12
 */

@RestController
@RequestMapping("/upload")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片功能
     *
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("Biz_Msg") String Biz_Msg, @RequestParam("Biz_ID") String Biz_ID, @RequestParam("file") MultipartFile file) throws Exception {
        String url = uploadService.uploadImage(Biz_Msg, Biz_ID, file);
        if (StringUtils.isBlank(url)) {
            // url为空，证明上传失败
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 返回200，并且携带url路径
        return ResponseEntity.ok(url);
    }

    /**
     * 查询图片列表功能
     *
     * @param bizId
     * @return
     */
    @RequestMapping(value = "getImageList")
    public AppResponse getImageList(String bizId) {
        try {
            return uploadService.getImageList(bizId);
        } catch (Exception e) {
            logger.error("图片查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * 删除图片功能
     *
     * @param fileId
     * @return
     */
    @PostMapping("deleteimage")
    public AppResponse deleteimage(String fileId) throws Exception {
        try {
            return uploadService.deleteimage(fileId);
        } catch (Exception e) {
            logger.error("图片删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
