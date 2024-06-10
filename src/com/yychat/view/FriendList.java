package com.yychat.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import com.yychat.model.Message;

import com.yychat.control.YychatClientConnection;
import com.yychat.model.MessageType;
import java.io.ObjectOutputStream;
import java.io.IOException;

//用户退出后关闭线程，实现 WindowListener 接口
  //ActionListener , MouseListener 接口
public class FriendList extends JFrame implements
          ActionListener,MouseListener,WindowListener {
     //定义HashMap 对象，用来保存 name+toname 和好友聊天界面
     public static HashMap<String,FriendChat>hmFriendChat = new HashMap<String,FriendChat>();
    public static HashMap<String,MultiPersonChat> hmMultiPersonChat = new HashMap<>();

    //定义卡片1（好友面板）中的组件
    JPanel friendPanel;
    JButton myFriendButton1;
    JButton myStrangerButton1;
    JButton blackListButton1;

    JScrollPane friendListScrollPane; //好友列表滚动条面板
    JPanel friendListPanel;
    final int MYFRIENDCOUNT = 50;   //50个好友
    JLabel[]  friendLabel= new JLabel[MYFRIENDCOUNT]; //定义好友图标数组

    //定义卡片2（陌生人面板）中的组件
    JPanel strangerPanel;
    JButton myFriendButton2;
    JButton myStrangerButton2;
    JButton blackListButton2;

    JScrollPane strangerListScrollPane; //陌生人列表滚动条面板
    JPanel strangerListPanel;

    //实验6— 为了在 actionPerformed（） 中访问 cl，声明为成员变量
    CardLayout cl;

//定义成员变量
      String name;

      //定义添加好友面板和按钮
      JPanel addFriendJPanel;
      JButton addFriendButton;
    JPanel deleteFriend1;
    JButton deleteFriend2;

    JPanel joinMoreChat1;
    JPanel createMultiPersonChat1;
    JButton createMultiPersonChat;
    JButton joinMoreChat;

    JPanel mychats;
    JScrollPane mychatsjsp;

//    修改 FriendList 类的构造方法
public FriendList(String name,String allFriend,String chats){
        //给成员变量 name 赋值
        this.name = name;

        //创建卡片1中的组件
        friendPanel = new JPanel(new BorderLayout()); //卡片1设置边界布局模式
    //创建卡片2中的组件
    strangerPanel = new JPanel(new BorderLayout());
    myFriendButton2 = new JButton("我的好友");
    myFriendButton2.addActionListener(this); //实验6 在第2张卡片的好友按钮上注册动作监听器
    myStrangerButton2 = new JButton("我的群聊");

    //创建添加好友面板和按钮并注册监听器
    addFriendJPanel = new JPanel(new GridLayout(2,2));
    addFriendButton = new JButton("添加好友");
    addFriendButton.addActionListener(this);  //在添加好友按钮上注册监听器

    //删除好友
    deleteFriend1=new JPanel(new GridLayout(5,1));
    deleteFriend2=new JButton("删除好友");
    deleteFriend2.addActionListener(this);//给该按钮注册一个监听器

//    加入群聊
    joinMoreChat1=new JPanel(new GridLayout(2,2));
    joinMoreChat=new JButton("加入群聊");
    joinMoreChat.addActionListener(this);


//    创建群聊
    createMultiPersonChat1=new JPanel(new GridLayout(2,2));
    createMultiPersonChat=new JButton("创建群聊");
    createMultiPersonChat.addActionListener(this);


    myFriendButton1=new JButton("我的好友");

    deleteFriend1.add(addFriendButton);
    deleteFriend1.add(deleteFriend2);
    deleteFriend1.add(joinMoreChat);
    deleteFriend1.add(createMultiPersonChat);
    deleteFriend1.add(myFriendButton1);


    friendPanel.add(deleteFriend1,"North"); //把我的好友按钮添加到卡片1的北部


    //调用showAllFriend 方法
    friendListPanel = new JPanel();
    showAllFriend(allFriend);
    //创建好友滚动条面板
        friendListScrollPane = new JScrollPane(friendListPanel);
        friendPanel.add(friendListScrollPane,"Center");

//   我的群聊
        myStrangerButton1 = new JButton("我的群聊");
        myStrangerButton1.addActionListener(this);
    //加入 群聊的滚动面板
    mychats = new JPanel();
    showChats(chats);
    mychatsjsp = new JScrollPane(mychats);
    strangerPanel.add(mychatsjsp,"Center");


    blackListButton1 = new JButton("黑名单");

        //为了容纳myStrangerButton1和blackListButton1，定义一个新JPanel面板
        JPanel stranger_BlackPanel = new JPanel(new GridLayout(2,1)); //两行一列的网格布局
        stranger_BlackPanel.add(myStrangerButton1);
        stranger_BlackPanel.add(blackListButton1);
        friendPanel.add(stranger_BlackPanel,"South");



        JPanel friend_strangerPanel = new JPanel(new GridLayout(2,1));
        friend_strangerPanel.add(myFriendButton2);
        friend_strangerPanel.add(myStrangerButton2);
        strangerPanel.add(friend_strangerPanel,"North");


        blackListButton2 = new JButton("黑名单");
        strangerPanel.add(blackListButton2,"South");

        //创建卡片布局
//        CardLayout cl = new CardLayout();   //已经定义过 cl 需注释掉此行
        cl = new CardLayout();
        this.setLayout(cl);
        this.add(friendPanel,"card1");
        this.add(strangerPanel,"card2");

        this.setIconImage(new ImageIcon("images/duck2.gif").getImage());
        //在好友列表的标题中添加用户名
        this.setTitle(name + "的好友列表");
        this.addWindowListener(this);
        this.setBounds(800,600,350,350);
        this.setVisible(true);
    }
    public static void main(String[] args) {
//        FriendList fl = new FriendList();    //注释掉创建 FriendList 对象的代码
    }

    //增加showAllFriend（）方法
      public void showAllFriend(String allFriend){
    String[] myFriend = allFriend.split(" ");
    friendListPanel.removeAll(); //在好友列表面板中移去全部组件
          friendListPanel.setLayout(new GridLayout(myFriend.length-1,1));
          for (int i = 1; i < myFriend.length; i++) {
              String imageStr = "image/" + i%6 + ".jpg";
              ImageIcon imageIcon = new ImageIcon(imageStr);
              friendLabel[i] = new JLabel(myFriend[i] + "",imageIcon,JLabel.LEFT);
              friendLabel[i].addMouseListener(this);  //注册监听器对象
              friendListPanel.add(friendLabel[i]);
          }
          friendListPanel.revalidate(); //让好友面板重新生效
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

    //输入新好友名字，发送到服务器端
        if (arg0.getSource() == addFriendButton){
            String newFriend = JOptionPane.showInputDialog("请输入新好友的名字");
            System.out.println("newFriend" + newFriend);
            if (newFriend != null){
                Message mess = new Message();
                mess.setSender(name);
                mess.setReceiver("Server");
                mess.setContent(newFriend);
                mess.setMessageType(MessageType.ADD_NEW_FRIEND);
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(YychatClientConnection.s.getOutputStream());
                    oos.writeObject(mess);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

//删除好友
        if(arg0.getSource()==deleteFriend2) {
            String deleteFriend=JOptionPane.showInputDialog("请输入要删除好友的名字：");
            System.out.println("deleteFriend:"+deleteFriend);
            if(deleteFriend!=null) {
                Message mess = new Message();
                mess.setSender(name);
                mess.setReceiver("Server");
                mess.setContent(deleteFriend);
                mess.setMessageType(MessageType.DELETE_FRIEND); //设置消息类型
                try {
                    ObjectOutputStream oos =new ObjectOutputStream(YychatClientConnection.s.getOutputStream());
                    oos.writeObject(mess);
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }

//        加入群聊
        if (arg0.getSource() == this.createMultiPersonChat) {
            new CreateChat(this.name);
        }
        if (arg0.getSource() == this.joinMoreChat) {
            String  chatsName = JOptionPane.showInputDialog("请输入群聊名称：");
            if (chatsName != null) {
                Message mess = new Message();
                mess.setMessageType(MessageType.JOIN_MULTI_PERSON_CHAT);
                mess.setSender(this.name);
                mess.setChatName(chatsName);
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(YychatClientConnection.s.getOutputStream());
                    oos.writeObject(mess);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


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



      //群聊功能
    public void showChats(String chats){
        mychats.removeAll();
        String [] chat = chats.split(" ");
        for (int i = 1; i < chat.length; i++) {
            JLabel chatsLabel = new JLabel(chat[i], new ImageIcon("images/3.jpg"), JLabel.LEFT);
            chatsLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() == 2){
                        JLabel jl =(JLabel) e.getSource();
                        String chatsName = jl.getText();
                        MultiPersonChat mpc = new MultiPersonChat(chatsName,name);
                        System.out.println(name);
                        hmMultiPersonChat.put(name+"from"+chatsName,mpc);
                    }
                }
                @Override
                public void mousePressed(MouseEvent e) {
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    JLabel jl = (JLabel) e.getSource();
                    jl.setForeground(Color.red);//设置好友名字为红色
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    JLabel jl = (JLabel) e.getSource();
                    jl.setForeground(Color.black);//设置好友名字为蓝色
                }
            });
            mychats.setLayout(new GridLayout(chat.length - 1,1));
            mychats.add(chatsLabel);
        }
        mychats.revalidate();
    }

      //实现 WindListener 接口中的7个抽象方法
      public void windowClosing(WindowEvent arge) {
          System.out.println(name + "准备关闭客户端...");
          //向服务署发送关闭客户编消息
          Message mess = new Message();
          mess.setSender(name);
          mess.setReceiver("Server");
          mess.setMessageType(MessageType.USER_EXIT_SERVER_THREAD_CLOSE);
          ObjectOutputStream oos;
          try {
              oos = new ObjectOutputStream(YychatClientConnection.s.getOutputStream());
              oos.writeObject(mess);//向服务器发送消息}catch(I0Exception e){
          } catch (IOException e) {
              e.printStackTrace();
          }
          System.exit(0);//关闭客户端
      }
    public void windowActivated(WindowEvent arg0){}
    public void windowClosed(WindowEvent arg0){}
    public void windowDeactivated(WindowEvent arg0){}
    public void windowDeiconified(WindowEvent arg0){}
    public void windowIconified(WindowEvent arg0) {}
    public void windowOpened(WindowEvent arg0){}
}
