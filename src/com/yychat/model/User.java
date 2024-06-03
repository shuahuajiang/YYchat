package com.yychat.model;

import java.io.Serializable;

public class User implements Serializable {   //必须实现 Serializable 接口，否则不能再网络上传输User类的对象
    String userName;
    String password;
    String userType;

    String ForgetPassword;


    public String getForgetPassword() {
        return ForgetPassword;
    }

    public void setForgetPassword(String forgetPassword) {
        ForgetPassword = forgetPassword;
    }

    public String getUserType(){
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
