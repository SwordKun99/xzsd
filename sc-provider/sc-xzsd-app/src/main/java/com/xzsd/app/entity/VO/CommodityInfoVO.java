package com.xzsd.app.entity.VO;


import com.baomidou.mybatisplus.annotation.TableField;
import com.xzsd.app.entity.FileInfo;

import java.util.List;

public class CommodityInfoVO {

    private String commodityId;
    private String commodityNunmer;
    private String commodityName;
    private String stoneId;
    private String stoneName;
    private String commodityImage;
    private String parentCode;
    private String systematicCode;
    private double pringting;
    private double costPrice;
    private double sellPrice;
    private String soldNumber;
    private String channel;
    private String repertory;
    private String introduce;
    private String svaluationStar;
    private String dataIssued;
    private Integer upDownstate;
    private String pageView;
    private String author;
    private String message;
    private String press;
    private Integer isDelete;
    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;
    private String soreNo;
    private Integer version;

    @TableField(exist = false)
    private int pageNum;
    @TableField(exist = false)
    private int pageSize;

    @TableField(exist = false)
    private List<FileInfo> filePath;


    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }


    public String getCommodityNunmer() {
        return commodityNunmer;
    }

    public void setCommodityNunmer(String commodityNunmer) {
        this.commodityNunmer = commodityNunmer;
    }


    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }


    public String getStoneId() {
        return stoneId;
    }

    public void setStoneId(String stoneId) {
        this.stoneId = stoneId;
    }


    public String getStoneName() {
        return stoneName;
    }

    public void setStoneName(String stoneName) {
        this.stoneName = stoneName;
    }


    public String getCommodityImage() {
        return commodityImage;
    }

    public void setCommodityImage(String commodityImage) {
        this.commodityImage = commodityImage;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getSystematicCode() {
        return systematicCode;
    }

    public void setSystematicCode(String systematicCode) {
        this.systematicCode = systematicCode;
    }

    public double getPringting() {
        return pringting;
    }

    public void setPringting(double pringting) {
        this.pringting = pringting;
    }


    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }


    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }


    public String getRepertory() {
        return repertory;
    }

    public void setRepertory(String repertory) {
        this.repertory = repertory;
    }


    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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

    public List<FileInfo> getFilePath() {
        return filePath;
    }

    public void setFilePath(List<FileInfo> filePath) {
        this.filePath = filePath;
    }

    public String getSoldNumber() {
        return soldNumber;
    }

    public void setSoldNumber(String soldNumber) {
        this.soldNumber = soldNumber;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSvaluationStar() {
        return svaluationStar;
    }

    public void setSvaluationStar(String svaluationStar) {
        this.svaluationStar = svaluationStar;
    }

    public Integer getUpDownstate() {
        return upDownstate;
    }

    public void setUpDownstate(Integer upDownstate) {
        this.upDownstate = upDownstate;
    }

    public String getPageView() {
        return pageView;
    }

    public void setPageView(String pageView) {
        this.pageView = pageView;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getSoreNo() {
        return soreNo;
    }

    public void setSoreNo(String soreNo) {
        this.soreNo = soreNo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDataIssued() {
        return dataIssued;
    }

    public void setDataIssued(String dataIssued) {
        this.dataIssued = dataIssued;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}