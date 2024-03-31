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
