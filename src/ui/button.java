package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class button extends JButton {
    private static final long serialVersionUID = 1L;
    private final int radius;

    public button(String text, int radius) {
        super(text);
        this.radius = radius;
        setContentAreaFilled(false); // 去掉背景
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2d = (Graphics2D) g.create();
        // 创建一个圆角矩形
        RoundRectangle2D roundRect = new RoundRectangle2D.Double(200, 200, width, height, radius, radius);
        g2d.setColor(getBackground());
        // 填充圆角矩形
        g2d.fill(roundRect);
        // 设置文字颜色
        g2d.setColor(getForeground());
        // 绘制文字
        super.paintComponent(g2d);
        // 释放资源
        g2d.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rounded Corner Button Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(new button("Click Me", 20));
        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}
