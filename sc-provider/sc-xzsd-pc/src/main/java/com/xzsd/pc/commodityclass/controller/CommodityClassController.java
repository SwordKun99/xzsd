package com.xzsd.pc.commodityclass.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.pc.commodityclass.service.CommodityClassService;
import com.xzsd.pc.entity.CommodityClassInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 增删改查commodityclass
 *
 * @Author SwordKun.
 * @Date 2020-03-29
 */
@RestController
@RequestMapping("/commodityclass")
public class CommodityClassController {

    private static final Logger logger = LoggerFactory.getLogger(CommodityClassController.class);

    @Resource
    private CommodityClassService commodityclassService;

    /**
     * commodityclass 新增商品分类
     *
     * @param commodityclassInfo
     * @return AppResponse
     * @author SwordKun.
     * @Date 2020-03-29
     */
    @PostMapping("saveCommodityClass")
    public AppResponse saveCommodityClass(CommodityClassInfo commodityclassInfo) {
        try {
            AppResponse appResponse = commodityclassService.saveCommodityClass(commodityclassInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("商品分类新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * commodityclass 删除商品分类
     *
     * @param commodityclassInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @PostMapping("deleteCommodityClass")
    public AppResponse deleteCommodityClass(CommodityClassInfo commodityclassInfo) {
        try {
            return commodityclassService.updateCommodityClassById(commodityclassInfo);
        } catch (Exception e) {
            logger.error("商品分类删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * commodityclass 修改商品分类
     *
     * @param commodityclassInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @PostMapping("updateCommodityClass")
    public AppResponse updateCommodityClass(CommodityClassInfo commodityclassInfo) {
        try {
            return commodityclassService.updateCommodityClass(commodityclassInfo);
        } catch (Exception e) {
            logger.error("修改商品分类信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * commodityclass 查询商品分类详情
     *
     * @param commodityclassInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @RequestMapping(value = "getCommodityClassByInfo")
    public AppResponse getCommodityClassByInfo(CommodityClassInfo commodityclassInfo) {
        try {
            return commodityclassService.getCommodityClassByInfo(commodityclassInfo);
        } catch (Exception e) {
            logger.error("商品分类查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * commodityclass 商品分类列表(分页)
     *
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @RequestMapping(value = "listCommodityClass")
    public AppResponse listCommodityClass() {
        try {
            return commodityclassService.listCommodityClass();
        } catch (Exception e) {
            logger.error("查询商品分类列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
