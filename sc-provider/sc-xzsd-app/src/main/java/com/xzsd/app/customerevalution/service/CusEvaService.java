package com.xzsd.app.customerevalution.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.UUIDUtils;
import com.xzsd.app.dao.*;
import com.xzsd.app.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-04-10
 */
@Service
public class CusEvaService {

    @Resource
    private CustomerEvalutionDao customerEvalutionDao;

    @Resource
    private CustomerDao customerDao;

    @Resource
    private ShopDao shopDao;

    @Resource
    private OrderDao orderDao;

    @Resource
    private CommodityDao commodityDao;

    /**
     * order 查询订单列表（分页）
     *
     * @param customerEvaluationInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse listCusEva(CustomerEvaluationInfo customerEvaluationInfo) {
        QueryWrapper<CustomerEvaluationInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CustomerEvaluationInfo::getIsDelete, 0);
        if (customerEvaluationInfo.getStarLevel() != null && customerEvaluationInfo.getStarLevel() == "5") {
            queryWrapper.lambda().in(CustomerEvaluationInfo::getStarLevel,4,5);
        }
        if (customerEvaluationInfo.getStarLevel() != null && customerEvaluationInfo.getStarLevel() == "3") {
            queryWrapper.lambda().in(CustomerEvaluationInfo::getStarLevel,2,3);
        }
        if (customerEvaluationInfo.getStarLevel() != null && customerEvaluationInfo.getStarLevel() == "1") {
            queryWrapper.lambda().in(CustomerEvaluationInfo::getStarLevel,0,1);
        }
        PageInfo<CustomerEvaluationInfo> pageData = PageHelper.startPage(customerEvaluationInfo.getPageNum(), customerEvaluationInfo.getPageSize()).doSelectPageInfo(() -> customerEvalutionDao.selectList(queryWrapper));
        return AppResponse.success("查询订单列表成功", pageData);
    }

    /**
     * evaluation 新增订单评价
     *
     * @param orderId,evaList
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveEvaluationInfo(String orderId, List<CustomerEvaluationInfo> evaList) {
        String customerId = SecurityUtils.getCurrentUserId();
        CustomerInfo customerInfo = customerDao.selectById(customerId);
        QueryWrapper<ShopInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShopInfo::getInvitation,customerInfo.getInvitation());
        ShopInfo shopInfo = shopDao.selectOne(queryWrapper);
        for (CustomerEvaluationInfo customerEvaluationInfo : evaList) {
            customerEvaluationInfo.setEveluationId(UUIDUtils.getUUID());
            customerEvaluationInfo.setOrderId(orderId);
            customerEvaluationInfo.setIsDelete(0);
            customerEvaluationInfo.setVersion(0);
            customerEvaluationInfo.setCreateTime(new Date());
            customerEvaluationInfo.setCustomerId(customerId);
            customerEvaluationInfo.setCreateSer(customerId);
            customerEvaluationInfo.setShopId(shopInfo.getShopId());
            Integer count = customerEvalutionDao.insert(customerEvaluationInfo);
            if (0 == count) {
                return AppResponse.bizError("评价失败，请重试！");
            }
            //更新商品星级
            CommodityInfo commodityInfo = commodityDao.selectById(customerEvaluationInfo.getCommodityId());
            String comleavel = customerEvalutionDao.comStarLevel(customerEvaluationInfo);
            commodityInfo.setSvaluationStar(comleavel);
            commodityDao.updateById(commodityInfo);
        }
        //更新订单状态
        OrderMasterInfo orderMasterInfo = orderDao.selectById(orderId);
        orderMasterInfo.setOrderStatus("3");
        orderMasterInfo.setVersion(orderMasterInfo.getVersion()+1);
        orderMasterInfo.setUpdateTime(new Date());
        orderMasterInfo.setUpdateUser(customerId);
        Integer count = orderDao.updateById(orderMasterInfo);
        if (0 == count) {
            return AppResponse.bizError("评价失败，请重试！");
        }
        return AppResponse.success("评价成功");
    }

    /**
     * evaluation 查询订单评价列表
     *
     * @param orderId
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse listEvalution (String orderId) {
        List<CustomerEvaluationInfo> evalutionList = customerEvalutionDao.listEvalution(orderId);
        return AppResponse.success("查询订单评价列表成功",evalutionList);
    }
}
