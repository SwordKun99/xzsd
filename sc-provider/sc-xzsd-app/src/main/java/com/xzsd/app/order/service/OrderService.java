package com.xzsd.app.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.neusoft.util.UUIDUtils;
import com.xzsd.app.dao.*;
import com.xzsd.app.entity.*;
import com.xzsd.app.entity.VO.OrderMasterInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-04-23
 */
@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    @Resource
    private OrderDao orderDao;

    @Resource
    private CustomerDao customerDao;

    @Resource
    private ShopDao shopDao;

    @Resource
    private CommodityDao commodityDao;

    @Resource
    private OrderCommodityDao orderCommodityDao;

    @Resource
    private UserDao userDao;

    /**
     * order 新增订单
     *
     * @param orderMasterInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-23
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveOrder(OrderMasterInfo orderMasterInfo) {
        String customerId = SecurityUtils.getCurrentUserId();
        String orderId = UUIDUtils.getUUID();
        String[] goodId = orderMasterInfo.getCommodityId().split(",");
        String[] goodMoney = orderMasterInfo.getDistnctMoney().split(",");
        String[] goodNum = orderMasterInfo.getGoosNum().split(",");
        double totlePrice = 0;
        Integer goodCnt = 0;
        for (int i = 0; i < goodMoney.length; i++) {
            //校验库存
            CommodityInfo commodityInfo = commodityDao.selectById(goodId[i]);
            if (commodityInfo != null) {
                if (commodityInfo.getUpDownstate() == '0') {
                    return AppResponse.bizError(commodityInfo.getCommodityName() + "商品已售罄，请重试！");
                } else if (commodityInfo.getUpDownstate() == '2' || commodityInfo.getUpDownstate() == '3') {
                    return AppResponse.bizError(commodityInfo.getCommodityName() + "商品已下架，请重试！");
                } else if (commodityInfo.getUpDownstate() == '1') {
                    int kc = Integer.parseInt(commodityInfo.getRepertory());
                    if (kc < Integer.parseInt(goodNum[i])) {
                        return AppResponse.bizError(commodityInfo.getCommodityName() + "商品库存不足，请重试！");
                    }
                    //更新库存
                    int newkc = kc - Integer.parseInt(goodNum[i]);
                    commodityInfo.setRepertory(Integer.toString(newkc));
                    commodityDao.updateById(commodityInfo);
                }
            } else {
                return AppResponse.bizError("未找到商品，请重试！");
            }
            totlePrice = totlePrice + Double.parseDouble(goodNum[i]) * Double.parseDouble(goodMoney[i]);
            goodCnt += Integer.parseInt(goodNum[i]);
            //添加订单商品表
            OrderCommodityInfo orderCommodityInfo = new OrderCommodityInfo();
            orderCommodityInfo.setOrdercomId(UUIDUtils.getUUID());
            orderCommodityInfo.setCommodityId(commodityInfo.getCommodityId());
            orderCommodityInfo.setCommodityNum(Integer.parseInt(goodNum[i]));
            orderCommodityInfo.setCommodityPrice(Double.parseDouble(goodMoney[i]));
            orderCommodityInfo.setCommodityTotalpri(Double.parseDouble(goodNum[i]) * Double.parseDouble(goodMoney[i]));
            orderCommodityInfo.setCreateTime(new Date());
            orderCommodityInfo.setVaersion(0);
            orderCommodityInfo.setCreateUser(customerId);
            orderCommodityInfo.setOrderId(orderId);
            orderCommodityDao.insert(orderCommodityInfo);
        }
        //判断客户是否绑定邀请码及邀请码是否过期
        ShopInfo shopInfo = shopDao.selectById(orderMasterInfo.getShopId());
        CustomerInfo customerInfo = customerDao.selectById(customerId);
        if (customerInfo.getInvitation() == null || customerInfo.getInvitation() != shopInfo.getInvitation()) {
            return AppResponse.bizError("请前往绑定或更新邀请码！");
        }
        //添加订单表
        orderMasterInfo.setOrderMoney(totlePrice);
        orderMasterInfo.setGoodsCnt(goodCnt);
        orderMasterInfo.setOrderId(orderId);
        orderMasterInfo.setOrderNumber(StringUtil.getCommonCode(4));
        orderMasterInfo.setCreateTime(new Date());
        orderMasterInfo.setCustomerId(customerId);
        orderMasterInfo.setCreateSer(customerId);
        orderMasterInfo.setCustomerName(customerInfo.getCustomerName());
        orderMasterInfo.setCustomerPhone(customerInfo.getCustomerPhone());
        orderMasterInfo.setShopAddress(shopInfo.getShopAddrees());
        orderMasterInfo.setVersion(0);
        orderMasterInfo.setIsDelete(0);
        orderMasterInfo.setOrderStatus("1");
        orderMasterInfo.setPayStatus("0");
        orderMasterInfo.setPayTime(new Date());
        Integer count = orderDao.insert(orderMasterInfo);
        if (0 == count) {
            return AppResponse.bizError("下单失败，请重试！");
        }
        return AppResponse.success("下单成功！");
    }

    /**
     * order 修改订单状态
     *
     * @param orderMasterInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-24
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateOrderStatus(OrderMasterInfoVO orderMasterInfoVO) {
        //获取当前登录客户id
        String customerId = SecurityUtils.getCurrentUserId();
        QueryWrapper<OrderMasterInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderMasterInfo::getOrderStatus, orderMasterInfoVO.getOrderStatus());
        queryWrapper.lambda().eq(OrderMasterInfo::getIsDelete, 0);
        Integer count = orderDao.selectCount(queryWrapper);
        if (0 != count) {
            return AppResponse.bizError("订单状态已存在，请重新选择！");
        }
        //修改订单状态  客户端：（1：已下单、2：未取货、3：已取货、4：未评价、5：已评价、6：已取消取货、7、取消订单）
        // 店长端：（1：发货中、2：确认到货、3：确认取货、4、确认取货、4、确认取货、6取消取货、7、取消到货）
        OrderMasterInfo orderMasterInfoVO1 = orderDao.selectById(orderMasterInfoVO.getOrderId());
        if (orderMasterInfoVO1 == null) {
            return AppResponse.bizError("查询不到该订单，请重试！");
        }
        OrderMasterInfo orderMasterInfo = new OrderMasterInfo();
        BeanUtils.copyProperties(orderMasterInfoVO, orderMasterInfo);
        orderMasterInfo.setVersion(orderMasterInfo.getVersion() + 1);
        orderMasterInfo.setUpdateUser(customerId);
        orderMasterInfo.setUpdateTime(new Date());
        int count1 = orderDao.updateById(orderMasterInfo);
        if (0 == count1) {
            return AppResponse.versionError("订单状态有变化，请刷新！");
        }
        return AppResponse.success("订单状态修改成功！");
    }

    /**
     * order 查询订单详情
     *
     * @param orderId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-24
     */
    public AppResponse orderInfo(String orderId) {
        //1、查询订单详情
        OrderMasterInfo orderMasterInfo = orderDao.selectById(orderId);
        if (orderMasterInfo == null) {
            return AppResponse.bizError("订单不存在，请重试！");
        }
        //2、找到店铺信息
        ShopInfo shopInfo = shopDao.selectById(orderMasterInfo.getShopId());
        orderMasterInfo.setShopName(shopInfo.getShopName());
        //3、找到订单商品list
        List<CommodityInfo> commodityInfoList = orderDao.listOrderCommodity(orderMasterInfo);
        orderMasterInfo.setCommodityInfoList(commodityInfoList);
        return AppResponse.success("查询订单详情成功", orderMasterInfo);
    }

    /**
     * order 查询订单列表
     *
     * @param orderMasterInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-24
     */
    public AppResponse listOrder(OrderMasterInfo orderMasterInfo) {
        String customerId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.selectById(customerId);
        if (userInfo.getRole() == '2') {//店长
            QueryWrapper<ShopInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(ShopInfo::getUserId, customerId);
            ShopInfo shopInfo = shopDao.selectOne(queryWrapper);
            orderMasterInfo.setShopId(shopInfo.getShopId());
        } else if (userInfo.getRole() == '3') {//客户
            orderMasterInfo.setCustomerId(customerId);
        }
        //根据条件找到符合条件的订单
        List<OrderMasterInfo> orderMasterInfoList = orderDao.listOrder(orderMasterInfo);
        // 包装Page对象
        PageInfo<OrderMasterInfo> pageData = new PageInfo<>(orderMasterInfoList);
        if (pageData.getList() != null && pageData.getList().size() > 0) {
            List<OrderMasterInfo> masterInfoList = pageData.getList();
            for (OrderMasterInfo masterInfo : masterInfoList) {
                List<CommodityInfo> commodityInfoList = orderDao.listOrderCommodity(masterInfo);
                masterInfo.setCommodityInfoList(commodityInfoList);
            }
        }
        return AppResponse.success("查询订单列表成功", pageData);
    }
}
