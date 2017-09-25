package com.bigdata.fnlp;

import org.fnlp.util.exception.LoadModelException;

import java.io.IOException;

/**
 * Created by Administrator on 2017/9/25.
 * Author：charlesXu
 * file: ${Name}.scala
 * time: 2017/09/25
 */
public class doSplit {
    public static void main(String[] args) throws IOException, LoadModelException {
        Splittlbb splittlbb = new Splittlbb();
        splittlbb.processFile();
        System.out.println("执行分词方法成功");
    }
}
