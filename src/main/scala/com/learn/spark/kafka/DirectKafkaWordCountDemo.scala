package com.learn.spark.kafka

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds,StreamingContext}

/**
  * Created by charles on 2017/6/18.
  */
object DirectKafkaWordCountDemo {

  def main(args: Array[String]): Unit = {
     val sparkConf = new SparkConf().setMaster("bigdata-hadoop").setAppName("DirectKafkaWordCountDemo")

     val ssc = new StreamingContext(sparkConf,Seconds(3))

     val brokers = "192.168.64.131:9092"
  }

}
