package com.yychat.control;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.sun.xml.internal.ws.resources.SenderMessages;
import com.yychat.model.Message;
import com.yychat.model.MessageType;

import java.io.ObjectOutputStream;

import java.util.Iterator;
import java.util.Set;

public class ServerReceiverThread extends Thread{   //在服务器ServerReceiverThread线程类
    Socket s ;
    public ServerReceiverThread(Socket s ){
        this.s = s;
    }
    public void run(){
        while (true){
            try {
                //创建对象输入流
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message mess = (Message) ois.readObject();
                if (mess.getMessageType().equals(MessageType.COMMON_CHAT_MESSAGE)){
                    System.out.println(mess.getSender() + "对" + mess.getReceiver()
                    + "说：" + mess.getContent());

//                  从 hmsocket 中拿到接收方的 socket 对象，然后转发聊天信息
                    String receiver = mess.getReceiver();
                    Socket rs = (Socket) YychatServer.hmSockes.get(receiver);
                    System.out.println("接收方"+ receiver + "的Socket对象"+ rs);
                    if (rs != null){    //接收方在线才能转发信息
                        ObjectOutputStream oos = new ObjectOutputStream(rs.getOutputStream());
                        oos.writeObject(mess);  //发送聊天信息
                    } else
                        System.out.println(receiver + "不在线");
                }
                //服务器端接收到请求消息后转发Message 对象到发送方
                if (mess.getMessageType().equals(MessageType.REQUEST_ONLINE_FRIEND)){
                    Set onlineFriendSet = YychatServer.hmSockes.keySet(); //拿到在线好友名字的集合
                    Iterator it = onlineFriendSet.iterator();
                    String onlineFriend = "";
                    while (it.hasNext()){
                        onlineFriend = " "+(String) it.next()+ onlineFriend;  //在线好友名字的字符串
                    }
                    mess.setReceiver(mess.getSender());  //接收方为发送方
                    mess.setSender("Server");   //发送方为服务器
                    mess.setMessageType(MessageType.REQUEST_ONLINE_FRIEND);  //设置消息类型
                    mess.setContent(onlineFriend);  //设置消息内容是在线好友的名字
                    sendMessage(s,mess);
                }
                //服务器接收信息，通知全部在线用户激活新上线好友图标
                if (mess.getMessageType().equals(MessageType.NEW_ONLINE_TO_ALL_FRIEND)){
                    mess.setMessageType(MessageType.NEW_ONLINE_FRIEND);
                    Set onlineFriendSet = YychatServer.hmSockes.keySet(); //拿到在线好友名字的集合
                    Iterator it = onlineFriendSet.iterator();
                    while (it.hasNext()){    //循环中依次给全部在线好友发送信息
                        String receiver = (String) it.next();  //接收信息的用户
                        mess.setReceiver(receiver);
                        //拿到接收信息用户的Socket对象
                        Socket rs = (Socket) YychatServer.hmSockes.get(receiver);
                        sendMessage(rs,mess);  //告诉receiver用户，sender上线了，激活sender图标
                    }
                }
            } catch (ClassNotFoundException | IOException e ){
                e.printStackTrace();
            }
        }
    }
    //增加 sendMessage 方法
    public void sendMessage (Socket s,Message mess){
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(mess);  //发送聊天信息
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
