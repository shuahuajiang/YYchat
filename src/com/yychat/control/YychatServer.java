package com.yychat.control;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.ObjectOutputStream;
import com.yychat.model.User;
import java.net.*;

public class YychatServer {
    ServerSocket ss;
    Socket s;
    public YychatServer(){
        try {
            ss = new ServerSocket(3456);
            System.out.println("服务器启动成功，正在监听3456端口");
            s = ss.accept();   //等待客户端连接
            System.out.println("连接成功："+ s);
            //服务器端接收user对象，并在控制台输出
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            User user = (User) ois.readObject();
            String userName = user.getUserName();
            String password = user.getPassword();
            System.out.println("服务器端收到客户端登录信息userName：" + userName + "password:"+ password);
        } catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
