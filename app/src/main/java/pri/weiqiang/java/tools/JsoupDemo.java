package pri.weiqiang.java.tools;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsoupDemo {

    public static int mount = 0;

    public static void main(String[] args) {
        System.out.println("JsoupDemo start");
        File file = new File("E:/xinwei-del-same.txt");
        txt2String(file);

    }

    private static void getContacts(String name) {
        Connection.Response res;
        try {
            res = Jsoup.connect("http://oa.xinwei.com.cn/names.nsf?Login").data("Username", "weiqiang", "Password", "weiqiang").method(Method.POST).execute();
//			Document doc = res.parse();
//			System.out.println(doc.data());
            // 这儿的SESSIONID需要根据要登录的目标网站设置的session Cookie名字而定
//			String sessionId = res.cookie("SESSIONID");
//			System.out.println(sessionId);
            Document objectDoc = Jsoup
                    .connect("http://oa.xinwei.com.cn/Produce/WeboaConfig.nsf/telSearchForm?openform&svrName=&queryStr=" + name + "&dbFile=Produce/DigiFlowOrgPsnMng.nsf&showField=UserDeptPhone")
                    //.cookie("SESSIONID", sessionId)
                    .cookies(res.cookies()).get();
            Elements content = objectDoc.getElementsByTag("textarea");
            String str = content.toString();
            str = str.replace("&lt;/td&gt;&lt;td align=left&gt", "");
            str = str.replace(
                    "textarea name=\"ResultList\" style=\"display:none\" rows=\"7\" cols=\"50\">&lt;table width='100%' border=1 style='border-collapse:collapse' class='table1'&gt;&lt;tr bgcolor='#d6dff7'&gt;&lt;td align='center' width='10%'&gt;&lt;b&gt;ITCode&lt;/b&gt;&lt;/td&gt;&lt;td align='center' width='10%'&gt;&lt;b&gt;用户名&lt;/b&gt;&lt;/td&gt;&lt;td align='center' width='10%'&gt;&lt;b&gt;员工编号&lt;/b&gt;&lt;/td&gt;&lt;td align='center' width='10%'&gt;&lt;b&gt;办公电话&lt;/b&gt;&lt;/td&gt;&lt;td align='center' width='15%'&gt;&lt;b&gt;移动电话&lt;/b&gt;&lt;/td&gt;&lt;td align='center' width='25%'&gt;&lt;b&gt;部门OU&lt;/b&gt;&lt;/td&gt;&lt;td align='center' width='20%'&gt;&lt;b&gt;部门领导&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;&lt;td align=left&gt;&lt;",
                    "");
            str = str.replace(";/font&gt;&lt;/a&gt;&lt;/td&gt;&lt;td align=left &gt;", "");
            str = str.replace("a href=javascript:openPsn('", "");
            str = str.replace("');&gt;&lt;font color='blue'&gt", "");
            str = str.replace("&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;</textarea>", "");
            str = str.replace("&lt", ";");
            str = str.replace(";/td&gt;;;/tr&gt;;;tr&gt;;;td align=left&gt;;;", "\n");
            saveAsFileWriter(str + "\n");
            System.out.println("str:" + str);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    /**
     * 读取txt文件的内容
     *
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    private static void txt2String(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            int i = 0;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                System.out.println(i + ":" + s);
                getContacts(s);
                i++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void saveAsFileWriter(String content) {
        File savefile = new File("E:/xinwei_contacts.txt");
        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(savefile, true);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}