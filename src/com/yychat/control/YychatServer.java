package com.yychat.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class YychatServer {
    ServerSocket ss;
    Socket s;
    public YychatServer(){
        try {
            ss = new ServerSocket(3456);
            System.out.println("服务器启动成功，正在监听3456端口");
            s = ss.accept();   //等待客户端连接
            System.out.println("连接成功："+ s);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
