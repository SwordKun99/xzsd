package com.xzsd.app.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("t_cust_cart")
public class CustCartInfo {

  @TableId
  private String shopcarId;
  private String shopcarNumber;
  private String customerId;
  private String commodityId;
  private String shopId;
  private String shopName;
  private String cnt;
  private long isDelete;
  private String createSer;
  private Date createTime;
  private String updateUser;
  private Date updateTime;
  private long soreNo;
  private long version;

  @TableField(exist = false)
  private int pageSize;
  @TableField(exist = false)
  private int pageNum;


  public String getShopcarId() {
    return shopcarId;
  }

  public void setShopcarId(String shopcarId) {
    this.shopcarId = shopcarId;
  }


  public String getShopcarNumber() {
    return shopcarNumber;
  }

  public void setShopcarNumber(String shopcarNumber) {
    this.shopcarNumber = shopcarNumber;
  }


  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }


  public String getCommodityId() {
    return commodityId;
  }

  public void setCommodityId(String commodityId) {
    this.commodityId = commodityId;
  }


  public String getShopId() {
    return shopId;
  }

  public void setShopId(String shopId) {
    this.shopId = shopId;
  }


  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }


  public String getCnt() {
    return cnt;
  }

  public void setCnt(String cnt) {
    this.cnt = cnt;
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