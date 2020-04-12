package com.xzsd.pc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_menu")
public class MenuInfo {

    @TableId
    private String menuId;
    private String menuNumber;
    private String menuName;
    private String menuSelection;
    private long menuJudge;
    private String menuRout;
    private long isDelete;
    private String createUser;
    private java.sql.Timestamp createTime;
    private String updateUser;
    private java.sql.Timestamp updateTime;
    private String soreNo;
    private long version;
    private String remake;

    @TableField(exist = false)
    private int startPage;
    @TableField(exist = false)
    private int pagesize;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }


    public String getMenuNumber() {
        return menuNumber;
    }

    public void setMenuNumber(String menuNumber) {
        this.menuNumber = menuNumber;
    }


    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }


    public String getMenuSelection() {
        return menuSelection;
    }

    public void setMenuSelection(String menuSelection) {
        this.menuSelection = menuSelection;
    }


    public long getMenuJudge() {
        return menuJudge;
    }

    public void setMenuJudge(long menuJudge) {
        this.menuJudge = menuJudge;
    }


    public String getMenuRout() {
        return menuRout;
    }

    public void setMenuRout(String menuRout) {
        this.menuRout = menuRout;
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


    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }


    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }


    public java.sql.Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.sql.Timestamp updateTime) {
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


    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
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
