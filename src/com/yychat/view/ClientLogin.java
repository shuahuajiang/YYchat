package com.yychat.view;

import javax.swing.*;

public class ClientLogin extends JFrame {
    JLabel jl;  //定义标签组件
    public ClientLogin(){
        jl = new JLabel("JAVA聊天室");
        this.add(jl,"North");  //标签组件添加到窗体北部

        this.setBounds(800,600,350,250);  //设置窗体边界（位置和大小）
        this.setVisible(true); //窗体可视
    }

    public static void main(String[] args) {
        ClientLogin cl = new ClientLogin();
    }
}
