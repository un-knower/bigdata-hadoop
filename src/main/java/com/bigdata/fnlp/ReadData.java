package com.bigdata.fnlp;

import java.sql.*;

/**
 * Created by Administrator on 2017/9/26.
 * Author：charlesXu
 * file: ReadData.java
 * time: 2017/09/26
 * desc: 读取数据库，并将数据追加到一个文件中输出
 */
public class ReadData {

    public static void main(String[] args) throws SQLException {
        Connection conn;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://192.168.0.120:3306/dev_qzscenter";
        String userName = "root";
        String passwd = " ";

        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url,userName, passwd);
            if (!conn.isClosed()){
                System.out.println("Connection Succeeded!");
                Statement stmt = conn.createStatement();
                String sql = "SELECT content from cms_art_txtdata;";
                ResultSet rs = stmt.executeQuery(sql);
                System.out.println("执行结果如下所示:");

                String content = null;
                while (rs.next()){
                    //获取statement这列数据
                    content = rs.getString("content");
                    System.out.println(content + "\t");
                }
                rs.close();
                conn.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry, can't find the Driver");
            e.printStackTrace();
        }
    }
}
