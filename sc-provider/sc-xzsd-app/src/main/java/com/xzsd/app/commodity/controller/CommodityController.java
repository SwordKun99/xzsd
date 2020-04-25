package com.xzsd.app.commodity.controller;


import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.commodity.service.CommodityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @DescriptionDemo 商品控制类
 * @Author SwordKun.
 * @Date 2020-04-23
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    private static final Logger logger = LoggerFactory.getLogger(CommodityController.class);

    @Autowired
    private CommodityService commodityService;

    /**
     * commodity 查询商品详情
     *
     * @param commodityId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @RequestMapping(value = "getCommodityByInfo")
    public AppResponse getCommodityByInfo(String commodityId) {
        try {
            return commodityService.getCommodityByInfo(commodityId);
        } catch (Exception e) {
            logger.error("商品查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * commodity 查询上级分类列表
     *
     * @param
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @RequestMapping(value = "listCommodityFirst")
    public AppResponse listCommodityFirst() {
        try {
            return commodityService.listCommodityFirst();
        } catch (Exception e) {
            logger.error("查询上级分类列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * commodity 查询二级分类列表
     *
     * @param
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @RequestMapping(value = "listCommoditySencond")
    public AppResponse listCommoditySencond(String parentCode) {
        try {
            return commodityService.listCommoditySencond(parentCode);
        } catch (Exception e) {
            logger.error("区查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
