package com.xzsd.pc.upload.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.pc.upload.service.UploadService;
import com.xzsd.pc.util.TencentCosUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public AppResponse uploadImage(MultipartFile file) throws Exception {
        String url = TencentCosUtil.upload("images", file);
        if (StringUtils.isBlank(url)) {
            // url为空，证明上传失败
            return AppResponse.bizError("图片上传失败");
        }
        // 返回200，并且携带url路径
        return AppResponse.success("图片上传成功！", url);
    }
}
