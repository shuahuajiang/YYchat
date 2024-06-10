package com.yychat.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.yychat.model.MessageType;
import com.yychat.model.User;

import com.yychat.model.Message;
import com.yychat.model.UserType;

import java.io.ObjectOutputStream;

import java.util.HashMap; //导入HashMap类

public class YychatServer {
//    定义HashMap对象来保存用户名和队友的server端socket对象
    public static HashMap hmSockes = new HashMap<String,Socket>();
    ServerSocket ss;
    Socket s;

    public static HashMap<String, Socket> hmSocket = new HashMap<>();
    public YychatServer() throws IOException {
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
                //在服务器的YychatServer类中新增注册用户代码
                if (user.getUserType().equals(UserType.USER_REGISTER)){
                    //调用seek方法，在user表中查询是否有同名用户
                    if (DBUtil.seekUser(userName)){  //有同名用户不能注册
                        mess.setMessageType(MessageType.USER_REGISTER_FAILURE);  //设置消息类型
                    }else {
                        DBUtil.insertIntoUser(userName,password);  //在user表中注册新用户
                        mess.setMessageType(MessageType.USER_REGISTER_SUCCESS);
                    }
                    oos.writeObject(mess);  //发送mess对象到客户端
                    s.close();
                }

                //修改密码
                if(user.getUserType().equals(UserType.FORGET_PASSWORD)) {
                    DBUtil.updateForgetPassword(userName, password);
                    mess.setMessageType(MessageType.FORGET_SUCCESS);
                    oos.writeObject(mess);
                }


                //把登录验证的代码放到if语句中
                if (user.getUserType().equals(UserType.USER_LOGIN_VALIDATE)) { //用户登录验证

//              调用 loginValidate（） 方法来完成数据库的用户登录验证
                    boolean loginSuccess = DBUtil.loginValidate(userName, password);

                    //利用 loginSuccess 修改登录验证代码
                    if (loginSuccess) {
                        System.out.println("密码验证通过");
                        //通过后拿到数据库中全部好友名字
                        String allFriend = DBUtil.seekAllFriend(userName,1);
                        mess.setContent(allFriend);
                        String chats = DBUtil.seekJoinChats(userName);
                        mess.setChatName(chats);
                        mess.setMessageType(MessageType.LOGIN_VALIDATE_SUCCESS);
                        oos.writeObject(mess);  //发送mess对象到客户端
                        hmSockes.put(userName, s);  //保存登录成功的新用户名和 socket 对象类

                    /*用户登陆成功后，服务器为每一个用户创建服务线程，
                    由于可能有多个客户同时向服务器发送信息,需要为每一个用户创建接收线程*/
                        new ServerReceiverThread(s).start(); //启动线程
                        System.out.println("启动线程成功");
                    } else {
                        System.out.println("密码验证失败");
                        mess.setMessageType(MessageType.LOGIN_VALIDATE_FAILURE);//登录验证失败
                        oos.writeObject(mess);  //发送mess对象到客户端
                        s.close();
                    }
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
