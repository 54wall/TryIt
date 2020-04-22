package pri.weiqiang.tryit.adb;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//必须有/n 不然无法执行结果
public class CallCmd {
    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec("ping 127.0.0.1");
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}