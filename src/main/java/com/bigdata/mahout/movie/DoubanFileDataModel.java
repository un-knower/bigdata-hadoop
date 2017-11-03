package com.bigdata.mahout.movie;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/3.
 * Author：charlesXu
 * file: DoubanFileDataModel.java
 * desc: 数据处理，建立两张表的关联关系
 * time: 2017/11/03
 */
public class DoubanFileDataModel extends FileDataModel{

    public static Map<String, Long> userNameAndIDMapping = new HashMap<>();
    public static Map<Long, String> userIdAndNameMapping = new HashMap<>();
    private static long userID = 0;

    public DoubanFileDataModel(File dataFile) throws IOException {
        super(dataFile);
    }

    public DoubanFileDataModel(File datafile, String delimiterRegex) throws IOException {
        super(datafile, delimiterRegex);
    }

    public DoubanFileDataModel(File dataFile, boolean transpose, long minReloadIntervalMS) throws IOException {
        super(dataFile, transpose, minReloadIntervalMS);
    }

    public DoubanFileDataModel(File dataFile, boolean transpose, long minReloadIntervalMS, String delemiterRegex) throws IOException {
        super(dataFile, transpose, minReloadIntervalMS, delemiterRegex);
    }

    protected long readUserIDFromString(String value){
        value = value.trim();
        if (userNameAndIDMapping.containsKey(value)){
            return userNameAndIDMapping.get(value);
        }
        userNameAndIDMapping.put(value, userID);
        userIdAndNameMapping.put(userID, value);
        userID ++;
        return (userID - 1);   //dubug看一下为什么要 -1
    }

    protected long readItemIDFromString(String value){
        value = value.trim();
        return super.readItemIDFromString(value);     //重写父类
    }
    protected void processLine(String line, FastByIDMap<?> data, FastByIDMap<FastByIDMap<Long>> timestamps, boolean fromPriorData){
        String[] fields = line.split(",");
        if (fields[2].equals("-1")){
            fields[2] = "3";
        }
        line = fields[0] + "," + fields[1] + "," + fields[2];

        super.processLine(line, data, timestamps, fromPriorData);
    }

    protected void processLineWithoutID(String line, FastByIDMap<FastIDSet> data, FastByIDMap<FastByIDMap<Long>> timestamps){
        String[] fields = line.split(",");
        if (fields[2].equals("-1")){
            fields[2] = "3";
        }
        line = fields[0] + "," + fields[1] + "," + fields[2];
        super.processLineWithoutID(line, data, timestamps);
    }
}
