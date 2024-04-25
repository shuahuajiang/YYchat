package com.yychat.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.yychat.model.Message;
import com.yychat.model.MessageType;
import com.yychat.control.YychatClientConnection;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class FriendChat extends JFrame implements ActionListener {
    JTextArea jta;
    JScrollPane jsp;
    JTextField jtf;
    JButton jb;

    String sender;
    String receiver;



    public FriendChat(String sender,String receiver){
        this.sender = sender;
        this.receiver = receiver;

        jta = new JTextArea();  //多行文本框
        jta.setForeground(Color.red);
        jsp = new JScrollPane(jta);
        this.add(jsp,"Center");

        jtf = new JTextField(15);  //单行文本框
        jb = new JButton("发送");
        jb.addActionListener(this);
        jb.setForeground(Color.blue);
        JPanel jp = new JPanel();
        jp.add(jtf); jp.add(jb);
        this.add(jp,"South");
        this.setSize(350,250);
        this.setLocationRelativeTo(null);

       this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       this.setDefaultCloseOperation(EXIT_ON_CLOSE);  //点击关闭时退出整个程序
//        this.setTitle(oneToAnother + "的聊天界面");

        this.setTitle(sender + "to"+ receiver + "的聊天界面");
        this.setIconImage(new ImageIcon("images/duck2.gif").getImage());
        this.setVisible(true);
    }

    public static void main(String[] args) {
//        FriendChat fc = new FriendChat();
    }

    //在 Friendchat 中增加 append()方法
    public  void append(Message mess){
//        jta.append(mess.getSender()+"对你说" + mess.getContent()+ "\r\n");
        jta.append(mess.getSendTime().toString()+"\r\n" + mess.getSender()+"对你说" + mess.getContent()+ "\r\n");
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == jb){                    //点击发送按钮
            jta.append(jtf.getText() + "\r\n");      //在多行文本框追加新的文本
            //创建和发送Message类的对象
            Message mess = new Message();
            mess.setSender(sender);
            mess.setReceiver(receiver);
            mess.setContent(jtf.getText());
            mess.setMessageType(MessageType.COMMON_CHAT_MESSAGE);  //设置聊天信息的类型

            try {
                OutputStream os = YychatClientConnection.s.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(mess);  //通过socket 对象发送聊天信息到服务器端
            } catch (IOException el){
                el.printStackTrace();
            }
        }
    }

}
