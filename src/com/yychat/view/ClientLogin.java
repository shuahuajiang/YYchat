package com.yychat.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientLogin extends JFrame implements ActionListener {
    JLabel jl;  //定义标签组件
    JButton jb1,jb2,jb3;
    JPanel jp;

    //定义登录界面中间部分组件
    JLabel jl1,jl2,jl3,jl4;
    JTextField jtf;
    JPasswordField jpf;
    JButton jb4;
    JCheckBox jc1,jc2;
    JPanel jp1,jp2,jp3;
    JTabbedPane jtp;

    public ClientLogin(){
//        jl = new JLabel("JAVA聊天室");
        jl = new JLabel(new ImageIcon("images/head.gif")); //图片标签
        this.add(jl,"North");  //标签组件添加到窗体北部

        //创建登录界面中间部分组件
        jl1 = new JLabel("YY号码",JLabel.CENTER);
        jl2 = new JLabel("YY密码",JLabel.CENTER);
        jl3 = new JLabel("忘记密码",JLabel.CENTER);
        jl3.setForeground(Color.BLUE); //设置字体颜色
        jl4 = new JLabel("申请密码保护",JLabel.CENTER);
        jb4 = new JButton(new ImageIcon("images/clear.gif"));
        jtf = new JTextField();
        jpf = new JPasswordField();
        jc1 = new JCheckBox("隐身登录");
        jc2 = new JCheckBox("记住密码");
        jp1 = new JPanel(new GridLayout(3,3)); //网格布局

        jp1.add(jl1); jp1.add(jtf); jp1.add(jb4); //在面板添加9个组件
        jp1.add(jl2); jp1.add(jpf); jp1.add(jl3);
        jp1.add(jc1); jp1.add(jc2); jp1.add(jl4);

        jtp = new JTabbedPane(); //选项卡面板
        jtp.add(jp1,"YY号码");
        jp2 = new JPanel();
        jp3 = new JPanel();
        jtp.add(jp2,"手机号码");
        jtp.add(jp3,"电子邮箱");
        this.add(jtp,"Center");

        jb1 = new JButton(new ImageIcon("images/login.gif")); //图片按钮
        jb1.addActionListener(this);  //在登录按钮上添加动作监听器
        jb2 = new JButton(new ImageIcon("images/register.gif"));
        jb3 = new JButton(new ImageIcon("images/cancel.gif"));

        jp = new JPanel(); //创建面板对象
        jp.add(jb1); //在JPanel面板中添加按钮
        jp.add(jb2); //默认FlowLayout流式布局
        jp.add(jb3);
        this.add(jp,"South"); //JPanel组件添加到窗体南部

        Image im = new ImageIcon("images/duck2.gif").getImage(); //创建image对象

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
    //添加
    public void actionPerformed(ActionEvent arg0){
        if (arg0.getSource() == jb1){    //鼠标点击登录按钮
            String name = jtf.getText(); //获取用户名
            new FriendList(name);        //创建好友列表界面
            this.dispose();              //关闭登录界面
        }
    }
}
