package com.xzsd.app.upload.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @DescriptionDemo 图片实现类
 * @Author SwordKun.
 * @Date 2020-04-12
 */
@Service
public class UploadService {

    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    private static final List<String> ALLOW_TYPES = Arrays.asList("image/png", "image/jpeg", "image/bmg");

}