package com.xzsd.app.order.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.entity.OrderMasterInfo;
import com.xzsd.app.entity.VO.OrderMasterInfoVO;
import com.xzsd.app.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 订单
 *
 * @author SwordKun.
 * @date 2020-04-23
 */
@RequestMapping("/order")
@RestController
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private OrderService orderService;

    /**
     * orderMaster 新增订单
     *
     * @param orderMasterInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-23
     */
    @RequestMapping("saveOrder")
    public AppResponse saveOrder(OrderMasterInfo orderMasterInfo) {
        try {
            return orderService.saveOrder(orderMasterInfo);
        } catch (Exception e) {
            logger.error("下单失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * orderMaster 修改订单状态
     *
     * @param orderMasterInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-24
     */
    @RequestMapping("updateOrderStatus")
    public AppResponse updateOrderStatus(OrderMasterInfoVO orderMasterInfoVO) {
        try {
            return orderService.updateOrderStatus(orderMasterInfoVO);
        } catch (Exception e) {
            logger.error("订单状态修改失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * orderMaster 查询订单详情
     *
     * @param orderId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-24
     */
    @RequestMapping("orderInfo")
    public AppResponse orderInfo(String orderId) {
        try {
            return orderService.orderInfo(orderId);
        } catch (Exception e) {
            logger.error("查询订单详情失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * orderMaster 查询订单列表
     *
     * @param orderMasterInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-24
     */
    @RequestMapping("listOrder")
    public AppResponse listOrder(OrderMasterInfo orderMasterInfo) {
        try {
            return orderService.listOrder(orderMasterInfo);
        } catch (Exception e) {
            logger.error("查询订单列表失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
