package com.bigdata.hive;

import java.sql.*;

import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2017/9/20.
 * Author：charlesXu
 * file: TestHive.java
 * time: 2017/09/20
 * desc: Hive 的javaAPI
 *   博客：   http://787141854-qq-com.iteye.com/blog/2068303
 *  启动hive 的远程服务接口命令执行： hive --service hiveserver >/dev/null 2>&1 &
 */
public class TestHive {

    private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive://bigadata-hadoop:10000/default";
    private static String user = "root";
    private static String password = "Aa12346";
    private ResultSet res;
    private static final Logger log = Logger.getLogger(TestHive.class);

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConn();
            stmt = conn.createStatement();

            String tableName = dropTable();
        }catch (){

        }

    }
    private static Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    private static String dropTable(Statement stmt){
        String tableName = "testHive";  //创建的表名
        sql = "drop table" + tableName;
        stmt.executeQuery(sql);
        return tableName;
    }
}
