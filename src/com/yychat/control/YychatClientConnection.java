package com.yychat.control;

import java.io.IOException;
import java.net.Socket;

import java.io.OutputStream;
import java.io.ObjectOutputStream;

import  com.yychat.model.User;

import javax.crypto.interfaces.PBEKey;

public class YychatClientConnection {
    Socket s;
    public YychatClientConnection(){
        try {
            s = new Socket("127.0.0.1",3456);
            System.out.println("客户端连接成功" + s);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loginValidate(User user){
        try {
            OutputStream os = s.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(user);
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
