package com.yychat.control;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

import com.yychat.model.*;
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


                //服务器端接收user对象，并在控制台输出
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());  //创建对象输入流对象
                User user = (User) ois.readObject();   //接收user对象
                String userName = user.getUserName();
                String password = user.getPassword();
                System.out.println(userName + "连接成功：" + s);
                System.out.println("服务器端收到客户端登录信息userName：" + userName + "password:" + password);

                //创建对象输出流对象
                ObjectOutputStream  oos = new ObjectOutputStream(s.getOutputStream());
                Message mess = new Message();
                if (password.equals("123456")){
                    System.out.println("密码验证通过");
                    mess.setMessageType(MessageType.LOGIN_VALIDATE_SUCCESS);
                    oos.writeObject(mess);  //发送mess对象到客户端

                    /*用户登陆成功后，服务器为每一个用户创建服务线程，
                    由于可能有多个客户同时向服务器发送信息,需要为每一个用户创建接收线程*/
                    new ServerReceiverThread(s).start(); //启动线程
                    System.out.println("启动线程成功");
                }else {
                    System.out.println("密码验证失败");
                    mess.setMessageType(MessageType.LOGIN_VALIDATE_FAILURE);//登录验证失败
                    oos.writeObject(mess);  //发送mess对象到客户端
                    s.close();
                }


//                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
//                oos.writeObject(mess);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
