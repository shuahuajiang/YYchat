package ui;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

class CustomTextField extends JTextField {
    public CustomTextField() {
        setOpaque(false); // 让文本框透明
        setBorder(new EmptyBorder(0, 0, 1, 0)); // 设置下边框
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 绘制父类的外观
        super.paintComponent(g);

        // 获取文本框的尺寸
        int width = getWidth();
        int height = getHeight();

        // 绘制下划线
        g.setColor(Color.BLACK); // 设置下划线颜色
        g.drawLine(0, height - 1, width, height - 1); // 绘制线条
    }
}

public class text  {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Custom TextField");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        CustomTextField textField = new CustomTextField();
        textField.setPreferredSize(new Dimension(200, 30));
        panel.add(textField);

        frame.add(panel);
        frame.setVisible(true);
    }
}

