package ui;

import javax.swing.*;
import java.awt.*;

public class JLayered {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GridBagLayout Example");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // 第一行文本框
        JLabel label1 = new JLabel("Username:");
        JTextField textField1 = new JTextField(10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(label1, constraints);
        constraints.gridx = 1;
        panel.add(textField1, constraints);

        // 第二行文本框
        JLabel label2 = new JLabel("Password:");
        JTextField textField2 = new JTextField(10);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(label2, constraints);
        constraints.gridx = 1;
        panel.add(textField2, constraints);

        // 第三行按钮
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        JButton button3 = new JButton("Button 3");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(button1, constraints);
        constraints.gridx = 1;
        panel.add(button2, constraints);
        constraints.gridx = 2;
        panel.add(button3, constraints);

        frame.add(panel);
        frame.setVisible(true);
    }
}

