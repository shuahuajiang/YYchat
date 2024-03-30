package com.yychat.control;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import com.yychat.model.Message;
import com.yychat.model.MessageType;

import java.io.ObjectOutputStream;

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
            } catch (ClassNotFoundException | IOException e ){
                e.printStackTrace();
            }
        }
    }
}
