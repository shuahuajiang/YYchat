package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class button extends JFrame {

    public button() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton loginButton = new JButton("Login");
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setContentAreaFilled(false); // 不填充按钮的默认背景

        // 为按钮添加圆角边框
        loginButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // 绘制圆角背景
        loginButton.setOpaque(false);
        loginButton.setBackground(new Color(0, 102, 204)); // 设置按钮的背景色
        loginButton.setPreferredSize(new Dimension(150, 40)); // 设置按钮的大小

        panel.add(loginButton, BorderLayout.CENTER);
        add(panel);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 102, 204)); // 设置绘制颜色
        g2d.fill(new RoundRectangle2D.Double(100, 100, 150, 40, 20, 20)); // 绘制圆角矩形
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            button example = new button();
            example.setVisible(true);
        });
    }
}