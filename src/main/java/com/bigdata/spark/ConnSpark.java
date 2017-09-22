package com.bigdata.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * Created by Administrator on 2017/9/20.
 * Author：charlesXu
 * file: ${Name}.scala
 * time: 2017/09/20
 */
public class ConnSpark {

//    public static final String master = "spark://172.30.0.41:7077";
    public static final String master = "spark://datanode1:7077";

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("demo").setMaster(master);
        JavaSparkContext sc = new JavaSparkContext(conf);
        System.out.println(sc);
        sc.close();
    }
}
