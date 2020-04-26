package com.xzsd.pc.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.xzsd.pc.dao.OrderDao;
import com.xzsd.pc.dao.ShopDao;
import com.xzsd.pc.dao.UserDao;
import com.xzsd.pc.entity.OrderInfo;
import com.xzsd.pc.entity.ShopInfo;
import com.xzsd.pc.entity.UserInfo;
import com.xzsd.pc.entity.VO.OrderInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-04-10
 */
@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ShopDao shopDao;

    /**
     * order 修改订单
     *
     * @param orderInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateOrder(OrderInfoVO orderInfoVO) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验订单是否存在
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(OrderInfo::getOrderStatus, orderInfoVO.getOrderStatus());
        Integer countOrderAcct = orderDao.selectCount(queryWrapper);
        if (0 != countOrderAcct) {
            return AppResponse.bizError("订单状态已存在，请重新输入！");
        }
        OrderInfo orderInfoOld = orderDao.selectById(orderInfoVO.getOrderId());
        if (orderInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该订单，请重试！");
            return appResponse;
        }
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderInfoVO, orderInfo);
        orderInfo.setVersion(orderInfoOld.getVersion() + 1);
        orderInfo.setUpdateTime(new Date());
        String updateUserId = SecurityUtils.getCurrentUserId();
        orderInfo.setUpdateUser(updateUserId);
        orderInfo.setOrderStatus(orderInfo.getOrderStatus());
        // 修改用户信息
        int count = orderDao.updateById(orderInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("订单信息有变化，请刷新！");
            return appResponse;
        }
        return appResponse;
    }

    /**
     * order 查询订单详情
     *
     * @param orderInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    public AppResponse getUserByOrderNumber(OrderInfo orderInfo) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderInfo::getOrderId, orderInfo.getOrderId());
        List<OrderInfo> orderInfoList = orderDao.selectList(queryWrapper);
        return AppResponse.success("查询成功！", orderInfoList);
    }

    /**
     * order 分页查询订单列表
     *
     * @param orderInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    public AppResponse listOrder(OrderInfo orderInfo) {
        PageHelper.startPage(orderInfo.getPageNum(), orderInfo.getPageSize());
        String userId = SecurityUtils.getCurrentUserId();
        QueryWrapper<ShopInfo> queryWrapper = new QueryWrapper<>();
        UserInfo userInfo = userDao.getUserByUserId(userId);
        Integer userRole = userInfo.getRole();
        if (userRole != null && userRole == 2) {
            queryWrapper.lambda().eq(ShopInfo::getUserId, userId);
            ShopInfo shopInfo = shopDao.selectOne(queryWrapper);
            String shopId = shopInfo.getShopId();
            orderInfo.setShopId(shopId);
        }
        List<OrderInfo> userList = orderDao.listOrder(orderInfo);
        // 包装Page对象
        PageInfo<OrderInfo> pageData = new PageInfo<>(userList);
        return AppResponse.success("查询订单列表成功", pageData);
    }
}
