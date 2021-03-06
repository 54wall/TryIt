package pri.weiqiang.tryit.scoket.chat.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://github.com/GuoJinyu/AndroidUtils
 * 使用枚举来实现的单例模式，不要再使方法静态，会报警告
 * accessed via instance reference shows references to static methods and
 * fields via class instance rather than a class itself.
 * 相当于没有使用枚举，使用的不是枚举类，
 */
public enum IpUtils {
    //定义一个枚举的元素，它就是Singleton的一个实例
    instance;

    /*获取设备IPv4地址对应的InetAddress对象*/
    public static InetAddress getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface
                    .getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*获取设备IPv4地址对应的字符串*/
    public static String getIpAddressString() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface
                    .getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*获取设备WiFi网络IPv4地址对应的InetAddress对象*/
    public static InetAddress getWiFiIpAddress() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface
                    .getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                if (netI.getDisplayName().equals("wlan0") || netI.getDisplayName().equals("eth0")) {
                    for (Enumeration<InetAddress> enumIpAddr = netI
                            .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                            return inetAddress;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*判断字符串是否为一个合法的IPv4地址*/
    public static boolean isIPv4Address(String address) {
        String regex = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})"
                + "\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})"
                + "\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})"
                + "\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(address);
        return m.matches();
    }

    /*获取设备WiFi网络IPv4地址对应的字符串*/
    public String getWiFiIpAddressString() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface
                    .getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                if (netI.getDisplayName().equals("wlan0") || netI.getDisplayName().equals("eth0")) {
                    for (Enumeration<InetAddress> enumIpAddr = netI
                            .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }
}
