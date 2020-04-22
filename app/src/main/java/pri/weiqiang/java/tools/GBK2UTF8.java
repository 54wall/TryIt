package pri.weiqiang.java.tools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class GBK2UTF8 {

    public static void main(String[] args) throws IOException {
        //GBK编码格式源码路径
        String srcDirPath = "D:\\IDE\\eclipse-workspace\\SearchStrInPath\\gbk";
        //转为UTF-8编码格式源码路径
        String utf8DirPath = "D:\\IDE\\eclipse-workspace\\SearchStrInPath\\utf8";
        //获取所有java文件
        Collection<File> javaGbkFileCol = FileUtils.listFiles(new File(srcDirPath), new String[]{"java", "xml", "txt"}, true);

        for (File javaGbkFile : javaGbkFileCol) {
            System.out.println("Running…… javaGbkFile:" + javaGbkFile.getName());
            // UTF8格式文件路径
            String utf8FilePath = utf8DirPath + javaGbkFile.getAbsolutePath().substring(srcDirPath.length());
            // 使用GBK读取数据，然后用UTF-8写入数据
            FileUtils.writeLines(new File(utf8FilePath), "UTF-8", FileUtils.readLines(javaGbkFile, "GBK"));
        }
        System.out.println("Finished……");
    }
}