package com.yychat.control;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.yychat.model.Message;
import com.yychat.model.MessageType;

import java.io.ObjectOutputStream;

import java.util.Date;
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
                System.out.println("服务器正在运行中。。。");
                //创建对象输入流
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message mess = (Message) ois.readObject();  //接收Message对象



                //在服务器端处理用户退出的代码
                if (mess.getMessageType().equals(MessageType.USER_EXIT_SERVER_THREAD_CLOSE)){
                    String sender = mess.getSender();
                    mess.setMessageType(MessageType.USER_EXIT_CLIENT_THREAD_CLOSE);
                    sendMessage(s,mess);
                    YychatServer.hmSocket.remove(sender);
                    System.out.println(sender + "用户退出了，正在关闭其服务线程");
                    s.close();
                    break;
                }

                //在服务器端添加处理请求添加新好友信息代码
                if (mess.getMessageType().equals(MessageType.ADD_NEW_FRIEND)){
                    String sender = mess.getSender();
                    String newFriend = mess.getContent();

                //首先查询新好友在user表中是否存在
                if (DBUtil.seekUser(newFriend)) {  //新好友在 user 表中存在
                    // 然后，在userrelation 表中查询新好友是不是已经是好友了
                    if (DBUtil.seekFriend(sender, newFriend, 1)) { //已经是好友了，不能承复添加,1是好友，0是陌生人

                        mess.setMessageType(MessageType.ADD_NEW_FRIEND_FAILURE_ALREADY_FRIEND);//设置添加新好友失败消息类型
                    } else {  //还不是好友，可以添加
                        DBUtil.insertIntoFriend(sender, newFriend, 1);// userrelation 插入好友记录
                        String allFriend = DBUtil.seekAllFriend(sender, 1);  //返网sender 的全部好友
                        mess.setContent(allFriend);  //全部好友保存在content 字段
                        mess.setMessageType(MessageType.ADD_NEW_FRIEND_SUCCESS);//设置添加新好友成功消息类型
                    }
                } else {
                    mess.setMessageType(MessageType.ADD_NEW_FRIEND_FAILURE_NO_USER);
                }
                Socket ss = (Socket) YychatServer.hmSockes.get(sender);
                sendMessage(ss, mess);  //发送消息到客户端
            }


                //删除好友
                if(mess.getMessageType().equals(MessageType.DELETE_FRIEND)) {
                    String sender=mess.getSender();
                    String newFriend=mess.getContent();
                    //首先查询新好友在user表是否存在
                    if(DBUtil.seekUser(newFriend)) {
                        //在userrelation表中查询是不是好友
                        if(DBUtil.seekFriend(sender,newFriend,1)) {
                            DBUtil.deleteIntoFriend(newFriend);
                            String allFriend=DBUtil.seekAllFriend(sender,1);
                            mess.setContent(allFriend);
                            mess.setMessageType(MessageType.DELETE_FRIEND_SUCCESS);
                        }else {

                            mess.setMessageType(MessageType.DELETE_FRIEND_FAILURE_ALREADY_FRIEND);
                        }
                    }else {
                        mess.setMessageType(MessageType.DELETE_FRIEND_FAILURE_NO_USER);
                    }
                    Socket ss=(Socket)YychatServer.hmSockes.get(sender);
                    sendMessage(ss,mess); //发送信息到客户端
                }


//                创建群聊
                if (mess.getMessageType().equals(MessageType.CREATE_MULTI_PERSON_CHAT)) {
                    if (DBUtil.seekChatsName(mess.getChatName()) || DBUtil.seekChatsNumber(mess.getChatNumber())) {
                        mess.setMessageType(MessageType.CREATE_MULTI_PERSON_CHAT_FAILURE);
                    } else {
                        DBUtil.insertIntoChats(mess.getChatName(), mess.getChatNumber());
                        DBUtil.inserIntouserChats(mess.getSender(),mess.getChatName());
                        DBUtil.createChatsTable(mess.getChatName(), mess.getChatNumber());
                        DBUtil.joinChats(mess.getChatName(),mess.getSender());
                        mess.setMessageType(MessageType.CREATE_MULTI_PERSON_CHAT_SUCCESS);
                    }
                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                    oos.writeObject(mess);
                }


//                加入群聊
                if(mess.getMessageType().equals(MessageType.JOIN_MULTI_PERSON_CHAT)){
                    if(DBUtil.seekChats(mess.getChatName())) {
                        if (DBUtil.seekChatsUser(mess.getChatName(), mess.getSender())) {
                            mess.setMessageType(MessageType.JOIN_MULTI_PERSON_CHAT_FAILURE);
                        } else {
                            DBUtil.joinChats(mess.getChatName(), mess.getSender());
                            DBUtil.inserIntouserChats(mess.getSender(), mess.getChatName());
                            mess.setMessageType(MessageType.JOIN_MULTI_PERSON_CHAT_SUCCESS);
                            mess.setChatName(DBUtil.seekJoinChats(mess.getSender()));
                        }
                    }else
                        mess.setMessageType(MessageType.MULTI_PERSON_CHAT_NOT_EXITS);
                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                    oos.writeObject(mess);
                }

//                群聊发送消息
                if(mess.getMessageType().equals(MessageType.SEND_TO_MULTI_PERSON_CHAT_CLIENT)){
                    String chatsMember = DBUtil.seekChatsMember(mess.getChatName());
                    System.out.println("Chat members: " + chatsMember);
                    mess.setReceiver(chatsMember);
                    mess.setMessageType(MessageType.RECEIVER_FROM_MULTI_PERSON_CHAT_SERVER);
                    String[] member = chatsMember.split(" ");
                    System.out.println("member length: " + member.length);
                    for (int i = 1; i < member.length; i++) {
                        Socket ss =(Socket) YychatServer.hmSockes.get(member[i]);
                        if( ss != null){
                            try {
                                ObjectOutputStream oos = new ObjectOutputStream(ss.getOutputStream());
                                oos.writeObject(mess);
                                System.out.println("Message sent to: " + member[i]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("No socket found for member: " + member[i]);
                        }
                    }
                }

                //保存聊天信息到message表
                if (mess.getMessageType().equals(MessageType.COMMON_CHAT_MESSAGE)){
                    System.out.println(mess.getSender() + "对" + mess.getReceiver()
                    + "说：" + mess.getContent());
                    mess.setSendTime(new java.util.Date());
                    DBUtil.saveMessage(mess);

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
                throw new RuntimeException(e);
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
