package com.xzsd.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @DescriptionDemo 司机实体类
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@TableName("t_drive")
public class DriveInfo {

    @TableId
    private String driveId;
    private String driveCode;
    private String driveNo;
    private String drivePassword;
    private String driveName;
    private String drivePhone;
    private String driveEmail;
    private String driveIdcard;
    private String driveImage;
    private String provinceId;
    private String cityId;
    private String districtId;
    private Integer isDelete;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private String soreNo;
    private Integer version;
    private String invitation;
    private String driverPath;

    public String getDriverPath() {
        return driverPath;
    }

    public void setDriverPath(String driverPath) {
        this.driverPath = driverPath;
    }

    @TableField(exist = false)
    private int pageNum;
    @TableField(exist = false)
    private int pageSize;

    public String getInvitation() {
        return invitation;
    }

    public void setInvitation(String invitation) {
        this.invitation = invitation;
    }

    public String getDriveId() {
        return driveId;
    }

    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }


    public String getDriveCode() {
        return driveCode;
    }

    public void setDriveCode(String driveCode) {
        this.driveCode = driveCode;
    }


    public String getDriveNo() {
        return driveNo;
    }

    public void setDriveNo(String driveNo) {
        this.driveNo = driveNo;
    }


    public String getDrivePassword() {
        return drivePassword;
    }

    public void setDrivePassword(String drivePassword) {
        this.drivePassword = drivePassword;
    }


    public String getDriveName() {
        return driveName;
    }

    public void setDriveName(String driveName) {
        this.driveName = driveName;
    }


    public String getDrivePhone() {
        return drivePhone;
    }

    public void setDrivePhone(String drivePhone) {
        this.drivePhone = drivePhone;
    }


    public String getDriveEmail() {
        return driveEmail;
    }

    public void setDriveEmail(String driveEmail) {
        this.driveEmail = driveEmail;
    }


    public String getDriveIdcard() {
        return driveIdcard;
    }

    public void setDriveIdcard(String driveIdcard) {
        this.driveIdcard = driveIdcard;
    }


    public String getDriveImage() {
        return driveImage;
    }

    public void setDriveImage(String driveImage) {
        this.driveImage = driveImage;
    }


    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }


    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }


    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
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
}
