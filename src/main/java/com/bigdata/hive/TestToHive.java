package com.bigdata.hive;


import org.junit.Before;
import org.junit.Test;

import java.sql.*;

/**
 * java远程操作hive
 * https://www.cnblogs.com/liumingyi/p/5955670.html
 */
public class TestToHive {
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;

    @Before
    public void getConnection() throws SQLException {
        try{
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            connection = DriverManager.getConnection("jdbc:hive2://192.168.18.130:10000/", "root", "root");
            System.out.println(connection);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //创建表
    @Test
    public void createTable(){
        String sql = "create table goods2(id int,name string) row format delimited fields terminated by '\t' ";
        try{
            ps = connection.prepareStatement(sql);
            ps.execute(sql);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除表
    @Test
    public void dropTable(){
        String sql = "drop table goods";
        try {
            ps = connection.prepareStatement(sql);
            ps.execute(sql);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //插入数据
    @Test
    public void insert(){
        String sql = "load data inpath '/goods.txt' into table goods";
        try{
            ps = connection.prepareStatement(sql);
            ps.execute(sql);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询数据
    @Test
    public void findData() throws SQLException {
        String sql = "select * from goods";
        ps = connection.prepareStatement(sql);
        ps.execute();
        while (rs.next()){
            System.out.println(rs.getObject(1) + "---" + rs.getObject(2));
        }
        close();
    }

    /**
     * 关闭连接
     *
     */
    private void close() {
        try{
            if (rs != null){
                rs.close();
            }
            if (ps != null){
                ps.close();
            }
            if (connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
