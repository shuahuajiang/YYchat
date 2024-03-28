package com.yychat.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendChat extends JFrame implements ActionListener {
    JTextArea jta;
    JScrollPane jsp;
    JTextField jtf;
    JButton jb;
    public FriendChat(){
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
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("聊天界面");
        this.setIconImage(new ImageIcon("images/duck2.gif").getImage());
        this.setVisible(true);
    }

    public static void main(String[] args) {
        FriendChat fc = new FriendChat();
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == jb){                    //点击发送按钮
            jta.append(jtf.getText() + "\r\n");      //在多行文本框追加新的文本
        }
    }

}
