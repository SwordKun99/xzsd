package com.xzsd.pc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("t_order_master")
public class OrderInfo {

    @TableId
    private String orderId;
    private String customerName;
    private String shooppingName;
    private String customerId;
    private double orderMoney;
    private double distnctMoney;
    private double shoppingMoney;
    private String orderNumber;
    private long channelType;
    private double feeMoney;
    private double goosPrice;
    private long orderStatus;
    private double paymentMoney;
    private long payStatus;
    private java.sql.Timestamp shoppingTime;
    private java.sql.Timestamp payTime;
    private java.sql.Timestamp receiveTime;
    private String stoneNumber;
    private long isDelete;
    private String createSer;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private long soreNo;
    private long version;
    private String remark;
    private long goodsCnt;

    @TableField(exist = false)
    private int startPage;
    @TableField(exist = false)
    private int pagesize;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getShooppingName() {
        return shooppingName;
    }

    public void setShooppingName(String shooppingName) {
        this.shooppingName = shooppingName;
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


    public double getShoppingMoney() {
        return shoppingMoney;
    }

    public void setShoppingMoney(double shoppingMoney) {
        this.shoppingMoney = shoppingMoney;
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


    public double getGoosPrice() {
        return goosPrice;
    }

    public void setGoosPrice(double goosPrice) {
        this.goosPrice = goosPrice;
    }


    public long getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(long orderStatus) {
        this.orderStatus = orderStatus;
    }


    public double getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(double paymentMoney) {
        this.paymentMoney = paymentMoney;
    }


    public long getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(long payStatus) {
        this.payStatus = payStatus;
    }


    public java.sql.Timestamp getShoppingTime() {
        return shoppingTime;
    }

    public void setShoppingTime(java.sql.Timestamp shoppingTime) {
        this.shoppingTime = shoppingTime;
    }


    public java.sql.Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(java.sql.Timestamp payTime) {
        this.payTime = payTime;
    }


    public java.sql.Timestamp getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(java.sql.Timestamp receiveTime) {
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


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getSoreNo() {
        return soreNo;
    }

    public void setSoreNo(long soreNo) {
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


    public long getGoodsCnt() {
        return goodsCnt;
    }

    public void setGoodsCnt(long goodsCnt) {
        this.goodsCnt = goodsCnt;
    }


    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}
