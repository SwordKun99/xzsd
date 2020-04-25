package com.xzsd.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @DescriptionDemo 订单商品实体类
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@TableName("t_order_commodity")
public class OrderCommodityInfo {

    @TableId
    private String ordercomId;
    private String orderId;
    private String commodityId;
    private double commodityPrice;
    private long commodityNum;
    private double commodityTotalpri;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private long vaersion;

    public String getOrdercomId() {
        return ordercomId;
    }

    public void setOrdercomId(String ordercomId) {
        this.ordercomId = ordercomId;
    }


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


    public double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }


    public long getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(long commodityNum) {
        this.commodityNum = commodityNum;
    }


    public double getCommodityTotalpri() {
        return commodityTotalpri;
    }

    public void setCommodityTotalpri(double commodityTotalpri) {
        this.commodityTotalpri = commodityTotalpri;
    }


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }


    public long getVaersion() {
        return vaersion;
    }

    public void setVaersion(long vaersion) {
        this.vaersion = vaersion;
    }

}
