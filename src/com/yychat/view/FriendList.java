package com.yychat.view;

import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.HashMap;

import com.yychat.model.Message;

  //ActionListener , MouseListener 接口
public class FriendList extends JFrame implements
          ActionListener,MouseListener {

     //定义HashMap 对象，用来保存 name+toname 和好友聊天界面
     public static HashMap<String,FriendChat>hmFriendChat = new HashMap<String,FriendChat>();


    //定义卡片1（好友面板）中的组件
    JPanel friendPanel;
    JButton myFriendButton1;
    JButton myStrangerButton1;
    JButton blackListButton1;

    JScrollPane friendListScrollPane; //好友列表滚动条面板
    JPanel friendListPanel;
    final int MYFRIENDCOUNT = 50;
    JLabel[]  friendLabel= new JLabel[MYFRIENDCOUNT]; //定义好友图标数组

    //定义卡片2（陌生人面板）中的组件
    JPanel strangerPanel;
    JButton myFriendButton2;
    JButton myStrangerButton2;
    JButton blackListButton2;

    JScrollPane strangerListScrollPane; //陌生人列表滚动条面板
    JPanel strangerListPanel;
    final int STRANGERCOUNT = 20;
    JLabel[]  strangerLabel= new JLabel[STRANGERCOUNT]; //定义陌生人图标数组

    //实验6— 为了在 actionPerformed（） 中访问 cl，声明为成员变量
    CardLayout cl;

//定义成员变量
      String name;


//    修改 FriendList 类的构造方法
//    public FriendList(){
//    public FriendList(String name){
public FriendList(String name,String allFriend){
        //给成员变量 name 赋值
        this.name = name;

        //创建卡片1中的组件
        friendPanel = new JPanel(new BorderLayout()); //卡片1设置边界布局模式
        myFriendButton1 = new JButton("我的好友");
        friendPanel.add(myFriendButton1,"North");

        //修改卡片1中添加好友图标的代码
    String[] myFriend = allFriend.split(" ");
    friendListPanel = new JPanel(new GridLayout(myFriend.length-1,1));
    for (int i = 1; i < myFriend.length; i++) {
        String imageStr = "images/" + i%6 + ".jpg";  //好友图标使用固定的图片
        ImageIcon imageIcon = new ImageIcon(imageStr);
        friendLabel[i] = new JLabel(myFriend[i] + "",imageIcon,JLabel.LEFT);
        friendListPanel.add(friendLabel[i]);
    }

//        //创建中间的好友列表滚动条面板
//        friendListPanel = new JPanel(new GridLayout(MYFRIENDCOUNT,1)); //50行一列
//        for (int i = 0; i < friendLabel.length; i++) {
//
////            String imageStr = "images/" + (int)(Math.random()*6) + ".jpg"; //随机生成图片路径
//
//            String imageStr = "images/" + i%6 + ".jpg";  //好友图标使用固定的图片
//            ImageIcon imageIcon = new ImageIcon(imageStr);
//            friendLabel[i] = new JLabel(i + "",imageIcon,JLabel.LEFT);
//
////            if (i != Integer.valueOf(name)) //自己的图标默认是激活的
//                friendLabel[i].setEnabled(false);  //好友图标为非激活
//
//
//            //在每一个好友图标上添加鼠标监听器
//            friendLabel[i].addMouseListener(this);
//
//            friendListPanel.add(friendLabel[i]);   //好友图标添加到好友列表面板
//        }
        friendListScrollPane = new JScrollPane(friendListPanel); //创建好友滚动条面板
        friendPanel.add(friendListScrollPane,"Center");


        myStrangerButton1 = new JButton("陌生人");
        myStrangerButton1.addActionListener(this); //实验6 在第一张卡片的陌生人按钮上注册动作监听器
        blackListButton1 = new JButton("黑名单");

        //为了容纳myStrangerButton1和blackListButton1，定义一个新JPanel面板
        JPanel stranger_BlackPanel = new JPanel(new GridLayout(2,1)); //两行一列的网格布局
        stranger_BlackPanel.add(myStrangerButton1);
        stranger_BlackPanel.add(blackListButton1);
        friendPanel.add(stranger_BlackPanel,"South");

        //创建卡片2中的组件
        strangerPanel = new JPanel(new BorderLayout());
        myFriendButton2 = new JButton("我的好友");
        myFriendButton2.addActionListener(this); //实验6 在第2张卡片的好友按钮上注册动作监听器
        myStrangerButton2 = new JButton("陌生人");

        JPanel friend_strangerPanel = new JPanel(new GridLayout(2,1));
        friend_strangerPanel.add(myFriendButton2);
        friend_strangerPanel.add(myStrangerButton2);
        strangerPanel.add(friend_strangerPanel,"North");

        //创建中间的陌生人列表滚动条
        strangerListPanel = new JPanel(new GridLayout(STRANGERCOUNT,1));
        for (int i = 0; i < strangerLabel.length; i++) {
            strangerLabel[i] = new JLabel(i+"号陌生人",new ImageIcon("images/tortoise.gif"),JLabel.LEFT);
            strangerListPanel.add(strangerLabel[i]);
        }
        strangerListScrollPane = new JScrollPane(strangerListPanel);
        strangerPanel.add(strangerListScrollPane,"Center"); //陌生人滚动条添加到卡片2中部

        blackListButton2 = new JButton("黑名单");
        strangerPanel.add(blackListButton2,"South");

        //创建卡片布局
//        CardLayout cl = new CardLayout();   //已经定义过 cl 需注释掉此行
        cl = new CardLayout();
        this.setLayout(cl);
        this.add(friendPanel,"card1");
        this.add(strangerPanel,"card2");
//       cl.show(this.getContentPane(),"card1");  //在窗体显示卡片1
//       cl.show(this.getContentPane(),"card1");

        this.setIconImage(new ImageIcon("images/duck2.gif").getImage());
        //在好友列表的标题中添加用户名
        this.setTitle(name + "的好友列表");
//        this.setTitle("好友列表");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLocationRelativeTo(null);
        this.setBounds(800,600,350,350);
        this.setVisible(true);

    }

    public static void main(String[] args) {
//        FriendList fl = new FriendList();    //注释掉创建 FriendList 对象的代码
    }

    //增加激活在线好友图标的方法
      public void activeOnlineFriendIcon(Message mess){
        String onlineFriend = mess.getContent();  //拿到在线好友名字的字符串
        String[] onlineFriendName = onlineFriend.split(" ");//生成在线好友名字的数组
          for (int i = 1; i < onlineFriendName.length; i++) {
//              this.friendLabel[Integer.valueOf(onlineFriendName[i])].setEnabled(true);
          }
      }

    //实验6—实现2张卡片的切换， 添加 actionPerformed（） 方法
    public  void actionPerformed(ActionEvent arg0){
        if (arg0.getSource() == myFriendButton2)
            cl.show(this.getContentPane(),"card1");
        if (arg0.getSource() == myStrangerButton1)
            cl.show(this.getContentPane(),"card2");
    }
    //添加mouselistener 接口中定义的五个方法
      public void mouseClicked(MouseEvent arg0){
        if (arg0.getClickCount() == 2){   //双击鼠标时
            JLabel jl = (JLabel)arg0.getSource();  //获得被双击好友的标签组件
            String toName = jl.getText();     //拿到好友名字
//            new FriendChat(name + "to" + toName);   //创建好友聊天界面
//            new FriendChat(name,toName);
            FriendChat fc = new FriendChat(name,toName);
            hmFriendChat.put(name+ "to" + toName,fc);
        }
      }
      public void mouseEntered(MouseEvent arg0){  //进入好友标签组件时
        JLabel jl = (JLabel) arg0.getSource();
        jl.setForeground(Color.red);
      }
      public void mouseExited(MouseEvent arg0){   //离开好友标签组件时
          JLabel jl = (JLabel) arg0.getSource();
          jl.setForeground(Color.blue);
      }
      public void mousePressed(MouseEvent arg0){}
      public void mouseReleased(MouseEvent arg0){}

      public void activeNewOnlineFriendIcon (String newonlineFriend){
//        this.friendLabel[Integer.valueOf(newonlineFriend)].setEnabled(true);
      }

}
