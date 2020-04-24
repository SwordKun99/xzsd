package com.xzsd.app.customerevalution.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.customerevalution.service.CusEvaService;
import com.xzsd.app.entity.CustomerEvaluationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customerevalution")
public class CustEvaController {

    private static final Logger logger = LoggerFactory.getLogger(CustEvaController.class);

    @Autowired
    private CusEvaService cusEvaService;

    /**
     * order 订单评价列表(分页)
     *
     * @param customerEvaluationInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @RequestMapping(value = "listCusEva")
    public AppResponse listCusEva(CustomerEvaluationInfo customerEvaluationInfo) {
        try {
            return cusEvaService.listCusEva(customerEvaluationInfo);
        } catch (Exception e) {
            logger.error("查询订单列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * order 新增订单评价
     *
     * @param orderId,evaList
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @RequestMapping(value = "saveEvaluationInfo")
    public AppResponse saveEvaluationInfo(String orderId, List<CustomerEvaluationInfo> evaList) {
        try {
            return cusEvaService.saveEvaluationInfo(orderId,evaList);
        } catch (Exception e) {
            logger.error("订单评价异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * order 新增订单评价
     *
     * @param orderId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @RequestMapping(value = "listEvalution")
    public AppResponse listEvalution(String orderId) {
        try {
            return cusEvaService.listEvalution(orderId);
        } catch (Exception e) {
            logger.error("查询订单商品信息列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
