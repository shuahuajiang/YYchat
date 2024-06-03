package com.yychat.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.yychat.control.YychatClientConnection;
import com.yychat.model.User;
import com.yychat.model.UserType;

public class ForgetPassword extends JFrame implements ActionListener{

    JLabel j1,j2,j3,j4;
    JButton j;
    JTextField f1,f2,f3,f4;

    public ForgetPassword() {
        j1 = new JLabel("YY号码",JLabel.CENTER);
        j2 = new JLabel("手机号码",JLabel.CENTER);
        j3 = new JLabel("电子邮箱",JLabel.CENTER);
        j4 = new JLabel("新密码",JLabel.CENTER);
        j = new JButton("修改密码");//创建面板,给修改密码按钮创建监听器
        j.addActionListener(this);
        f1 = new JTextField();
        f2 = new JTextField();//创建文本框
        f3 = new JTextField();
        f4 = new JTextField();
        this.add(j1);
        this.add(f1);
        this.add(j2);
        this.add(f2);
        this.add(j3);
        this.add(f3);
        this.add(j4);
        this.add(f4);
        this.add(j);

        this.setLayout(new GridLayout(5,3));
        this.setSize(400,400);
        this.setLocationRelativeTo(null);
        this.setTitle("找回密码");
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==j) {
            String username = f1.getText();
            String phone = f2.getText();
            String email = f3.getText();
            String newPass = f4.getText();
            //监听该按钮,如果点击了修改密码的按钮,则将我们输入的四个信息传入user对象

            User user  = new User();
            user.setUserName(username);
            user.setPhone(phone);
            user.setEmail(email);
            user.setPassword(newPass);

            user.setUserType(UserType.FORGET_PASSWORD);
            if(new YychatClientConnection().ForgetPassword(user)) {
                //调用客户端forgetpassword方法
                JOptionPane.showMessageDialog(this, username+"找回密码成功!");
            }else {
                JOptionPane.showMessageDialog(this, username+"找回密码失败!");
            }
        }
    }
}