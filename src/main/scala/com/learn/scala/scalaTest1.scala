package com.learn.scala

/**
  * Created by charles on 2017/6/11.
  */
object scalaTest1 {
  def main(args: Array[String]): Unit = {

    """
      |for (i <- 1 to 10 if i % 2 == 0) println(i)
      |"""
    def printAll(numbers: Int*): Unit ={
      println("class" + numbers.getClass)
    }
    printAll(1,2,4)
  }

}
