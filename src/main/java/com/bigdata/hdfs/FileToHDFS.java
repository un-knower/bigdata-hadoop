package com.bigdata.hdfs;


import com.bigdata.utils.Constant;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.*;
import java.net.URI;

/**
 *  java 实现远程hdfs的文件操作（新建，上传，下载，删除）
 */
public class FileToHDFS {

    public static Configuration conf;

    static{
        conf = new Configuration();
        conf.set("fs.default.name", Constant.HADOOP_URL);
    }
    public static void main(String[] args) {
        try{
            uploadToHdfs();
            deleteFromHdfs();
            getDirectoryFromHdfs();
            appendToHdfs();
            readToHdfs();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("SUCCESS");
        }
    }

    /**
     *  从hdfs上读取文件
     */
    private static void readToHdfs() throws IOException {
        String dst = "qq-hdfs.txt";
        FileSystem fs = FileSystem.get(URI.create(dst), conf);
        FSDataInputStream hdfsInStream = fs.open(new Path(dst));

        OutputStream out = new FileOutputStream("F:text.txt");
        byte[] ioBuffer = new byte[1024];
        int readLen = hdfsInStream.read(ioBuffer);

        while (-1 != readLen){
            out.write(ioBuffer, 0, readLen);
            readLen = hdfsInStream.read(ioBuffer);
        }
        out.close();
        hdfsInStream.close();
        fs.close();
    }


    /**
     * 以append方式将内容添加到HDFS上文件的末尾;注意：文件更新，需要在hdfs-site.xml中添
     * <property>
     *     <name>dfs.append.support</name>
     *     <value>true</value>
     * </property>
     */
    private static void appendToHdfs() throws IOException {
        String dst = "test.txt";
        FileSystem fs = FileSystem.get(URI.create(dst), conf);
        FSDataOutputStream out = fs.append(new Path(dst));

        int readLen = "zhangzk add by hdfs java api".getBytes().length;

        while (-1 != readLen){
            out.write("zhangzk add by hdfs java api".getBytes(), 0, readLen);
        }
        out.close();
        fs.close();
    }

    /**
     * 遍历hdfs上的文件和目录
     */
    private static void getDirectoryFromHdfs() throws IOException {
        String dst = "/user";
        FileSystem fs = FileSystem.get(URI.create(dst), conf);
        FileStatus fileList[] = fs.listStatus(new Path(dst));
        int size = fileList.length;
        for (int i = 0; i < size; i++){
            System.out.println("Name" + fileList[i].getPath().getName() + "/t/tsize:" + fileList[i].getLen());
        }
        fs.close();
    }


    /**
     * 删除文件
     */
    private static void deleteFromHdfs() throws IOException {
        String dst = "/user/hdfs";
        FileSystem fs = FileSystem.get(URI.create(dst), conf);
        fs.deleteOnExit(new Path(dst));
        fs.close();
    }

    /**
     * 上传文件到hdfs
     */
    private static void uploadToHdfs() throws IOException {
        String loadSrc = "f:test.txt";     //本地文件
        String dst = "/user/hdfs/test.txt";    //hdfs 上的文件夹
        InputStream in = new BufferedInputStream(new FileInputStream(loadSrc));
        FileSystem fs = FileSystem.get(URI.create(dst), conf);
        OutputStream out = fs.create(new Path(dst), new Progressable() {
            @Override
            public void progress() {
                System.out.println(".");
            }
        });
        IOUtils.copyBytes(in, out, 4096, true);
    }

}
