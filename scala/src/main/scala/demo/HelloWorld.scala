package demo

import java.awt.event
import java.text.SimpleDateFormat
import java.util.Calendar

object HelloWorld {

  def main(args: Array[String]) {
    println(firstDay()) // 输出 Hello World
  }

  def firstDay(): String = {
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyyMMdd")
    val cal: Calendar = Calendar.getInstance()
    cal.add(Calendar.MONTH, 0);
    cal.set(Calendar.DAY_OF_MONTH, 1); //设置为1号,当前日期既为本月第一天
    val yesterday = dateFormat.format(cal.getTime)
    yesterday
  }

  def myMethodName(): Unit = {

  }

  def handler(evt: event.ActionEvent) = {
    //var VariableName : DataType [=  Initial Value]
    var myVar: String = "Foo"
    val myVar1: String = "Foo"
    val myVar2 = "Foo"
    val myVar3 = 10
    for (a <- 1 to 10) {
      println("Value of a: " + a)
    }
    // 方法
    val f = (x: Int) => x + 3
  }

  /**
    * def functionName ([参数列表]) : [return type] = {
    * function body
    * return [expr]
    * }
    *
    * @param x
    * @return
    */
  def m(x: Int) = x + 3

  def printStrings(args: String*) = {
    var i: Int = 0
    for (arg <- args) {
      println("Arg value[" + i + "] = " + arg)
      i = i + 1
    }
  }

  // https://blog.csdn.net/bluishglc/article/details/52584401

}
