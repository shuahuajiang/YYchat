package com.yychat.model;

import java.io.Serializable;

public class Message implements Serializable,MessageType{
    String messageType;

    public String getMessageType(){
        return messageType;
    }
    public void setMessageType(String messageType){
        this.messageType = messageType;
    }
}
