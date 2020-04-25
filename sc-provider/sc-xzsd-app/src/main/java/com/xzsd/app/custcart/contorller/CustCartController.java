package com.xzsd.app.custcart.contorller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.custcart.service.CustCartService;
import com.xzsd.app.entity.CustCartInfo;
import com.xzsd.app.entity.VO.CustCartInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @DescriptionDemo 购物车控制类
 * @Author SwordKun.
 * @Date 2020-04-23
 */
@RequestMapping("/shopcar")
@RestController
public class CustCartController {

    private static final Logger logger = LoggerFactory.getLogger(CustCartController.class);

    @Resource
    private CustCartService custCartService;

    /**
     * custcart 新增购物车
     *
     * @param custCartInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @RequestMapping("saveCustCart")
    public AppResponse saveCustCart(CustCartInfo custCartInfo) {
        try {
            return custCartService.saveCustCart(custCartInfo);
        } catch (Exception e) {
            logger.error("查询购物车列表失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * custcart 删除购物车
     *
     * @param shopcarId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @RequestMapping("deleteCustCart")
    public AppResponse deleteCustCart(String shopcarId) {
        try {
            return custCartService.deleteCustCart(shopcarId);
        } catch (Exception e) {
            logger.error("查询购物车列表失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * custcart 修改购物车商品数量
     *
     * @param custCartInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-22
     */
    @RequestMapping("updateCustCart")
    public AppResponse updateCustCart(CustCartInfoVO custCartInfoVO) {
        try {
            return custCartService.updateCustCart(custCartInfoVO);
        } catch (Exception e) {
            logger.error("查询购物车列表失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * custcart 查询购物车；列表
     *
     * @param custCartInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-22
     */
    @RequestMapping("listShoppingCart")
    public AppResponse listCustCart(CustCartInfo custCartInfo) {
        try {
            return custCartService.listCustCart(custCartInfo);
        } catch (Exception e) {
            logger.error("查询购物车列表失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}