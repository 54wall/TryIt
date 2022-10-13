package com.wildma.mqttandroidclient.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class SystemInfoUtil {

    public static final String TAG = SystemInfoUtil.class.getSimpleName();

    /**
     * 获取设备IP和MAC地址
     *
     * @return IP地址:String[0],MAC地址:String[1]
     */
    public static String[] getIpMacAddress(Context context) {
        String[] etherIpMacAddress = getEtherIpMacAddress(context);
        if (etherIpMacAddress == null) {
            return getWlanIpMacAddress(context);
        }
        return etherIpMacAddress;
    }

    public static String[] getWlanIpMacAddress(Context context) {
        try {
            String allNetInterface = getAllNetInterface(context);
            //数据示例: wlan0 192.168.0.1 MAC xx:xx:xx:xx:xx:xx + \n + 数据 ...
            if (!TextUtils.isEmpty(allNetInterface)
                    && (allNetInterface.contains("wlan")||allNetInterface.contains("WLAN"))
            ) {
                String[] split = allNetInterface.split("\n");
                for (String info : split) {
                    if (info.contains("wlan") ) {
                        String[] split1 = info.split(" ");
                        String[] result = new String[2];
                        result[0] = split1[1];
                        result[1] = split1[3];
                        Log.e(TAG,"result[0]:"+result[0] +"result[1] "+result[1] );
                        return result;
                    }else if (info.contains("WLAN")) {
                        String[] split1 = info.split(" ");
                        String[] result = new String[2];
                        result[0] = split1[1];
                        result[1] = split1[3];
                        Log.e(TAG,"result[0]:"+result[0] +"result[1] "+result[1] );
                        return result;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    public static String[] getEtherIpMacAddress(Context context) {
        try {
            String allNetInterface = getAllNetInterface(context);
            //数据示例: eth0 192.168.0.1 MAC xx:xx:xx:xx:xx:xx + \n + 数据 ...
            if (!TextUtils.isEmpty(allNetInterface) && allNetInterface.contains("eth")) {
                String[] split = allNetInterface.split("\n");
                for (String info : split) {
                    if (info.contains("eth")) {
                        String[] split1 = info.split(" ");
                        String[] result = new String[2];
                        result[0] = split1[1];
                        result[1] = split1[3];
                        return result;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    public static String getAllNetInterface(Context context) {
        StringBuffer buf = new StringBuffer();
        String ipInfo;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    // 过滤掉127段的ip地址
                    if (!"127.0.0.1".equals(ip)) {
                        buf.append(ni.getName().toUpperCase()).append(":").append(ip)
                                .append("\nMAC:").append(getMacAddress(ni.getInetAddresses().nextElement())).append("\n");
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


}
