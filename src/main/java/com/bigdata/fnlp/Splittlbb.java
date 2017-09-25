package com.bigdata.fnlp;

import org.fnlp.util.exception.LoadModelException;

import java.io.*;

/**
 * Created by Administrator on 2017/9/25.
 * Author：charlesXu
 * file: ${Name}.scala
 * time: 2017/09/25
 * desc: 分词
 */
public class Splittlbb {

    public void processFile() throws IOException, LoadModelException {
        String filePath = this.getClass().getClassLoader().getResource("text/tlbb.txt").getPath();
        BufferedReader in = new BufferedReader(new FileReader(filePath));

        File outFile = new File("E:/tlbb_t.txt");
        if (outFile.exists()){
            outFile.delete();
        }
        FileOutputStream fop = new FileOutputStream(outFile);

        //构建FileOutputStream对象
        String line = in.readLine();
        OutputStreamWriter writer = new OutputStreamWriter(fop, "utf-8");

        FudanTokenizer tokenizer = new FudanTokenizer();
        while (line != null){
            line = tokenizer.processSentence(line);
            writer.append(line);
            line = in.readLine();
        }
        in.close();
        writer.close();
        fop.close();
    }
}
