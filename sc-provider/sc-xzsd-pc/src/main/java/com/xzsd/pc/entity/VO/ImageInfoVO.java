package com.xzsd.pc.entity.VO;

/**
 * @DescriptionDemo 轮播图VO实体类
 * @Author SwordKun.
 * @Date 2020-03-28
 */
public class ImageInfoVO {

    private String imageId;
    private String imageNumber;
    private String imageNo;
    private String commodityId;
    private String commodityName;
    private Integer imageType;
    private String secondarDirectory;
    private String imageTitle;
    private String periodStrat;
    private String periodOver;
    private Integer isDelete;
    private String createSer;
    private String createTime;
    private String updateUser;
    private String updateTime;
    private String soreNo;
    private Integer version;
    private Integer upDownstate;
    private String parentCode;
    private String systematicCode;

    private int PageNum;
    private int pageSize;
    private String imagePath;


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


    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }


    public String getSecondarDirectory() {
        return secondarDirectory;
    }

    public void setSecondarDirectory(String secondarDirectory) {
        this.secondarDirectory = secondarDirectory;
    }


    public Integer getImageType() {
        return imageType;
    }

    public void setImageType(Integer imageType) {
        this.imageType = imageType;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
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


    public int getPageNum() {
        return PageNum;
    }

    public void setPageNum(int pageNum) {
        PageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getPeriodStrat() {
        return periodStrat;
    }

    public void setPeriodStrat(String periodStrat) {
        this.periodStrat = periodStrat;
    }

    public String getPeriodOver() {
        return periodOver;
    }

    public void setPeriodOver(String periodOver) {
        this.periodOver = periodOver;
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

    public Integer getUpDownstate() {
        return upDownstate;
    }

    public void setUpDownstate(Integer upDownstate) {
        this.upDownstate = upDownstate;
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
}
