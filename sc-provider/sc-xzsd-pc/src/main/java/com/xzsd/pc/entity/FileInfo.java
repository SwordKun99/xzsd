package com.xzsd.pc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_file")
public class FileInfo {
  @TableId
  private String fileId;
  private String pathKey;
  private String bizId;
  private String path;
  private long isDelete;
  private String createSer;
  private java.sql.Timestamp createTime;
  private String updateUser;
  private java.sql.Timestamp updateTime;
  private long soreNo;
  private long version;


  public String getPathKey() {
    return pathKey;
  }

  public void setPathKey(String pathKey) {
    this.pathKey = pathKey;
  }

  public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }


  public String getBizId() {
    return bizId;
  }

  public void setBizId(String bizId) {
    this.bizId = bizId;
  }


  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
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
