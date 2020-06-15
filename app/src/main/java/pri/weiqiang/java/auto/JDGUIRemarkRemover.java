package pri.weiqiang.java.auto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * 去掉java反编译（JD-GUI）生成的源文件中注释
 * https://blog.csdn.net/weixin_30437481/article/details/97706120
 */
public class JDGUIRemarkRemover {
    private int counter = 0;

    public JDGUIRemarkRemover() {
    };

    public static void main(String[] args) {
        String filePath = "D:\\1519classes.jar.src\\tendyron";  //放待处理的文件路径或者文件夹路径

        JDGUIRemarkRemover jdGUIRemarkRemover = new JDGUIRemarkRemover();
        if (jdGUIRemarkRemover.process(filePath)) {
            System.out.println("处理java文件 " + jdGUIRemarkRemover.getCounter() + "个!");
        } else {
            System.err.println("处理失败！");
        }
    }

    private boolean process(String s) {
        File f = new File(s);

        if (!f.exists()) {
            return false;
        }

        if (f.isFile()) {
            try {
                erase(f);
            } catch (RuntimeException ex) {
                System.err.println(ex.getMessage());
            }
        } else if (f.isDirectory()) {
            String[] fs = f.list();
            for (String sr : fs) {
                sr = f.getAbsolutePath() + File.separator + sr;
                process(sr);
            }
        } else {
            System.out.println("参数错误!");
        }

        return true;
    }

    private void erase(File f) throws RuntimeException {
        List<String> tmp = new ArrayList<String>();

        String name = f.getName();
        String path = f.getParent() + File.separator;
        BufferedReader br = null;

        if (!name.endsWith(".java")) {
            throw new RuntimeException("不是JAVA文件 : " + name);
        }

        System.out.println("处理文件[" + (path + name) + "]");

        try {
            br = new BufferedReader(new FileReader(f));
            String s = null;
            while ((s = br.readLine()) != null) {
                tmp.add(s);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        f.delete();

        File copy = new File(path + name);
        BufferedWriter bw = null;
        try {
            copy.createNewFile();

            bw = new BufferedWriter(new FileWriter(copy, true));

            for (Iterator<String> itr = tmp.iterator(); itr.hasNext();) {
                String s = itr.next();

                if (s.contains("Location:") || s.contains("Qualified Name:")
                        || s.contains("JD-Core Version:")) {
                    counter++;
                    return;
                }

                int begin = s.indexOf("/*");
                int end = s.indexOf("*/");

                if (end != -1 && begin != -1) {
                    s = s.substring(end - begin + 3, s.length());
                }

                bw.write(s + "\n");
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        counter++;
    }

    private int getCounter() {
        return counter;
    }
}