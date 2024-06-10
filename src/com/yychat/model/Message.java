package com.yychat.model;

import java.io.Serializable;

import java.util.Date;

public class Message implements Serializable,MessageType{
    String messageType;
    //增加3个成员变量和getters、setters方法
    String sender;
    String receiver;
    String content;

    String chatName;
    String chatNumber;

    //保存聊天信息
    Date sendTime;

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }




    public String getMessageType(){
        return messageType;
    }
    public void setMessageType(String messageType){
        this.messageType = messageType;
    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChatNumber() {
        return this.chatNumber;
    }
    public void setChatNumber(String chatNumber) {
        this.chatNumber = chatNumber;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
    public String getChatName() {
        return this.chatName;
    }
}
