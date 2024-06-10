package com.yychat.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import com.yychat.model.MessageType;
import  com.yychat.model.User;
import com.yychat.model.Message;

public class YychatClientConnection {
    //    Socket s;
    public static Socket s;

    public YychatClientConnection() {
        try {
            s = new Socket("127.0.0.1", 3456);  //创建socket对象，和服务器建立连接
            System.out.println("客户端连接成功" + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //新增registerUser（）方法
    public static boolean registerUser(User user) {
        boolean registerSuccess = false;
        //发送 user 对象
        try {
            OutputStream os = s.getOutputStream();  //通过 Socket 获得字节输出流对象
            ObjectOutputStream oos = new ObjectOutputStream(os);  //把字节输出流包装成对象输出流对象
            oos.writeObject(user);//向服务器端发送user对象

            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Message mess = (Message) ois.readObject();   //接收服务器端发送的Message 对象

            if (mess.getMessageType().equals(MessageType.USER_REGISTER_SUCCESS)) {
                registerSuccess = true;//注册成功
            }
            s.close(); //不管注册是否成功都要关闭客户端的Socket对象
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return registerSuccess;
    }


    //新增ForgetPass()方法
    public static boolean ForgetPassword(User user)
    {
        try
        {
            OutputStream os = s.getOutputStream();// 通过Socket获得字节输出流对象
            ObjectOutputStream oos = new ObjectOutputStream(os);// 把字节输出流包装成对象输出流对象
            oos.writeObject(user);

            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Message mess = (Message) ois.readObject();// 接收服务器端发送的Message对象
            if(mess.getMessageType().equals(MessageType.FORGET_SUCCESS))
            {
                return true;
            }else if(mess.getMessageType().equals(MessageType.FORGET_FALSE))
            {
                return false;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }


    //使用数据库中好友来更新好友列表
    public Message loginValidate1(User user) {
        Message mess = null;   //默认登陆验证失败
        try {
            OutputStream os = s.getOutputStream();  //通过 Socket 获得字节输出流对象
            ObjectOutputStream oos = new ObjectOutputStream(os);//把字节输出流包装成对象输出流对象
            oos.writeObject(user);  //向服务器端发送user 对象

            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            mess = (Message) ois.readObject();  //接收服务器增发送的Message 对象
            if (mess.getMessageType().equals(MessageType.LOGIN_VALIDATE_SUCCESS)) {

                new ClientReceiverThread(s).start();
            } else
                s.close();  //登陆密码验证失败，关闭客户端的Socket对象
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return mess;
    }
}
