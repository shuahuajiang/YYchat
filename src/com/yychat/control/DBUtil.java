package com.yychat.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DBUtil {
    public static String db_url = "jdbc:mysql://127.0.0.1:3306/yychat2022s?useUnicode=" +
            "true&characterEncoding=utf-8"; //支持数据库中文数据
    public static String db_username = "root";
    public static String db_password = "JHJjhj20030418";

    public static Connection conn = getConnection();

    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(db_url,db_username,db_password);
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    //注册新用户到 use= 表中，增加査询用户的方法
    public static boolean seekUser(String userName){
        boolean seekSuccess = false;
        String user_query_str= "select * from user where username=?";
        PreparedStatement psmt;
        try {
            psmt= conn.prepareStatement(user_query_str);
            psmt.setString(1,userName);
            ResultSet rs = psmt.executeQuery();
            seekSuccess = rs.next();  //有同名用户返回true
        }  catch(SQLException e) {
            e.printStackTrace();
        } return seekSuccess;
    }

    //增加在user表中插入新用户的方法
    public static int insertIntoUser(String userName,String password) {
        int count = 0;
        String user_inset_into_str = "insert into user(username,password) values(?,?)";
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(user_inset_into_str);
            psmt.setString(1, userName);
            psmt.setString(2, password);
            count = psmt.executeUpdate();  //返国成功插入的记录数目
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static boolean loginValidate(String userName,String password){
        boolean loginSuccess = false;
        String user_query_str = "select * from user where username=? and password=?";
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(user_query_str);

            psmt.setString(1,userName);
            psmt.setString(2,password);
            ResultSet rs = psmt.executeQuery();
            //loginsucces为true，表明在user表中查询到记录，否则为false
            loginSuccess = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginSuccess;
    }
}
