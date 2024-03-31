package com.yychat.model;

public interface MessageType {
    String LOGIN_VALIDATE_SUCCESS = "1";
    String LOGIN_VALIDATE_FAILURE = "2";
    String COMMON_CHAT_MESSAGE = "3";  //增加普通聊天信息类型

    String REQUEST_ONLINE_FRIEND = "4";  //客户端请求获得在线好友的名字
    String RESPONSE_ONLINE_FRIEND = "5"; //服务器返回在线好友的名字

    String NEW_ONLINE_TO_ALL_FRIEND = "6";
    String NEW_ONLINE_FRIEND = "7";

    String USER_REGISTER_SUCCESS = "8";
    String USER_REGISTER_FAILURE = "9";
}
