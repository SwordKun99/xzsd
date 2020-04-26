package com.xzsd.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @DescriptionDemo 用户评价实体类
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@TableName("t_user_evaluation")
public class CustomerEvaluationInfo {

    @TableId
    private String eveluationId;
    private String customerId;
    private String commodityId;
    private String customerName;
    private String orderId;
    private String stoneId;
    private String shopId;
    private String shopName;
    private String starLevel;
    private String evaContent;
    private long isDelete;
    private String createSer;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private String soreNo;
    private long version;

    @TableField(exist = false)
    private int pageSize;
    @TableField(exist = false)
    private int pageNum;
    @TableField(exist = false)
    private String commodityPath;

    public String getEveluationId() {
        return eveluationId;
    }

    public void setEveluationId(String eveluationId) {
        this.eveluationId = eveluationId;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getStoneId() {
        return stoneId;
    }

    public void setStoneId(String stoneId) {
        this.stoneId = stoneId;
    }


    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


    public String getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }


    public String getEvaContent() {
        return evaContent;
    }

    public void setEvaContent(String evaContent) {
        this.evaContent = evaContent;
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

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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


    public void setUpdateTime(java.sql.Timestamp updateTime) {
        this.updateTime = updateTime;
    }


    public String getSoreNo() {
        return soreNo;
    }

    public void setSoreNo(String soreNo) {
        this.soreNo = soreNo;
    }

    public String getCommodityPath() {
        return commodityPath;
    }

    public void setCommodityPath(String commodityPath) {
        this.commodityPath = commodityPath;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
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
}
