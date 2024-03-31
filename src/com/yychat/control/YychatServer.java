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

//导入 数据库 相关类
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YychatServer {
//    定义HashMap对象来保存用户名和队友的server端socket对象
    public static HashMap hmSockes = new HashMap<String,Socket>();
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

/*              对数据库代码封装，注释掉数据库相关代码

                //在项目中加载驱动程序
                Class.forName("com.mysql.cj.jdbc.Driver");
                //生成数据库连接对象
                //  jdbc:mysql://127.0.0.1:3306/yychat2022s  只支持英文
                String db_url = "jdbc:mysql://127.0.0.1:3306/yychat2022s?useUnicode=" +
                        "true&characterEncoding=utf-8"; //支持数据库中文数据

                String db_username = "root";
                String db_password = "JHJjhj20030418"; //注意是本地设置的密码
                Connection conn;

                boolean loginSuccess = false;  //增加登录验证变量
                try {
                    conn = DriverManager.getConnection(db_url,db_username,db_password);
                    //查询user表，生成结果集
                    String user_query_str = "select * from user where username=? and password=?";
                    PreparedStatement psmt = conn.prepareStatement(user_query_str);
                    psmt.setString(1,userName);
                    psmt.setString(2,password);
                    ResultSet rs = psmt.executeQuery();
                    //loginsucces为true，表明在user表中查询到记录，否则为false
                    loginSuccess = rs.next();
                } catch (SQLException e){
                    e.printStackTrace();
                }                                           */

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

                //把登录验证的代码放到if语句中
                if (user.getUserType().equals(UserType.USER_LOGIN_VALIDATE)) { //用户登录验证

//              调用 loginValidate（） 方法来完成数据库的用户登录验证
                    boolean loginSuccess = DBUtil.loginValidate(userName, password);


                    //利用 loginSuccess 修改登录验证代码
//                if (password.equals("123456")){
                    if (loginSuccess) {
                        System.out.println("密码验证通过");
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
