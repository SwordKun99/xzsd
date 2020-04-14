package com.xzsd.pc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("t_focus_rotations")
public class ImageInfo {

    @TableId
    private String imageId;
    private String imageNumber;
    private String imageNo;
    private String imageUr1;
    private String commodityId;
    private String commodityName;
    private String imageType;
    private String secondarDirectory;
    private String imageTitle;
    private java.sql.Timestamp periodStrat;
    private java.sql.Timestamp periodValidtyOver;
    private long isDelete;
    private String createSer;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private long soreNo;
    private long version;

    @TableField(exist = false)
    private int startPage;
    @TableField(exist = false)
    private int pagesize;


    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }


    public String getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(String imageNumber) {
        this.imageNumber = imageNumber;
    }


    public String getImageNo() {
        return imageNo;
    }

    public void setImageNo(String imageNo) {
        this.imageNo = imageNo;
    }


    public String getImageUr1() {
        return imageUr1;
    }

    public void setImageUr1(String imageUr1) {
        this.imageUr1 = imageUr1;
    }


    public String getCommodityNumber() {
        return commodityId;
    }

    public void setCommodityNumber(String commodityId) {
        this.commodityId = commodityId;
    }


    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }


    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }


    public String getSecondarDirectory() {
        return secondarDirectory;
    }

    public void setSecondarDirectory(String secondarDirectory) {
        this.secondarDirectory = secondarDirectory;
    }


    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }


    public java.sql.Timestamp getPeriodStrat() {
        return periodStrat;
    }

    public void setPeriodStrat(java.sql.Timestamp periodStrat) {
        this.periodStrat = periodStrat;
    }


    public java.sql.Timestamp getPeriodValidtyOver() {
        return periodValidtyOver;
    }

    public void setPeriodValidtyOver(java.sql.Timestamp periodValidtyOver) {
        this.periodValidtyOver = periodValidtyOver;
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
