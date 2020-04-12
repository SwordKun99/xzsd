package com.xzsd.pc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("t_commodity")
public class CommodityInfo {

    @TableId
    private String commodityId;
    private String commodityNunmer;
    private String commodityName;
    private String stoneId;
    private String stoneName;
    private String commodityImage;
    private String codeSortsecond;
    private String codeNamesecond;
    private String codeSortinfirst;
    private String codeNamefirst;
    private double pringting;
    private double costPrice;
    private double sellPrice;
    private long soldNumber;
    private long channel;
    private String repertory;
    private String introduce;
    private long svaluationStar;
    private Date dataIssued;
    private long upDownstate;
    private long pageView;
    private String author;
    private String message;
    private String press;
    private long isDelete;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private long soreNo;
    private long version;

    @TableField(exist = false)
    private int startPage;
    @TableField(exist = false)
    private int pagesize;


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


    public String getCodeSortsecond() {
        return codeSortsecond;
    }

    public void setCodeSortsecond(String codeSortsecond) {
        this.codeSortsecond = codeSortsecond;
    }

    public String getCodeNamesecond() {
        return codeNamesecond;
    }

    public void setCodeNamesecond(String codeNamesecond) {
        this.codeNamesecond = codeNamesecond;
    }

    public String getCodeSortinfirst() {
        return codeSortinfirst;
    }

    public void setCodeSortinfirst(String codeSortinfirst) {
        this.codeSortinfirst = codeSortinfirst;
    }

    public String getCodeNamefirst() {
        return codeNamefirst;
    }

    public void setCodeNamefirst(String codeNamefirst) {
        this.codeNamefirst = codeNamefirst;
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


    public long getSoldNumber() {
        return soldNumber;
    }

    public void setSoldNumber(long soldNumber) {
        this.soldNumber = soldNumber;
    }


    public long getChannel() {
        return channel;
    }

    public void setChannel(long channel) {
        this.channel = channel;
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


    public long getSvaluationStar() {
        return svaluationStar;
    }

    public void setSvaluationStar(long svaluationStar) {
        this.svaluationStar = svaluationStar;
    }


    public Date getDataIssued() {
        return dataIssued;
    }

    public void setDataIssued(Date dataIssued) {
        this.dataIssued = dataIssued;
    }

    public long getUpDownstate() {
        return upDownstate;
    }

    public void setUpDownstate(long upDownstate) {
        this.upDownstate = upDownstate;
    }


    public long getPageView() {
        return pageView;
    }

    public void setPageView(long pageView) {
        this.pageView = pageView;
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


    public long getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(long isDelete) {
        this.isDelete = isDelete;
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
