package com.yychat.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sun.xml.internal.ws.resources.SenderMessages;
import com.yychat.control.YychatClientConnection;
import com.yychat.model.User;


import com.yychat.model.Message;
import com.yychat.model.MessageType;
import com.yychat.model.UserType;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import java.net.Socket;
import javax.imageio.ImageIO;

public class ClientLogin extends JFrame implements ActionListener {

    //创建hashmap，用来保存好友列表界面
    public static HashMap hmFriendList = new HashMap<String, FriendList>();

    //定义标签组件
    JLabel jl;
    JButton jb1, jb2, jb3;
    JPanel jp;

    //定义登录界面中间部分组件
    JLabel jl1, jl2,  jl4;
    JTextField jtf;
    JPasswordField jpf;
    JButton jb4,jl3;
    JCheckBox jc1, jc2;
    JPanel jp1, jp2, jp3;
    JTabbedPane jtp;

    public ClientLogin() {

//     设置字体
        Font font = new Font("Serif", Font.BOLD, 16);
        Font font2 = new Font("宋体", Font.PLAIN, 12);

//        jl = new JLabel("JAVA聊天室");
        jl = new JLabel(new ImageIcon("images/head.gif")); //图片标签
        this.add(jl, "North");  //标签组件添加到窗体北部

        //创建登录界面中间部分组件
        jl1 = new JLabel("YY账号：", JLabel.RIGHT);
        jl1.setForeground(Color.GRAY);
        jl1.setFont(font);
        jl2 = new JLabel("YY密码：", JLabel.RIGHT);
        jl2.setForeground(Color.GRAY);
        jl2.setFont(font);
        jl4 = new JLabel("找回密码", JLabel.LEFT);
        jl4.setForeground(Color.GRAY);
        jl4.setFont(font2);

        ImageIcon downIcon = new ImageIcon("images/down.png");
        ImageIcon resized_downIcon = resizeImage(downIcon, 20, 20); // 调整为50x50的尺寸
        jb4 = new JButton(new ImageIcon(resized_downIcon.getImage()));
        jb4.setHorizontalAlignment(SwingConstants.LEFT);
        //背景透明
        jb4.setOpaque(false);
        jb4.setContentAreaFilled(false);
        jb4.setBorderPainted(false);

        ImageIcon lookIcon = new ImageIcon("images/look.png");
        ImageIcon resized_lookIcon = resizeImage(lookIcon, 20, 20); // 调整为50x50的尺寸
        jl3 = new JButton(new ImageIcon(resized_lookIcon.getImage()));
        jl3.setHorizontalAlignment(SwingConstants.LEFT);
        //背景透明
        jl3.setOpaque(false);
        jl3.setContentAreaFilled(false);
        jl3.setBorderPainted(false);


        //透明文本框加下划线
        jtf = new CustomTextField();
        jpf = new CustomTextField1();

        jc1 = new JCheckBox("隐身登录");
        jc1.setHorizontalAlignment(SwingConstants.RIGHT);
        jc1.setForeground(Color.GRAY);
        jc1.setBackground(Color.white);
        jc1.setFont(font2);

        ImageIcon uncheckedIcon = new ImageIcon("images/checkbox2.png"); // 未选中时的图标
        ImageIcon resizedIcon = resizeImage(uncheckedIcon, 15, 15); // 调整为50x50的尺寸

        ImageIcon checkedIcon = new ImageIcon("images/checkbox.png"); // 选中时的图标
        ImageIcon resizedIcon2 = resizeImage(checkedIcon, 15, 15);
        jc1.setIcon(resizedIcon); // 设置未选中时的图标
        jc1.setSelectedIcon(resizedIcon2); // 设置选中时的图标
        jc1.setSelected(true); //默认选中

        jc2 = new JCheckBox("记住密码");
        jc2.setHorizontalAlignment(SwingConstants.CENTER);
        jc2.setForeground(Color.GRAY);
        jc2.setBackground(Color.white);
        jc2.setFont(font2);
        jc2.setIcon(resizedIcon); // 设置未选中时的图标
        jc2.setSelectedIcon(resizedIcon2); // 设置选中时的图标
        jc2.setSelected(true); //默认选中

        jp1 = new JPanel(new GridLayout(3, 3)); //网格布局
        jp1.setBackground(Color.white);  //设置背景颜色


        jp1.add(jl1);
        jp1.add(jtf);
//        jp1.add(jb4); //在面板添加9个组件
        jp1.add(jb4); //空组件
        jp1.add(jl2);
        jp1.add(jpf);
//        jp1.add(jl3);
        jp1.add(jl3); //空组件
        jp1.add(jc1);
        jp1.add(jc2);
        jp1.add(jl4);

        this.add(jp1, "Center");



        jb1 = new JButton(new ImageIcon("images/login.gif")); //图片按钮
        jb1.addActionListener(this);  //在登录按钮上添加动作监听器
        jb1.setOpaque(false);
        jb1.setContentAreaFilled(false);
        jb1.setBorderPainted(false);
        Icon icon = jb1.getIcon();
        if (icon instanceof ImageIcon) {
            ImageIcon imageIcon = (ImageIcon) icon;
            // 获取图像
            Image image = imageIcon.getImage();

            // 计算新的图像尺寸（这里放大两倍）
            int width = image.getWidth(null) * 2;
            int height = image.getHeight(null) * 2;

            // 创建一个新的缓冲图像，用于放大
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // 获取图像绘图上下文
            Graphics2D g2d = bufferedImage.createGraphics();
            // 执行放大操作
            g2d.drawImage(image, 0, 0, width, height, null);
            g2d.dispose();

            // 将放大后的图像设置回 ImageIcon 中
            ImageIcon enlargedIcon = new ImageIcon(bufferedImage);

            // 将放大后的图像设置回 JButton 中
            jb1.setIcon(enlargedIcon);
        }


        //在注册按钮上添加动作事件的监听器对象
        jb2 = new JButton("注册账号");
        jb2.setAlignmentX(Component.LEFT_ALIGNMENT);
        jb2.addActionListener(this);
        //背景透明
        jb2.setOpaque(false);
        jb2.setContentAreaFilled(false);
        jb2.setBorderPainted(false);

        jb3 = new JButton(new ImageIcon("images/cancel.gif"));

        jp = new JPanel(new FlowLayout(FlowLayout.CENTER)); //创建面板对象
        jp.setBackground(Color.white);
         //在JPanel面板中添加按钮
        jp.add(jb2);
        jp.add(jb1);//默认FlowLayout流式布局
        jp.add(jb3);
        this.add(jp, "South"); //JPanel组件添加到窗体南部

        Image im = new ImageIcon("images/duck2.gif").getImage(); //创建image对象
        this.setIconImage(im);  //窗体左上角图标

//        this.setBounds(800,600,350,250);  //设置窗体边界（位置和大小）
        this.setLocationRelativeTo(null);
        this.setSize(430, 370); //窗体大小
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //点击窗体关闭按钮
        this.setTitle("YY聊天");
        this.setVisible(true); //窗体可视
    }


    public static void main(String[] args) {
        ClientLogin cl = new ClientLogin(); //创建窗体对象
    }

    //添加
    public void actionPerformed(ActionEvent arg0) {
        //判断ActionEvent事件来源，调用注册方法来发送user对象
//        if (arg0.getSource() == jb1){    //鼠标点击登录按钮

        String name = jtf.getText(); //获取用户名
        String password = new String(jpf.getPassword()); //获取登录密码

        User user = new User();//创建user类的对象
        user.setUserName(name);
        user.setPassword(password);
        //响应注册事件
        if (arg0.getSource() == jb2) {
            user.setUserType(UserType.USER_REGISTER); //设置user对象
            if (new YychatClientConnection().registerUser(user)) {
                JOptionPane.showMessageDialog(this, name + "用户注册成功");
            } else {
                JOptionPane.showMessageDialog(this, name + "用户名重复，请重新注册");
            }
        }
//          注册新用户，修改部分登录代码
        if (arg0.getSource() == jb1) {
            user.setUserType(UserType.USER_LOGIN_VALIDATE);

            Message mess = new YychatClientConnection().loginValidate1(user);  //返回消息对象
            if (mess.getMessageType().equals(MessageType.LOGIN_VALIDATE_SUCCESS)) {   //登录验证通过
                String allFriend = mess.getContent();   //登录端拿到全部好友名字
                FriendList fl = new FriendList(name,allFriend); //把好发名字传到好友列表窗体
                hmFriendList.put(name, fl);
                this.dispose();   //关闭登陆界面
            } else {
                JOptionPane.showMessageDialog(this, "密码错误，重新登录");
            }

//            if (new YychatClientConnection().loginValidate(user)){
//                hmFriendList.put(name,new FriendList(name));
//                System.out.println("客户端登录成功");
//
//                Message mess = new Message();
//                mess.setSender(name);
//                mess.setReceiver("Server");
//                mess.setMessageType(MessageType.REQUEST_ONLINE_FRIEND);
//
//                //使用 sendMessage()方法发送消息
//                sendMessage(YychatClientConnection.s,mess);
//
//                //设置消息类型发送到服务器
//                mess.setMessageType(MessageType.NEW_ONLINE_TO_ALL_FRIEND);
//                sendMessage(YychatClientConnection.s,mess);
//                this.dispose();
//            }else {
//                JOptionPane.showMessageDialog(this,"密码错误，重新登录");
//            }

//            new FriendList(name);        //创建好友列表界面
//            this.dispose();              //关闭登录界面
//            new YychatClientConnection();
//            new YychatClientConnection().loginValidate(user);
            }
        }
        public void sendMessage (Socket s, Message mess){
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(mess);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


  //透明文本框加下划线
   public class CustomTextField extends JTextField {
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
            g.setColor(Color.lightGray); // 设置下划线颜色
            g.drawLine(0, height - 1, width, height - 1); // 绘制线条
        }
    }

   public class CustomTextField1 extends JPasswordField {
        public CustomTextField1() {
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
            g.setColor(Color.lightGray); // 设置下划线颜色
            g.drawLine(0, height - 1, width, height - 1); // 绘制线条
        }

    }
    private static ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
