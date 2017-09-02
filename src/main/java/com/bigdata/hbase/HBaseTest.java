package com.bigdata.hbase;

import com.sun.org.apache.xpath.internal.operations.String;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by charles on 2017/9/2.
 */
public class HBaseTest {

    /**
     * 配置
     */
    static Configuration config = null;
    private Connection conn = null;
    private Table table = null;

    private HBaseAdmin admin = null;
    //定义配置对象HBaseConfiguration
    private HBaseConfiguration cfg = null;

    public HBaseTest() throws Exception{
        Configuration HBASE_CONFIG = new Configuration();
        HBASE_CONFIG.set("hbase.zookeeper.quorum", "192.168.64.131");
        HBASE_CONFIG.set("hbase.zookeeper.property.clientPort", "2181");

        cfg = new HBaseConfiguration(HBASE_CONFIG);
        admin = new HBaseAdmin(cfg);
    }

    //创建一张表
    public void createTable(String tableName, String columnFamily) throws Exception{
        if (admin.tableExists(tableName)){
            System.out.println(tableName + "存在!");
            System.exit(0);
        } else {
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            tableDescriptor.addFamily(new HColumnDescriptor(columnFamily));
            admin.createTable(tableDescriptor);
            System.out.println("Created Table Sucsessfully!");
        }
    }

    /**
     * 获取表的索欧表信息
     * @param
     */
    public List getAllTables() throws IOException{
        List<String> tables = null;
        if (admin != null){
           HTableDescriptor[] allTable = admin.listTables();
           if (allTable.length > 0){
               tables = new ArrayList<String>();
               for (HTableDescriptor hTableDescriptor : allTable){
                   tables.add(hTableDescriptor.getNameAsString());
                   System.out.println(hTableDescriptor.getNameAsString());
               }
           }
        }
        return tables;
    }

    /**
     * 插入数据
     * @param table
     * @param key
     * @param family
     * @param col
     * @param dataIn
     * @return
     * @throws IOException
     */
//    public boolean addOneRecord(String table, String key, String family,
//                                String col, byte[] dataIn) throws IOException{
//        //API被废弃
////        HTablePool tp = new HTablePool(cfg, 1000);
////        HTable tb = (HTable) tp.getTable(table);
//        table.setAutoFlushTo(false);
//        Put put = new Put(key.getBytes());
//        put.add(family.getBytes(), col.getBytes(), dataIn);
//        try{
//            tb.put(put);
//            System.out.println("Insert Data" + key + "Success!");
//            return true;
//        } catch (IOException e){
//            System.out.println("Insert Data" + key + "Fail!");
//            return false;
//        }
//    }

    public void getAllData(String tableName) throws IOException{
        HTable table = new HTable(cfg, tableName);
        Scan scan = new Scan();
        ResultScanner rs = table.getScanner(scan);
        for (Result r : rs){
            for (KeyValue kv : r.raw()){
                System.out.println(new String(kv.g)) + new String(kv.getValue());
            }
        }
    }


//    public static void main(String[] args) {
//        try{
//            HBaseTest hbase = new HBaseTest();
//            hbase.createTable("student", "faml");
//            hbase.getAllTables();
//            hbase.addOneRecord("student","id1","fam1","name","Jack".getBytes());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


}
