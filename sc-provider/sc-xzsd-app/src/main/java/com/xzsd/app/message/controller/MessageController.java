package com.xzsd.app.message.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.entity.ShopInfo;
import com.xzsd.app.message.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 门店下司机实现类
 *
 * @author SwordKun.
 * @date 2020-04-23
 */
@RequestMapping("/message")
@RestController
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Resource
    private MessageService messageService;

    /**
     * commodity 查询购物车列表(分页)
     *
     * @param shopInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-23
     */
    @RequestMapping("listShop")
    public AppResponse listShop(ShopInfo shopInfo) {
        try {
            return messageService.listShop(shopInfo);
        } catch (Exception e) {
            logger.error("查询门店下司机列表失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
