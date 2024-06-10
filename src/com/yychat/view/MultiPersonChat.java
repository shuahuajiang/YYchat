package com.yychat.view;

import com.yychat.model.Message;
import com.yychat.model.MessageType;
import com.yychat.control.YychatClientConnection;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MultiPersonChat extends JFrame implements ActionListener {
    JTextArea jta1;
    JTextArea setMessage;
    JScrollPane jsp2;
    JScrollPane jsp3;
    JButton jb1;
    JPanel jp1;
    JPanel jp4;
    String chatsName;
    String sender;
    public MultiPersonChat(String chatsName,String userName){
        this.sender = userName;
        this.chatsName = chatsName;
        //消息显示区域
        jta1 = new JTextArea();
        jta1.setEditable(false);
        jta1.setLineWrap(true);
        jta1.setWrapStyleWord(true);

        //消息框显示位置
        jsp2 = new JScrollPane(jta1);
        jsp2.setBounds(0,0,300,300);

        //消息框与北部功能按钮容器显示位置
        jp4 = new JPanel();
        jp4.setLayout(null);
        jp4.add(jsp2);
        jp4.setBounds(0,0,300,300);

        //输入消息框
        jp1 = new JPanel();
        jp1.setLayout(null);
        setMessage = new JTextArea();
        jb1 = new JButton("发送");
        //发送按钮
        jb1.setBounds(220,125,70,25);
        jb1.addActionListener(this);
        jp1.add(setMessage);
        jp1.add(jb1);
        setMessage.setLineWrap(true);//激活自动换行功能
        setMessage.setWrapStyleWord(true);//激活断行不断字功能
        jsp3 = new JScrollPane(setMessage);
        jsp3.setBounds(0,0,300,120);
        jp1.add(jsp3);
        //输入框与发送按钮容器显示位置
        jp1.setBounds(0,310,300,200);

        this.setLayout(null);
        this.add(jp4);
        this.add(jp1);

        this.setVisible(true);
        this.setBounds(500,500,328,500);
        this.setTitle(chatsName);
        this.setResizable(false);
    }

    public static void main(String[] args) {
        new MultiPersonChat("聊天室","yychat");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jb1){
            String content = setMessage.getText(); // 获取输入框中的文本内容
            Message mess = new Message();
            mess.setSender(sender);
            mess.setContent(content);
            mess.setChatName(chatsName);// 设置消息所属的聊天室名称
            mess.setMessageType(MessageType.SEND_TO_MULTI_PERSON_CHAT_CLIENT);// 设置消息类型为发送到多人聊天客户端
            System.out.println("Message type set. Current message: " +mess.getMessageType());
            OutputStream os = null;
            try {
                os = YychatClientConnection.s.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(mess);
                System.out.println("Message sent: " + mess.getContent());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            // 调用 append 方法来更新消息显示区域

            setMessage.setText("");
        }
    }
    public void append(Message mess) {
        jta1.append(mess.getSender() + "说： "+mess.getContent() + "\r\n");
    }
}

