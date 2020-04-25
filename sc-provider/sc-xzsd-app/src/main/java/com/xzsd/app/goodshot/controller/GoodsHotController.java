package com.xzsd.app.goodshot.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.entity.GoodsHotInfo;
import com.xzsd.app.goodshot.service.GoodsHotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @DescriptionDemo 热门商品控制类
 * @Author SwordKun.
 * @Date 2020-04-23
 */
@RestController
@RequestMapping("/goodshot")
public class GoodsHotController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsHotController.class);

    @Autowired
    private GoodsHotService goodsHotService;

    /**
     * commodity 热门商品列表(分页)
     *
     * @param goodsHotInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-22
     */
    @RequestMapping(value = "listGoodsHot")
    public AppResponse listGoodsHot(GoodsHotInfo goodsHotInfo) {
        try {
            return goodsHotService.listGoodsHot(goodsHotInfo);
        } catch (Exception e) {
            logger.error("查询热门商品列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
