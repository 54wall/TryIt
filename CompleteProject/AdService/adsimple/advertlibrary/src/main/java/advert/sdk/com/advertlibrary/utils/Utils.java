package advert.sdk.com.advertlibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;


public class Utils {
    private static final String TAG = Utils.class.getSimpleName();
    private static final int widthPixels = 0;
    private static final int heightPixels = 0;


    public static boolean isNetworkAvailable() {

        return true;
    }

    /**
     * 获取当前手机IP地址
     * 用于网络adb，社采联调等
     *
     * @param context
     * @return
     */
    public static String getIPAddress(Context context) {
        try {
            NetworkInfo info = ((ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                    try {
                        //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                            NetworkInterface intf = en.nextElement();
                            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                                InetAddress inetAddress = enumIpAddr.nextElement();
                                if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                    return "运营商网络：" + inetAddress.getHostAddress();
                                }
                            }
                        }
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }

                } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                    return "WIFI:" + ipAddress;
                } else if (info.getType() == ConnectivityManager.TYPE_ETHERNET) {//以太网
                    String ipAddress = getLocalIpAddress();
                    return "以太网:" + ipAddress;
                } else {
                    return "未识别网络 Type:" + info.getType();
                }
            } else {
                //当前无网络连接,请在设置中打开网络
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得以太网ip
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            String ipAddress = "";
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ipAddress = inetAddress.getHostAddress();
                        if (!ipAddress.contains("::")) {
                            return inetAddress.getHostAddress();
                        }
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception ex) {//SocketException
            ex.printStackTrace();
            return "GetHostIP Fail";//,Please clear the shareReference

        }
        return "GetHostIP Fail";//,Please clear the shareReference
    }


    public static InetAddress getLocalIp() {
        InetAddress inetAddress = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    inetAddress = enumIpAddr.nextElement();
                }
            }
        } catch (Exception ex) {//SocketException
            ex.printStackTrace();
            return inetAddress;//,Please clear the shareReference
        }
        return inetAddress;//,Please clear the shareReference
    }


    public static String getAllNetInterface(Context context) {
        StringBuffer buf = new StringBuffer();
        String ipInfo = null;
        try {
            Network[] info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getAllNetworks();
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();

                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    Log.d("getAllNetInterface", "getAllNetInterface,available interface:" + ni.getName() + ",address:" + ip);
                    // 过滤掉127段的ip地址
                    if (!"127.0.0.1".equals(ip)) {

                        Log.i(TAG, "getAllNetInterface: " + ni.getName() + " " + ia.getHostAddress());
                        buf.append(ni.getName()).append(" ").append(ip)
                                .append(" MAC ").append(getMacAddress(ni.getInetAddresses().nextElement())).append("\n");
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        ipInfo = buf.toString();
        return ipInfo;
    }


    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    public static String getMacAddress(InetAddress inetAddress) {
        String strMacAddr = null;
        try {
            // 获得IpD地址
            InetAddress ip = inetAddress;
            byte[] b = NetworkInterface.getByInetAddress(ip)
                    .getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {
        }
        return strMacAddr;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
//        String[] weekOfDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    public static String getDeviceTemp() {
        String str = "-100℃";
        try {
            String deviceTempStr = readToString("/sys/class/thermal/thermal_zone0/temp");
            if (deviceTempStr != null) {
                float temp = Float.parseFloat(deviceTempStr);
                if (temp > 1000) {
                    str = temp / 1000 + "℃";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return str;
        }
    }

    public static String readToString(String fileName) {
        File file = new File(fileName);
        Long filelength = file.length();//4096
        byte[] filecontent = new byte[6];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {//IOException
            e.printStackTrace();
        }
        try {
            String strRtn = new String(filecontent);
            return strRtn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定包名的版本号
     *
     * @param context     本应用程序上下文
     * @param packageName 你想知道版本信息的应用程序的包名
     * @return
     */
    public static int getVersionCode(Context context, String packageName) {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(packageName, 0);
            int version = packInfo.versionCode;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取本应用的版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int version = packInfo.versionCode;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * get file md5 注意返回的值是大写
     *
     * @param file
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String getFileMD5(File file) throws Exception {
        FileInputStream in = new FileInputStream(file);
        byte[] md5s = getHash(in, "MD5");
        in.close();
        return Convert.toHexString(md5s);
    }

    public static class Convert {

        private final static char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        /**
         * Reverse bytes to a new memery
         *
         * @param in
         * @return
         */
        public static byte[] ReverseByteToCopy(byte[] in) {
            byte[] reData = new byte[in.length];
            for (int i = 0; i < in.length / 2; i++) {
                reData[i] = in[in.length - i - 1];
                reData[in.length - i - 1] = in[i];
            }
            return reData;
        }

        public static void ReverseByte(byte[] in) {
            byte tmp;
            for (int i = 0; i < in.length / 2; i++) {
                tmp = in[i];
                in[i] = in[in.length - i - 1];
                in[in.length - i - 1] = tmp;
            }
        }

        public static String toHexString(byte[] byteArray) {
            if (byteArray == null) {
                return "";
            }
            return toHexString(byteArray, 0, byteArray.length);
        }

        public static String toHexString(byte[] d, int s, int n) {
            if (d == null) {
                return "";
            }
            final char[] ret = new char[n * 2];
            final int e = s + n;

            int x = 0;
            for (int i = s; i < e; ++i) {
                final byte v = d[i];
                ret[x++] = HEX[0x0F & (v >> 4)];
                ret[x++] = HEX[0x0F & v];
            }
            return new String(ret);
        }

        public static String toHexStringR(byte[] d, int s, int n) {
            final char[] ret = new char[n * 2];

            int x = 0;
            for (int i = s + n - 1; i >= s; --i) {
                final byte v = d[i];
                ret[x++] = HEX[0x0F & (v >> 4)];
                ret[x++] = HEX[0x0F & v];
            }
            return new String(ret);
        }

        public static byte[] toByteArray(String hexString) {
            if (null == hexString) {
                throw new IllegalArgumentException("this hexString must not be empty");
            }

            hexString = hexString.toLowerCase().replace(" ", "");
            final byte[] byteArray = new byte[hexString.length() / 2];
            int k = 0;
            for (int i = 0; i < byteArray.length; i++) {
                byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
                byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
                byteArray[i] = (byte) (high << 4 | low);
                k += 2;
            }
            return byteArray;
        }

        public static int byte2Int(byte b) {
            return b & 0xff;
        }
    }

    /**
     * @param inputStream
     * @param hashAlg     哈希算法 SHA1，MD5
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] getHash(InputStream inputStream, String hashAlg) throws IOException, NoSuchAlgorithmException {
        MessageDigest mdTemp = MessageDigest.getInstance(hashAlg);
        byte[] buff = new byte[4096];
        int len = 0;
        while ((len = inputStream.read(buff)) > 0) {
            mdTemp.update(buff, 0, len);
        }
        byte[] md = mdTemp.digest();
        return md;
    }

    public static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile() || file.list() == null) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * 设置是否启用自动同步网络时间
     *
     * @param context
     * @param isAutoDateTime
     */
    public static void setAutoDateTime(Context context, boolean isAutoDateTime) {
        try {
            int value;
            if (isAutoDateTime) {
                value = 1;
            } else {
                value = 0;
            }
            android.provider.Settings.Global.putInt(context.getContentResolver(), android.provider.Settings.Global.AUTO_TIME, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置系统时间
     *
     * @param timestamp
     */
    public static void setSystemTime(long timestamp) {
        try {
            SystemClock.setCurrentTimeMillis(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前文件夹路径下文件数量
     *
     * @param path 文件夹路径
     */
    public static int getFolderFileCount(String path) {
        try {
            File file = new File(path);
            int count = 0;
            if (file != null && file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (f.isDirectory()) {
                        count = count + f.list().length;
                    } else {
                        count++;
                    }
                }
                return count;
            }

        } catch (Exception e) {
            Log.e(TAG, "e:" + e.toString());
        }
        return 0;
    }

}
