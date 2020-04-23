package com.xzsd.pc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("t_order_master")
public class OrderInfo {

    @TableId
    private String orderId;
    private String commodityId;
    private String shopId;
    private String customerId;
    private String customerName;
    private String customerPhone;
    private double orderMoney;
    private double distnctMoney;
    private String goodsCnt;
    private String orderNumber;
    private long channelType;
    private double feeMoney;
    private String goosNum;
    private String orderStatus;
    private double paymentMoney;
    private Date payTimeend;
    private Date payTimestart;
    private String payStatus;
    private Date shoppingTime;
    private Date payTime;
    private Date receiveTime;
    private String stoneNumber;
    private long isDelete;
    private String createSer;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private String soreNo;
    private long version;
    private String remark;

    @TableField(exist = false)
    private int pageSize;
    @TableField(exist = false)
    private int pageNum;


    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
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


    public Date getPayTimeend() {
        return payTimeend;
    }

    public void setPayTimeend(Date payTimeend) {
        this.payTimeend = payTimeend;
    }

    public Date getPayTimestart() {
        return payTimestart;
    }

    public void setPayTimestart(Date payTimestart) {
        this.payTimestart = payTimestart;
    }

    public Date getShoppingTime() {
        return shoppingTime;
    }

    public void setShoppingTime(Date shoppingTime) {
        this.shoppingTime = shoppingTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
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


    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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

}
