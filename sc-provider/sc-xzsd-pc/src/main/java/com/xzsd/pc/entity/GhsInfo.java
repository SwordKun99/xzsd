package com.xzsd.pc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("t_ghs")
public class GhsInfo {

    @TableId
    private String ghsId;
    private String ghsCode;
    private String ghsSum;
    private long isDelete;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private long soreNo;
    private long version;


    public String getGhsId() {
        return ghsId;
    }

    public void setGhsId(String ghsId) {
        this.ghsId = ghsId;
    }


    public String getGhsCode() {
        return ghsCode;
    }

    public void setGhsCode(String ghsCode) {
        this.ghsCode = ghsCode;
    }


    public String getGhsSum() {
        return ghsSum;
    }

    public void setGhsSum(String ghsSum) {
        this.ghsSum = ghsSum;
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

}
