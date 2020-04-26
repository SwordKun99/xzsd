package com.xzsd.pc.upload.service;

import com.xzsd.pc.menu.controller.MenuController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-04-12
 */

@Service
public class UploadService {

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    private static final List<String> ALLOW_TYPES = Arrays.asList("image/png", "image/jpeg", "image/bmg");


}