package ui;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class checkbox {
    public static void main(String[] args) {
        // 读取原始图片
        ImageIcon originalIcon = new ImageIcon("images/checkbox2.png");

        // 调整图片尺寸
        ImageIcon resizedIcon = resizeImage(originalIcon, 50, 50); // 调整为50x50的尺寸

        // 创建复选框并设置调整后的图标
        JCheckBox checkBox = new JCheckBox("Custom CheckBox");
        checkBox.setIcon(resizedIcon);
        checkBox.setSelectedIcon(resizedIcon);

        // 创建窗口并显示
        JFrame frame = new JFrame("Resized CheckBox Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(checkBox);
        frame.pack();
        frame.setVisible(true);
    }

    // 调整图片尺寸的方法
    private static ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}