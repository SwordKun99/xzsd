package com.xzsd.pc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("t_goods_hot")
public class GoodsHotInfo {

    @TableId
    private String goodshotId;
    private String goodshotNumber;
    private String commodityId;
    private String commodityNumber;
    private String commodityName;
    private String goodshotNun;
    private String goodshotSum;
    private long isDelete;
    private String createSer;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private long soreNo;
    private long version;

    @TableField(exist = false)
    private int pageSize;
    @TableField(exist = false)
    private int pageNum;


    public String getGoodshotId() {
        return goodshotId;
    }

    public void setGoodshotId(String goodshotId) {
        this.goodshotId = goodshotId;
    }


    public String getGoodshotNumber() {
        return goodshotNumber;
    }

    public void setGoodshotNumber(String goodshotNumber) {
        this.goodshotNumber = goodshotNumber;
    }


    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }


    public String getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(String commodityNumber) {
        this.commodityNumber = commodityNumber;
    }


    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }


    public String getGoodshotNun() {
        return goodshotNun;
    }

    public void setGoodshotNun(String goodshotNun) {
        this.goodshotNun = goodshotNun;
    }


    public String getGoodshotSum() {
        return goodshotSum;
    }

    public void setGoodshotSum(String goodshotSum) {
        this.goodshotSum = goodshotSum;
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
