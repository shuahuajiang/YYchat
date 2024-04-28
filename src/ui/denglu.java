package ui;

import javax.swing.*;
import java.awt.*;

public class denglu extends JPanel {

    private JLabel jl1, jl2;
    private JTextField jtf;
    private JPasswordField jpf;
    private JCheckBox jc1, jc2;

    public denglu() {
        setLayout(new GridBagLayout());
        setBackground(Color.white);

        jl1 = new JLabel("YY号码");
        jl2 = new JLabel("YY密码");

        jtf = new JTextField(15);
        jpf = new JPasswordField(15);

        jc1 = new JCheckBox("隐身登录");
        jc1.setBackground(Color.white);
        jc2 = new JCheckBox("记住密码");
        jc2.setBackground(Color.white);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;

        add(jl1, gbc);

        gbc.gridy++;
        add(jtf, gbc);

        gbc.gridy++;
        add(jl2, gbc);

        gbc.gridy++;
        add(jpf, gbc);

        gbc.gridy++;
        add(jc1, gbc);

        gbc.gridy++;
        add(jc2, gbc);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new denglu());
        frame.pack();
        frame.setVisible(true);
    }
}
