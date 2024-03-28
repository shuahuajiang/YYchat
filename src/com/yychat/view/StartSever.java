package com.yychat.view;

import javax.swing.*;
import java.awt.*;

public class StartSever extends JFrame {
    JButton jb1,jb2;
    public  StartSever() {
        jb1 = new JButton("启动服务器");
        jb1.setFont(new Font("宋体", Font.BOLD, 25));  //设置字体
        jb2 = new JButton("停止服务器");
        jb2.setFont(new Font("宋体", Font.BOLD, 25));

        this.setLayout(new GridLayout(1, 2));   //网格布局
        this.add(jb1);
        this.add(jb2);
        this.setSize(400, 100);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("images/duck2.gif").getImage());
        this.setTitle("YYchat 服务器");
        this.setVisible(true);
    }

    public static void main(String[] args) {
        StartSever ss = new StartSever();
    }

}
