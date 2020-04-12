package com.xzsd.pc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("t_commodity_classification")
public class CommodityClassInfo implements Serializable {

    @TableId
    private String systematicId;
    private String systematicCode;
    private String systematicName;
    private String commodityId;
    private String parentCode;
    private String parentName;
    private String systematicRemark;
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


    public String getSystematicId() {
        return systematicId;
    }

    public void setSystematicId(String systematicId) {
        this.systematicId = systematicId;
    }


    public String getSystematicCode() {
        return systematicCode;
    }

    public void setSystematicCode(String systematicCode) {
        this.systematicCode = systematicCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getSystematicName() {
        return systematicName;
    }

    public void setSystematicName(String systematicName) {
        this.systematicName = systematicName;
    }


    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }


    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getSystematicRemark() {
        return systematicRemark;
    }

    public void setSystematicRemark(String systematicRemark) {
        this.systematicRemark = systematicRemark;
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
