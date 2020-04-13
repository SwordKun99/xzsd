package com.xzsd.pc.commodity.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.AuthUtils;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.commodity.service.CommodityService;
import com.xzsd.pc.entity.CommodityInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @Description增删改查Commodity
 * @Author SwordKun.
 * @Date 2020-03-29
 */

@RestController
@RequestMapping("/commodity")
public class CommodityController {
    private static final Logger logger = LoggerFactory.getLogger(CommodityController.class);

    @Resource
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
    public AppResponse saveCommodity(@RequestBody CommodityInfo commodityInfo) {
        try {
            //获取商品分类id
            String commodityId = AuthUtils.getCurrentCommodityId();
            commodityInfo.setCreateUser(commodityId);
            commodityInfo.setCommodityId(UUIDUtils.getUUID());
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
     * @param commodityInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @PostMapping("deleteCommodity")
    public AppResponse deleteCommodity(@RequestBody CommodityInfo commodityInfo) {
        try {
            return commodityService.updateCommodityById(commodityInfo);
        } catch (Exception e) {
            logger.error("商品分类删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * commodity 修改商品
     *
     * @param commodityInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @PostMapping("updateCommodity")
    public AppResponse updateCommodity(@RequestBody CommodityInfo commodityInfo) {
        try {
            //获取商品分类id
            String commodityId = AuthUtils.getCurrentCommodityId();
            commodityInfo.setCreateUser(commodityId);
            commodityInfo.setUpdateUser(commodityId);
            return commodityService.updateCommodity(commodityInfo);
        } catch (Exception e) {
            logger.error("修改商品分类信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }


    /**
     * commodity 查询商品详情
     *
     * @param commodityInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @RequestMapping(value = "getCommodityByInfo")
    public AppResponse getCommodityByInfo(@RequestBody CommodityInfo commodityInfo) {
        try {
            return commodityService.getCommodityByInfo(commodityInfo);
        } catch (Exception e) {
            logger.error("商品分类查询错误", e);
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
    public AppResponse listCommodity(@RequestBody CommodityInfo commodityInfo, long time) {
        try {
            return commodityService.listCommodity(commodityInfo, time);
        } catch (Exception e) {
            logger.error("查询用户列表异常", e);
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
            logger.error("查询用户列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * commodity 一级分类列表
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
            logger.error("查询一级分类列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * commodity 二级分类列表
     *
     * @param commodityInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    /*@RequestMapping(value = "listCommoditySecond")
    public AppResponse listCommoditySecond(@RequestBody CommodityInfo commodityInfo, long time) {
        try {
            return commodityService.listCommodity(commodityInfo, time);
        } catch (Exception e) {
            logger.error("查询二级分类列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }*/

}
