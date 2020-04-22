package pri.weiqiang.tryit.adb;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class AdbActivity extends BaseActivity {
    private static String TAG = AdbActivity.class.getSimpleName();

    // 判断是否具有ROOT权限
    public static boolean isRoot() {
        boolean res = false;
        try {
            if ((!new File("/system/bin/su").exists()) &&
                    (!new File("/system/xbin/su").exists())) {
                res = false;
            } else {
                res = true;
            }
        } catch (Exception e) {

        }
        return res;
    }

    //执行命令并且输出结果 https://blog.csdn.net/qq_27512671/article/details/78099015
    public static String execRootCmd(String cmd) {
        String result = "";
        DataOutputStream dos = null;
        DataInputStream dis = null;

        try {
            // 经过Root处理的android系统即有su命令
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dis = new DataInputStream(p.getInputStream());

            Log.i(TAG, cmd);
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            String line = null;
            BufferedReader d
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = d.readLine()) != null) {
                Log.e("130 result", line);
                result += line;
            }
            p.waitFor();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    CommandUtil.execute("adb connect " + getLocalIpAddress() + ":55555");
                }
            });
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.e(TAG, "152 result:" + result);
        return result;
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, ex.toString());
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adb);
        CheckBox cb = findViewById(R.id.checkBox);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.e(TAG, "Checkbox:" + isChecked);
                    setDebug(55555);
                    Log.e(TAG, "setDebug(55555)");
                } else {
                    Log.e(TAG, "Checkbox:" + isChecked);
                    setDebug(-1);
                    Log.e(TAG, "setDebug(-1)");
                }
            }
        });
        Button mBtnConnectMyself = findViewById(R.id.btn_connect_myself);
        mBtnConnectMyself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRoot();
                Log.e(TAG, "getLocalIpAddress:" + getLocalIpAddress());
                String str = execRootCmd("adb connect " + getLocalIpAddress() + ":55555");
                Log.e(TAG, "str:" + str);
            }
        });
    }

    private void setDebug(int port) {
        getRoot();
        String str1 = execRootCmd("setprop service.adb.tcp.port" + port);
        String str2 = execRootCmd("stop adbd");
        String str3 = execRootCmd("start adbd");
        Log.e(TAG, "str1:" + str1);
        Log.e(TAG, "str2:" + str2);
        Log.e(TAG, "str2:" + str3);
    }

    //获取ROOT权限 https://blog.csdn.net/qq_34115898/article/details/53994078
    public void getRoot() {
        if (isRoot()) {
            Log.e(TAG, "已经具有ROOT权限!");
        } else {
            try {
                Log.e(TAG, "正在获取ROOT权限!");
                Runtime.getRuntime().exec("su");
            } catch (Exception e) {
                Log.e(TAG, "获取ROOT权限时出错!");
            }
        }

    }
}
