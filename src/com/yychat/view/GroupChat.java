package com.yychat.view;

import com.yychat.control.YychatClientConnection;
import com.yychat.model.Message;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GroupChat extends JFrame implements ActionListener {
    JTextArea jta1;
    JTextArea setMessage;
    JPanel onlinePerson;
    JScrollPane jsp1,jsp2,jsp3;
    JButton jb1;
    JPanel jp1,jp2,jp3,jp4;
    JTextArea jta2;
    String chatsName;
    String sender;

    public GroupChat(String chatsName, String userName) {
        this.sender = userName;
        this.chatsName = chatsName;
        this.jta1 = new JTextArea();
        this.jta1.setEditable(false);
        this.jta1.setLineWrap(true);
        this.jta1.setWrapStyleWord(true);
        this.jsp2 = new JScrollPane(this.jta1);
        this.jsp2.setBounds(0, 30, 600, 370);
        this.jp3 = new JPanel(new GridLayout(1, 5));
        this.jp3.add(new JButton(new ImageIcon("images/公告.png")));
        this.jp3.add(new JButton(new ImageIcon("images/土地.png")));
        this.jp3.add(new JButton(new ImageIcon("images/土地.png")));
        this.jp3.add(new JButton(new ImageIcon("images/土地.png")));
        this.jp3.add(new JButton(new ImageIcon("images/土地.png")));
        this.jp3.setBounds(0, 0, 300, 30);
        this.jp4 = new JPanel();
        this.jp4.setLayout((LayoutManager)null);
        this.jp4.add(this.jp3);
        this.jp4.add(this.jsp2);
        this.jp4.setBounds(0, 0, 600, 400);
        this.jp1 = new JPanel();
        this.jp1.setLayout((LayoutManager)null);
        this.setMessage = new JTextArea();
        this.jb1 = new JButton("发送");
        this.jb1.setBounds(520, 125, 70, 25);
        this.jb1.addActionListener(this);
        this.jp1.add(this.setMessage);
        this.jp1.add(this.jb1);
        this.setMessage.setLineWrap(true);
        this.setMessage.setWrapStyleWord(true);
        this.jsp3 = new JScrollPane(this.setMessage);
        this.jsp3.setBounds(0, 0, 600, 120);
        this.jp1.add(this.jsp3);
        this.jp1.setBounds(0, 410, 600, 200);
        this.onlinePerson = new JPanel(new GridLayout(30, 1));

        for(int i = 0; i < 10; ++i) {
            this.onlinePerson.add(new JLabel("成员" + i));
        }

        this.jsp1 = new JScrollPane(this.onlinePerson);
        this.jsp1.setBounds(0, 310, 200, 240);
        this.jp2 = new JPanel();
        this.jp2.setLayout((LayoutManager)null);
        this.jta2 = new JTextArea("广告位招租");
        this.jta2.setFont(new Font("楷体", 1, 26));
        this.jta2.setBounds(0, 0, 200, 300);
        this.jta2.setEditable(false);
        this.jp2.add(this.jta2);
        this.jp2.add(this.jsp1);
        this.jp2.setBounds(600, 0, 200, 600);
        this.setLayout((LayoutManager)null);
        this.add(this.jp4);
        this.add(this.jp2);
        this.add(this.jp1);
        this.setVisible(true);
        this.setBounds(500, 500, 818, 600);
        this.setTitle(chatsName);
        this.setResizable(false);
    }

    public static void main(String[] args) {
        new GroupChat("shagou", "shagou");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.jb1) {
            String content = this.setMessage.getText();
            Message mess = new Message();
            mess.setSender(this.sender);
            mess.setContent(content);
            mess.setChatName(this.chatsName);
            mess.setMessageType("35");
            OutputStream os = null;

            try {
                os = YychatClientConnection.s.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(mess);
            } catch (IOException var6) {
                throw new RuntimeException(var6);
            }

            this.setMessage.setText("");
        }

    }

    public void append(Message mess) {
        JTextArea var10000 = this.jta1;
        String var10001 = mess.getSender();
        var10000.append(var10001 + "说： " + mess.getContent() + "\r\n");
    }
}
