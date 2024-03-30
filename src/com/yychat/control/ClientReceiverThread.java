package com.yychat.control;
import java.io.*;
import java.net.*;
import com.yychat.model.*;
import com.yychat.view.*;

public class ClientReceiverThread extends Thread{
    Socket s;
    public ClientReceiverThread(Socket s){
        this.s = s;
    }
    public void run(){ //定义线程方法
        while (true){
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                //阻塞式编程，没有读到信息(服务器没有发送)，就一直等待
                Message mess = (Message) ois.readObject();
                if (mess.getMessageType().equals(MessageType.COMMON_CHAT_MESSAGE)){
                    String receiver = mess.getReceiver();
                    String sender = mess.getSender();
                    //从 HashMap 中拿到 Friendchat 对象，并显示聊天信息
                    FriendChat fc = (FriendChat) FriendList.hmFriendChat.get(receiver + "to" + sender);
                    if (fc != null){
                        fc.append(mess);
                    }else
                        System.out.println("请打开"+ receiver +"to"+ sender+ "的聊天界面");
                }

            } catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
