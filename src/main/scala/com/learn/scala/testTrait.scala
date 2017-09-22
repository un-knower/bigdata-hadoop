package com.learn.scala

/**
  * Created by Administrator on 2017/9/22.
  * Author：charlesXu
  * file: testTrait.scala
  * time: 2017/09/22
  * desc: Trait
  */

/**
  *  trait (特质) ： 相当于java的接口，比接口功能强大。
  *    与接口不同的是，他还可以定义属性和方法的实现
  *    一般情况下Scala的类只能够继承单一父类，但是如果是Trait的话就可以继承多个，从结果来看就是多重继承。
  *
  *    ========================================
  * 特征构造顺序
  *  特征也可以有构造器，由字段的初始化和其他特整体中的语句构成。
  *  构造器的执行顺序：
  *       调用超类的构造器
  *       特征构造器在超类构造器之后，类构造器之前执行
  *       特征由左到右被构造
  *       每个特征中，父特征先被构造
  *       如果多个特征共有一个父特征，父特征不会被重复构造
  *       所有特征被构造完毕，子类被构造。
  */

  trait Equal{
     def isEqual(x: Any): Boolean
     def isNotEqual(x: Any): Boolean = !isEqual(x)
  }

 class Point(xc: Int, yc: Int) extends Equal{
      var x: Int = xc
      var y: Int = yc
      def isEqual(obj: Any) =
         obj.isInstanceOf[Point] &&
         obj.asInstanceOf[Point].x == x
 }

object testTrait {
  def main(args: Array[String]): Unit = {
    var p1 = new Point(2,3)
    var p2 = new Point(2,4)
    var p3 = new Point(3,3)

    println(p1.isNotEqual(p2))
    println(p1.isNotEqual(p3))
    println(p1.isNotEqual(2))
  }
}
