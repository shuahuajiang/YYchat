package com.yychat.control;

import java.io.IOException;
import java.net.Socket;

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
}
