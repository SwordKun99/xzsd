package com.xzsd.pc.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @DescriptionDemo 店长实体类
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@TableName("t_message")
public class MessageInfo {

  @TableId
  private String messageId;
  private String messageName;
  private String messageNumber;


  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }


  public String getMessageName() {
    return messageName;
  }

  public void setMessageName(String messageName) {
    this.messageName = messageName;
  }


  public String getMessageNumber() {
    return messageNumber;
  }

  public void setMessageNumber(String messageNumber) {
    this.messageNumber = messageNumber;
  }

}
