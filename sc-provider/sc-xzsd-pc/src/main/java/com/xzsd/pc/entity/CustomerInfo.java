package com.xzsd.pc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("t_customer_information")
public class CustomerInfo implements Serializable {
    @TableId
    private String customerId;
    private String customerNumber;
    private String customerName;
    private String custormerSex;
    private String customerPhone;
    private String customerEmail;
    private String customerIdcode;
    private String custormerNo;
    private String customerPassword;
    private String customerAddress;
    private long isDelete;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private long soreNo;
    private long version;
    private String customerScore;

    @TableField(exist = false)
    private int pageSize;
    @TableField(exist = false)
    private int pageNum;


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustormerSex() {
        return custormerSex;
    }

    public void setCustormerSex(String custormerSex) {
        this.custormerSex = custormerSex;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerIdcode() {
        return customerIdcode;
    }

    public void setCustomerIdcode(String customerIdcode) {
        this.customerIdcode = customerIdcode;
    }

    public String getCustormerNo() {
        return custormerNo;
    }

    public void setCustormerNo(String custormerNo) {
        this.custormerNo = custormerNo;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
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

    public String getCustomerScore() {
        return customerScore;
    }

    public void setCustomerScore(String customerScore) {
        this.customerScore = customerScore;
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

    @Override
    public String toString() {
        return "CustomerInfo{" +
                "customerId='" + customerId + '\'' +
                ", customerNumber='" + customerNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", custormerSex='" + custormerSex + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerIdcode='" + customerIdcode + '\'' +
                ", custormerNo='" + custormerNo + '\'' +
                ", customerPassword='" + customerPassword + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", isDelete=" + isDelete +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", soreNo=" + soreNo +
                ", version=" + version +
                ", customerScore='" + customerScore + '\'' +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                '}';
    }

}
