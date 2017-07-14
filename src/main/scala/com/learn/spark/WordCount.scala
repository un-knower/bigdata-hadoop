package com.learn.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by charles on 2017/6/4.
  */
object WordCount {
  def main(args: Array[String]): Unit = {
    /**
      * 1,创建spark的配置对象
      */
    val conf = new SparkConf().setAppName("WordCount").setMaster("bigdata-hadoop")

    /**
      * 2,创建SparkContext对象
      */
    val sc = new SparkContext(conf)
    /**
      * 3,根据具体的数据来源通过SparkContext来创建RDD
      */
    val linesRDD = sc.textFile("hdfs://user/bigdata/README.md")

  }

}
