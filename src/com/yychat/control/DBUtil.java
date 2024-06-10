package com.yychat.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.yychat.model.Message;
import java.util.Date;

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


    //增加忘记密码的方法，修改user表的密码
    public static int updateForgetPassword(String userName,String password)
    {
        int cnt = 0;
        String update_user_password = "update user set password = ? where username = ?";
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(update_user_password);
            psmt.setString(1, password);
            psmt.setString(2, userName);
            cnt = psmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return cnt;
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

    public static String seekAllFriend(String userName,int FriendType) {
        String allFriend = "";
        String userrelation_query_str = "select slaveuser from userrelation where masteruser=? and relation=?";

        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(userrelation_query_str);
            psmt.setString(1, userName);
            psmt.setInt(2, FriendType);
            ResultSet rs = psmt.executeQuery();
            while (rs.next())
                allFriend = allFriend + " " + rs.getString(1);
            System.out.println(userName + "全部好友:" + allFriend);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allFriend;
    }

    //添加 seekFriend （）方法
    public static boolean seekFriend(String sender,String newFriend,int friendType){
        boolean seekSuccess = false;
        String userrelation_query_str = "select * from userrelation where masteruser=? and slaveuser=? and relation=?";
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(userrelation_query_str);
            psmt.setString(1, sender);
            psmt.setString(2, newFriend);
            psmt.setInt(3, friendType);
            ResultSet rs = psmt.executeQuery();
            seekSuccess = rs.next(); //有同名用户返间true
        }  catch (SQLException e) {
            e.printStackTrace();
        }
            return seekSuccess;
    }

    //添加 insertIntoFriend （）方法
    public static int insertIntoFriend(String sender,String newFriend,int friendType){
        int count = 0;
        String userrelation_insertInto_str = "insert into userrelation(masteruser,slaveuser,relation) values(?,?,?)";
        try {
            PreparedStatement psmt = conn.prepareStatement(userrelation_insertInto_str);
            psmt.setString(1, sender);
            psmt.setString(2, newFriend);
            psmt.setInt(3, friendType);
            count = psmt.executeUpdate();
        }  catch(SQLException e) {
            e.printStackTrace();
        }       return count;
    }

    //	删除好友
    public static int deleteIntoFriend(String sender) {
        int count=0;
        String userrelation_deletetInto_str="delete from userrelation where slaveuser=?";
        try {
            PreparedStatement psmt = conn.prepareStatement(userrelation_deletetInto_str);
            psmt.setString(1,sender);
            count=psmt.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static boolean seekChatsName(String chatsName){
        boolean result = false;
        String chats_query_str = "select * from groupchats where chatsname=?";
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(chats_query_str);
            psmt.setString(1,chatsName);
            ResultSet rs = psmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  result;
    }

    public static int insertIntoChats(String chatsName, String chatsNumber) {
        int count = 0;
        String chats_insertInto_str = "insert into groupchats(chatsname,chatsnumber) values(?,?)";
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(chats_insertInto_str);
            psmt.setString(1,chatsName);
            psmt.setString(2,chatsNumber);
            count = psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public  static  void  inserIntouserChats(String userName,String chatsName){
        String userchats_insertInto_str = "insert into userchats(user,chats) values(?,?)";
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(userchats_insertInto_str);
            psmt.setString(1,userName);
            psmt.setString(2,chatsName);
            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean seekChatsNumber(String chatsNumber){
        boolean result = false;
        String chats_query_str = "select * from groupchats where chatsnumber=?";
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(chats_query_str);
            psmt.setString(1,chatsNumber);
            ResultSet rs = psmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  result;
    }

    public static void createChatsTable(String chatsName, String chatsNumber){
        String chats_create_table_str = "create table "+chatsName+"(\n" +
                "id int(11) auto_increment,\n" +
                "member varchar(20),\n" +
                "primary key(id)\n" +
                ") ENGINE=INNODB auto_increment=1 DEFAULT CHARSET=utf8;";
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(chats_create_table_str);
            psmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void joinChats(String chatsName,String userName){
        String chats_insertInto_str = "insert into " +chatsName +"(member) values(?)";
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(chats_insertInto_str);
            psmt.setString(1,userName);
            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String seekJoinChats(String userName){
        String chats = "";
        String userchats_query_str = "select chats from userchats where user=?";
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(userchats_query_str);
            psmt.setString(1,userName);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()){
                chats =chats + " "+ rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  chats;
    }

    public static String seekChatsMember(String chatsName){
        String chatsMember = "";
        String chats_query_str="select member from "+chatsName;
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(chats_query_str);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()){
                chatsMember = chatsMember +" " + rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return chatsMember;
    }

    public static boolean seekChatsUser(String chatsName,String userName){
        boolean result = false;
        String chats_quert_str = "select id from "+chatsName+" where member=?";
        System.out.println("select id from "+chatsName+" where member="+userName);
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(chats_quert_str);
            psmt.setString(1,userName);
            ResultSet rs = psmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public  static  boolean seekChats(String chatsName){
        boolean result = false;
        String chats_query_str="select chatsnumber from groupchats where chatsname=?";
        PreparedStatement psmt;
        try {
            System.out.println(chatsName);
            psmt = conn.prepareStatement(chats_query_str);
            psmt.setString(1,chatsName);
            ResultSet rs = psmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;

    }





    //新增savaMessage方法
    public static void saveMessage(Message mess) {
        String message_insertInto_str = "insert into message(sender,receiver,content,sendtime)values(?,?,?,?)";
        PreparedStatement psmt;
        try {
            psmt = conn.prepareStatement(message_insertInto_str);
            psmt.setString(1, mess.getSender());
            psmt.setString(2, mess.getReceiver());
            psmt.setString(3, mess.getContent());
            Date sendTime = mess.getSendTime();//从Message 对象中拿到发送时闻
            psmt.setTimestamp(4, new java.sql.Timestamp(sendTime.getTime()));//把发送时间转换成Timestamp 类型
            psmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
