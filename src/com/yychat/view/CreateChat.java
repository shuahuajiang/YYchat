package com.yychat.view;

import com.yychat.model.Message;
import com.yychat.model.MessageType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import  com.yychat.control.YychatClientConnection;

public class CreateChat extends JFrame implements ActionListener {

    JButton jb1;
    JButton jb2;

    JTextField jtf1;
    JTextField jtf2;

    JLabel jl1;
    JLabel jl2;

    JPanel jp1;
    String name;
    public CreateChat(String name){
        this.name = name;
        jl1 = new JLabel("群名称");
        jl2 = new JLabel("群号码");

        jtf1 = new JTextField();
        jtf2 = new JTextField();

        jp1 = new JPanel(new GridLayout(2,2));
        jp1.add(jl1);
        jp1.add(jtf1);
        jp1.add(jl2);
        jp1.add(jtf2);

        jb1 = new JButton("确认");
        jb2 = new JButton("取消");
        jb1.addActionListener(this);
        jb2.addActionListener(this);

        jb1.setBounds(20,80,70,30);
        jb2.setBounds(150,80,70,30);

        jp1.setBounds(20,10,200,50);
        this.add(jp1);
        this.add(jb1);
        this.add(jb2);

        this.setLayout(null);
        this.setSize(250,150);
        this.setVisible(true);
        this.setTitle("创建群聊");
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        //    new CreateChat("Name");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == jb1){
            Message mess = new Message();
            mess.setChatName(jtf1.getText());
            mess.setChatNumber(jtf2.getText());
            mess.setSender(name);
            mess.setMessageType(MessageType.CREATE_MULTI_PERSON_CHAT);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(YychatClientConnection.s.getOutputStream());
                oos.writeObject(mess);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
        }

        if(e.getSource() == jb2){
            this.dispose();
        }
    }
}
