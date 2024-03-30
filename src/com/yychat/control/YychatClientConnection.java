package com.yychat.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import java.io.OutputStream;
import java.io.ObjectOutputStream;

import com.yychat.model.MessageType;
import  com.yychat.model.User;

import javax.crypto.interfaces.PBEKey;

import com.yychat.model.Message;
import com.yychat.view.ClientLogin;

public class YychatClientConnection {
//    Socket s;
    public static Socket s;  //定义 public static 类型的 socket 对象
    public YychatClientConnection(){
        try {
            s = new Socket("127.0.0.1",3456);  //创建socket对象，和服务器建立连接
            System.out.println("客户端连接成功" + s);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
//    public void loginValidate(User user){
    public  boolean loginValidate(User user){   //客户端接收Message对象，成功则返回ture
        boolean loginSuccess = false;   //默认登录失败
        try {
            OutputStream os = s.getOutputStream(); //通过 socket 获得字节输出
            ObjectOutputStream oos = new ObjectOutputStream(os);  //把字节输出流包装成对象输出流对象
            oos.writeObject(user);    //向服务器端发送user 对象

            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Message mess = (Message) ois.readObject();

            if (mess.getMessageType().equals(MessageType.LOGIN_VALIDATE_SUCCESS)) {

                loginSuccess = true;  //登录验证成功

                new ClientReceiverThread(s).start();  //创建客户端接收线程，用来接收从服务器端发来信息

            }else
                s.close();

        }  catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  loginSuccess;
    }
}
