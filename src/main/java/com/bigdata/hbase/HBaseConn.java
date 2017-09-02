package com.bigdata.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by charles on 2017/9/2.
 */
public class HBaseConn {

    public static Configuration conf;
    public static Connection conn;
    public static Admin admin;


    public static void main(String[] args) throws IOException{

        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.64.131");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.master", "192.168.64.131:16010");

        conn = ConnectionFactory.createConnection(conf);
        HTableDescriptor table = new HTableDescriptor(TableName.valueOf("testConnTable"));
        table.addFamily(new HColumnDescriptor("group1"));

        if (admin.tableExists(table.getTableName())){
            admin.disableTable(table.getTableName());
            admin.deleteTable(table.getTableName());
        }
        admin.createTable(table);
    }
}
