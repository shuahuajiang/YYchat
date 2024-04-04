package com.yychat.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sun.xml.internal.ws.resources.SenderMessages;
import com.yychat.control.YychatClientConnection;
import com.yychat.model.User;

import com.yychat.model.Message;
import com.yychat.model.MessageType;
import com.yychat.model.UserType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import java.net.Socket;

public class ClientLogin extends JFrame implements ActionListener {

    //创建hashmap，用来保存好友列表界面
    public static HashMap hmFriendList = new HashMap<String, FriendList>();

    JLabel jl;  //定义标签组件
    JButton jb1, jb2, jb3;
    JPanel jp;

    //定义登录界面中间部分组件
    JLabel jl1, jl2, jl3, jl4;
    JTextField jtf;
    JPasswordField jpf;
    JButton jb4;
    JCheckBox jc1, jc2;
    JPanel jp1, jp2, jp3;
    JTabbedPane jtp;

    public ClientLogin() {
//        jl = new JLabel("JAVA聊天室");
        jl = new JLabel(new ImageIcon("images/head.gif")); //图片标签
        this.add(jl, "North");  //标签组件添加到窗体北部

        //创建登录界面中间部分组件
        jl1 = new JLabel("YY号码", JLabel.CENTER);
        jl2 = new JLabel("YY密码", JLabel.CENTER);
        jl3 = new JLabel("忘记密码", JLabel.CENTER);
        jl3.setForeground(Color.BLUE); //设置字体颜色
        jl4 = new JLabel("申请密码保护", JLabel.CENTER);
        jb4 = new JButton(new ImageIcon("images/clear.gif"));
        jtf = new JTextField();
        jpf = new JPasswordField();
        jc1 = new JCheckBox("隐身登录");
        jc2 = new JCheckBox("记住密码");
        jp1 = new JPanel(new GridLayout(3, 3)); //网格布局

        jp1.add(jl1);
        jp1.add(jtf);
        jp1.add(jb4); //在面板添加9个组件
        jp1.add(jl2);
        jp1.add(jpf);
        jp1.add(jl3);
        jp1.add(jc1);
        jp1.add(jc2);
        jp1.add(jl4);

        jtp = new JTabbedPane(); //选项卡面板
        jtp.add(jp1, "YY号码");
        jp2 = new JPanel();
        jp3 = new JPanel();
        jtp.add(jp2, "手机号码");
        jtp.add(jp3, "电子邮箱");
        this.add(jtp, "Center");

        jb1 = new JButton(new ImageIcon("images/login.gif")); //图片按钮
        jb1.addActionListener(this);  //在登录按钮上添加动作监听器

        //在注册按钮上添加动作事件的监听器对象
        jb2 = new JButton(new ImageIcon("images/register.gif"));
        jb2.addActionListener(this);

        jb3 = new JButton(new ImageIcon("images/cancel.gif"));

        jp = new JPanel(); //创建面板对象
        jp.add(jb1); //在JPanel面板中添加按钮
        jp.add(jb2); //默认FlowLayout流式布局
        jp.add(jb3);
        this.add(jp, "South"); //JPanel组件添加到窗体南部

        Image im = new ImageIcon("images/duck2.gif").getImage(); //创建image对象
        this.setIconImage(im);  //窗体左上角图标

//        this.setBounds(800,600,350,250);  //设置窗体边界（位置和大小）
        this.setLocationRelativeTo(null);
        this.setSize(350, 250); //窗体大小
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //点击窗体关闭按钮
        this.setTitle("YY聊天");
        this.setVisible(true); //窗体可视
    }

    public static void main(String[] args) {
        ClientLogin cl = new ClientLogin(); //创建窗体对象
    }

    //添加
    public void actionPerformed(ActionEvent arg0) {
        //判断ActionEvent事件来源，调用注册方法来发送user对象
//        if (arg0.getSource() == jb1){    //鼠标点击登录按钮

        String name = jtf.getText(); //获取用户名
        String password = new String(jpf.getPassword()); //获取登录密码

        User user = new User();//创建user类的对象
        user.setUserName(name);
        user.setPassword(password);
        //响应注册事件
        if (arg0.getSource() == jb2) {
            user.setUserType(UserType.USER_REGISTER); //设置user对象
            if (new YychatClientConnection().registerUser(user)) {
                JOptionPane.showMessageDialog(this, name + "用户注册成功");
            } else {
                JOptionPane.showMessageDialog(this, name + "用户名重复，请重新注册");
            }
        }
//          注册新用户，修改部分登录代码
        if (arg0.getSource() == jb1) {
            user.setUserType(UserType.USER_LOGIN_VALIDATE);

            Message mess = new YychatClientConnection().loginValidate1(user);  //返回消息对象
            if (mess.getMessageType().equals(MessageType.LOGIN_VALIDATE_SUCCESS)) {   //登录验证通过
                String allFriend = mess.getContent();   //登录端拿到全部好友名字
                FriendList fl = new FriendList(name,allFriend); //把好发名字传到好友列表窗体
                hmFriendList.put(name, fl);
                this.dispose();   //关闭登陆界面
            } else {
                JOptionPane.showMessageDialog(this, "密码错误，重新登录");
            }

//            if (new YychatClientConnection().loginValidate(user)){
//                hmFriendList.put(name,new FriendList(name));
//                System.out.println("客户端登录成功");
//
//                Message mess = new Message();
//                mess.setSender(name);
//                mess.setReceiver("Server");
//                mess.setMessageType(MessageType.REQUEST_ONLINE_FRIEND);
//
//                //使用 sendMessage()方法发送消息
//                sendMessage(YychatClientConnection.s,mess);
//
//                //设置消息类型发送到服务器
//                mess.setMessageType(MessageType.NEW_ONLINE_TO_ALL_FRIEND);
//                sendMessage(YychatClientConnection.s,mess);
//                this.dispose();
//            }else {
//                JOptionPane.showMessageDialog(this,"密码错误，重新登录");
//            }

//            new FriendList(name);        //创建好友列表界面
//            this.dispose();              //关闭登录界面
//            new YychatClientConnection();
//            new YychatClientConnection().loginValidate(user);
            }
        }
        public void sendMessage (Socket s, Message mess){
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(mess);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
