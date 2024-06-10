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

    String ADD_NEW_FRIEND = "10"; //客户端发送到服务器的请求添加好友消息

    String ADD_NEW_FRIEND_FAILURE_NO_USER = "11";  //添加好友失败
    String ADD_NEW_FRIEND_FAILURE_ALREADY_FRIEND = "12";//添加失败（已经是好友了）
    String ADD_NEW_FRIEND_SUCCESS = "13"; //添加成功

    String USER_EXIT_SERVER_THREAD_CLOSE = "14";
    String USER_EXIT_CLIENT_THREAD_CLOSE = "15";

    String DELETE_FRIEND = "16";
    String DELETE_FRIEND_FAILURE_ALREADY_FRIEND = "17";
    String DELETE_FRIEND_SUCCESS = "18";
    String DELETE_FRIEND_FAILURE_NO_USER = "19";

    String FORGET_SUCCESS = "20";
    String FORGET_FALSE = "21";
    String CREATE_MULTI_PERSON_CHAT = "29";
    String CREATE_MULTI_PERSON_CHAT_SUCCESS = "30";
    String CREATE_MULTI_PERSON_CHAT_FAILURE = "31";
    String JOIN_MULTI_PERSON_CHAT = "32";
    String JOIN_MULTI_PERSON_CHAT_SUCCESS = "33";
    String JOIN_MULTI_PERSON_CHAT_FAILURE = "34";
    String SEND_TO_MULTI_PERSON_CHAT_CLIENT = "35";
    String RECEIVER_FROM_MULTI_PERSON_CHAT_SERVER = "36";
    String MULTI_PERSON_CHAT_NOT_EXITS = "39";
}
