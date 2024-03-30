package com.yychat.control;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.yychat.model.Message;
import com.yychat.model.MessageType;

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
                }
            } catch (ClassNotFoundException | IOException e ){
                e.printStackTrace();
            }
        }
    }
}
