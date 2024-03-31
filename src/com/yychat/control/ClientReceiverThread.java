package com.yychat.control;
import java.io.*;
import java.net.*;
import com.yychat.model.*;
import com.yychat.view.*;
import jdk.nashorn.internal.ir.annotations.Ignore;

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

                if (mess.getMessageType().equals(MessageType.REQUEST_ONLINE_FRIEND)){
                    //为了拿到按收方的FriendList 对象(好友列表界面)，需要在创建时保存到 HashMap 对象
                    FriendList fl = (FriendList) ClientLogin.hmFriendList.get(mess.getReceiver());
                    fl.activeOnlineFriendIcon(mess);
                }
                //接收服务器发送来的新上线好友消息
                if (mess.getMessageType().equals(MessageType.NEW_ONLINE_FRIEND)){
                    String receiver = mess.getReceiver();  //先拿到receiver的好友列表界面
                    FriendList fl = (FriendList) ClientLogin.hmFriendList.get(receiver);
                    String sender = mess.getSender();
                    fl.activeNewOnlineFriendIcon(sender); //激活新上线好友图标
                }

            } catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}