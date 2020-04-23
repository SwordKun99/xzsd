package com.xzsd.pc.order.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.pc.entity.OrderInfo;
import com.xzsd.pc.entity.VO.OrderInfoVO;
import com.xzsd.pc.order.service.OrderService;
import com.xzsd.pc.user.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private OrderService orderService;

    /**
     * order 修改订单
     *
     * @param orderInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @PostMapping("updateOrder")
    public AppResponse updateOrder(OrderInfoVO orderInfoVO) {
        try {
            return orderService.updateOrder(orderInfoVO);
        } catch (Exception e) {
            logger.error("修改订单信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * order 查询订单详情
     *
     * @param orderInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @RequestMapping(value = "getOrderByOrderNumber")
    public AppResponse getOrderByOrderNumber(OrderInfo orderInfo) {
        try {
            return orderService.getUserByOrderNumber(orderInfo);
        } catch (Exception e) {
            logger.error("订单查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * order 订单列表(分页)
     *
     * @param orderInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @RequestMapping(value = "listOrder")
    public AppResponse listOrder(OrderInfo orderInfo) {
        try {
            return orderService.listOrder(orderInfo);
        } catch (Exception e) {
            logger.error("查询订单列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
