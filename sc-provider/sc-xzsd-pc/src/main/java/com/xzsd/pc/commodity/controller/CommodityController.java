package com.xzsd.pc.commodity.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.pc.commodity.service.CommodityService;
import com.xzsd.pc.entity.CommodityInfo;
import com.xzsd.pc.entity.VO.CommodityInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description增删改查Commodity
 * @Author SwordKun.
 * @Date 2020-03-29
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    private static final Logger logger = LoggerFactory.getLogger(CommodityController.class);

    @Autowired
    private CommodityService commodityService;


    /**
     * commodity 新增商品
     *
     * @param commodityInfo
     * @return AppResponse
     * @author SwordKun.
     * @Date 2020-03-29
     */
    @PostMapping("saveCommodity")
    public AppResponse saveCommodity(CommodityInfo commodityInfo) throws Exception {
        try {
            AppResponse appResponse = commodityService.saveCommodity(commodityInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("商品分类新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }


    /**
     * commodity 删除商品
     *
     * @param commodityId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @PostMapping("deleteCommodity")
    public AppResponse deleteCommodity(String commodityId) {
        try {
            return commodityService.deleteCommodity(commodityId);
        } catch (Exception e) {
            logger.error("商品分类删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * commodity 修改商品
     *
     * @param commodityInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @PostMapping("updateCommodity")
    public AppResponse updateCommodity(CommodityInfoVO commodityInfoVO) {
        try {
            return commodityService.updateCommodity(commodityInfoVO);
        } catch (Exception e) {
            logger.error("修改商品分类信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }


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
     * commodity 商品列表(分页)
     *
     * @param commodityInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @RequestMapping(value = "listCommodity")
    public AppResponse listCommodity(CommodityInfo commodityInfo) {
        try {
            return commodityService.listCommodity(commodityInfo);
        } catch (Exception e) {
            logger.error("查询商品列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }


    /**
     * commodity 商家列表
     *
     * @param
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @RequestMapping(value = "listCommodityStone")
    public AppResponse listCommodityStone() {
        try {
            return commodityService.listCommodityStone();
        } catch (Exception e) {
            logger.error("查询商家列表异常", e);
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

    /**
     * commodity 商品上下架
     *
     * @param commodityInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @PostMapping("updateComState")
    public AppResponse updateComState(CommodityInfoVO commodityInfoVO) {
        try {
            return commodityService.updateComState(commodityInfoVO);
        } catch (Exception e) {
            logger.error("商品修改上下架错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
