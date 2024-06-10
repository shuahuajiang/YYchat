package com.yychat.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.yychat.control.YychatClientConnection;
import com.yychat.model.User;
import com.yychat.model.UserType;

public class ForgetPassword extends JFrame implements ActionListener {

    JLabel j1, j4;
    JButton j;
    JTextField f1, f4;

    public ForgetPassword() {
        j1 = new JLabel("YY号码:", JLabel.RIGHT);
        j4 = new JLabel("新密码:", JLabel.RIGHT);
        j = new JButton("修改密码");
        j.addActionListener(this);
        f1 = new JTextField(15);
        f4 = new JTextField(15);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5; // 设置j1所在列的权重
        panel.add(j1, constraints);

        constraints.gridx = 1;
        constraints.weightx = 0.5; // 设置f1所在列的权重
        panel.add(f1, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.weightx = 0.5; // 设置j4所在列的权重
        panel.add(j4, constraints);

        constraints.gridx = 1;
        constraints.weightx = 0.5; // 设置f4所在列的权重
        panel.add(f4, constraints);

        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(j, constraints);

        add(panel);

        setTitle("修改密码");

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == j) {
            String username = f1.getText();
            String newPass = f4.getText();
            User user = new User();
            user.setUserName(username);
            user.setPassword(newPass);
            user.setUserType(UserType.FORGET_PASSWORD);
            if (new YychatClientConnection().ForgetPassword(user)) {
                JOptionPane.showMessageDialog(this, username + "修改密码成功!");
            } else {
                JOptionPane.showMessageDialog(this, username + "修改密码失败!");
            }
        }
    }

    public static void main(String[] args) {
        new ForgetPassword();
    }
}
