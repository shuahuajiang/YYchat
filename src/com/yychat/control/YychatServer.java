package com.yychat.control;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;



import com.yychat.model.MessageType;
import com.yychat.model.User;
import java.net.*;

import com.yychat.model.Message;
import java.io.ObjectOutputStream;

public class YychatServer {
    ServerSocket ss;
    Socket s;
    public YychatServer(){
        try {
            ss = new ServerSocket(3456);
            System.out.println("服务器启动成功，正在监听3456端口");
            while (true) {     //服务器端要不断等待客户端建立连接，否则只能连接一个客户
                s = ss.accept();   //等待客户端连接
                System.out.println("连接成功：" + s);

                //服务器端接收user对象，并在控制台输出
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());  //创建对象输入流对象
                User user = (User) ois.readObject();   //接收user对象
                String userName = user.getUserName();
                String password = user.getPassword();
                System.out.println("服务器端收到客户端登录信息userName：" + userName + "password:" + password);

                //发送Message对象到客户端
                Message mess = new Message();
                if (password.equals("123456")) {
                    System.out.println("密码验证通过");

                    mess.setMessageType(MessageType.LOGIN_VALIDATE_SUCCESS);  //登录验证成功
                } else {
                    System.out.println("密码验证失败");
                    mess.setMessageType(MessageType.LOGIN_VALIDATE_FAILURE);//登录验证失败
                }
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(mess);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
