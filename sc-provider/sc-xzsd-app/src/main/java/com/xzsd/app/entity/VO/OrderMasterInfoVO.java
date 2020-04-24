package com.xzsd.app.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

public class OrderMasterInfoVO {

    private String orderId;
    private String commodityId;
    private String shopId;
    private String customerPhone;
    private String customerName;
    private String customerId;
    private double orderMoney;
    private double distnctMoney;
    private String goodsCnt;
    private String orderNumber;
    private long channelType;
    private double feeMoney;
    private String goosNum;
    private String orderStatus;
    private double paymentMoney;
    private String payTimeend;
    private String payTimestart;
    private String payStatus;
    private String shoppingTime;
    private String payTime;
    private String receiveTime;
    private String stoneNumber;
    private long isDelete;
    private String createSer;
    private String createTime;
    private String updateUser;
    private String updateTime;
    private String soreNo;
    private long version;
    private String remark;

    private int pageNum;
    private int pageSize;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }


    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }


    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(double orderMoney) {
        this.orderMoney = orderMoney;
    }


    public double getDistnctMoney() {
        return distnctMoney;
    }

    public void setDistnctMoney(double distnctMoney) {
        this.distnctMoney = distnctMoney;
    }


    public String getGoodsCnt() {
        return goodsCnt;
    }

    public void setGoodsCnt(String goodsCnt) {
        this.goodsCnt = goodsCnt;
    }


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public long getChannelType() {
        return channelType;
    }

    public void setChannelType(long channelType) {
        this.channelType = channelType;
    }


    public double getFeeMoney() {
        return feeMoney;
    }

    public void setFeeMoney(double feeMoney) {
        this.feeMoney = feeMoney;
    }


    public String getGoosNum() {
        return goosNum;
    }

    public void setGoosNum(String goosNum) {
        this.goosNum = goosNum;
    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    public double getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(double paymentMoney) {
        this.paymentMoney = paymentMoney;
    }


    public String getPayTimeend() {
        return payTimeend;
    }

    public void setPayTimeend(String payTimeend) {
        this.payTimeend = payTimeend;
    }


    public String getPayTimestart() {
        return payTimestart;
    }

    public void setPayTimestart(String payTimestart) {
        this.payTimestart = payTimestart;
    }


    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }


    public String getShoppingTime() {
        return shoppingTime;
    }

    public void setShoppingTime(String shoppingTime) {
        this.shoppingTime = shoppingTime;
    }


    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }


    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }


    public String getStoneNumber() {
        return stoneNumber;
    }

    public void setStoneNumber(String stoneNumber) {
        this.stoneNumber = stoneNumber;
    }


    public long getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(long isDelete) {
        this.isDelete = isDelete;
    }


    public String getCreateSer() {
        return createSer;
    }

    public void setCreateSer(String createSer) {
        this.createSer = createSer;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }


    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


    public String getSoreNo() {
        return soreNo;
    }

    public void setSoreNo(String soreNo) {
        this.soreNo = soreNo;
    }


    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
