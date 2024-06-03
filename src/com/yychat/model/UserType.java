package com.yychat.model;

//新增用户类型接口，添加两个用户类型
public interface UserType {
    String USER_LOGIN_VALIDATE = "1";
    String USER_REGISTER = "2";

    String FORGET_PASSWORD = "3";
}
