package com.yychat.view;

import javax.swing.*;

public class ClientLogin extends JFrame {
    JLabel jl;  //定义标签组件
    JButton jb1,jb2,jb3;
    JPanel jp;
    public ClientLogin(){
//        jl = new JLabel("JAVA聊天室");
        jl = new JLabel(new ImageIcon("images/head.gif"));
        this.add(jl,"North");  //标签组件添加到窗体北部

        jb1 = new JButton(new ImageIcon("images/login.gif")); //图片按钮
        jb2 = new JButton(new ImageIcon("images/register.gif"));
        jb3 = new JButton(new ImageIcon("images/cancel.gif"));

        jp = new JPanel(); //创建面板对象
        jp.add(jb1); //在JPanel面板中添加按钮
        jp.add(jb2); //默认FlowLayout流式布局
        jp.add(jb3);
        this.add(jp,"South"); //JPanel组件添加到窗体南部

//        this.setBounds(800,600,350,250);  //设置窗体边界（位置和大小）
        this.setLocationRelativeTo(null);
        this.setSize(350,250); //窗体大小
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //点击窗体关闭按钮
        this.setTitle("YY聊天");

        this.setVisible(true); //窗体可视
    }

    public static void main(String[] args) {
        ClientLogin cl = new ClientLogin(); //创建窗体对象
    }
}
