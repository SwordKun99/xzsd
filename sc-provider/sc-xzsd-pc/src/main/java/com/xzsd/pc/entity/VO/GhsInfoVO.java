package com.xzsd.pc.entity.VO;

/**
 * @DescriptionDemo 热门商品位展示数量VO实体类
 * @Author SwordKun.
 * @Date 2020-03-28
 */
public class GhsInfoVO {

    private String ghsId;
    private String ghsCode;
    private String ghsSum;
    private Integer isDelete;
    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;
    private String soreNo;
    private Integer version;


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
