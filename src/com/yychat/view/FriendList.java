package com.yychat.view;

import sun.applet.Main;

import javax.swing.*;
import java.awt.*;

public class FriendList extends JFrame {

    //定义卡片1（好友面板）中的组件
    JPanel friendPanel;
    JButton myFriendButton1;
    JButton myStrangerButton1;
    JButton blackListButton1;

    //定义卡片2（陌生人面板）中的组件
    JPanel strangerPanel;
    JButton myFriendButton2;
    JButton myStrangerButton2;
    JButton blackListButton2;

    public FriendList(){
        //创建卡片1中的组件
        friendPanel = new JPanel(new BorderLayout()); //卡片1设置边界布局模式
        myFriendButton1 = new JButton("我的好友");
        friendPanel.add(myFriendButton1,"North");

        myStrangerButton1 = new JButton("陌生人");
        blackListButton1 = new JButton("黑名单");

        //为了容纳myStrangerButton1和blackListButton1，定义一个新JPanel面板
        JPanel stranger_BlackPanel = new JPanel(new GridLayout(2,1)); //两行一列的网格布局
        stranger_BlackPanel.add(myStrangerButton1);
        stranger_BlackPanel.add(blackListButton1);
        friendPanel.add(stranger_BlackPanel,"South");

        //创建卡片2中的组件
        strangerPanel = new JPanel(new BorderLayout());
        myFriendButton2 = new JButton("我的好友");
        myStrangerButton2 = new JButton("陌生人");

        JPanel friend_strangerPanel = new JPanel(new GridLayout(2,1));
        friend_strangerPanel.add(myFriendButton2);
        friend_strangerPanel.add(myStrangerButton2);
        strangerPanel.add(friend_strangerPanel,"North");

        blackListButton2 = new JButton("黑名单");
        strangerPanel.add(blackListButton2,"South");

        CardLayout cl = new CardLayout(); //创建卡片布局
        this.setLayout(cl);
        this.add(friendPanel,"card1");
        this.add(strangerPanel,"card2");
//        cl.show(this.getContentPane(),"card1");  //在窗体显示卡片1
        cl.show(this.getContentPane(),"card1");

        this.setIconImage(new ImageIcon("images/duck2.gif").getImage());
        this.setTitle("好友列表");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(800,600,350,250);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        FriendList fl = new FriendList();
    }
}
