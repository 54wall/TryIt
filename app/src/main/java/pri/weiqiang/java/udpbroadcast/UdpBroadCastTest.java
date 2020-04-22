package pri.weiqiang.java.udpbroadcast;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UdpBroadCastTest {

    private static final int SEND_BROADCAST_PORT = 9000;
    private static final int RECV_BROADCAST_PORT = 8000;
    private static final String BROADCAST_ADDR = "255.255.255.255";

    public static void main(String[] args) {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(new UdpRunnable());
    }
    //java 及 android 本地接收广播
    private static class UdpRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    byte[] message = new byte[128];
                    final DatagramSocket datagramSocket = new DatagramSocket(9001);// RECV_BROADCAST_PORT
                    final DatagramPacket datagramPacket = new DatagramPacket(message, message.length);
                    try {
                        System.out.println("开始监听");
                        datagramSocket.receive(datagramPacket);
                        final String ip = datagramPacket.getAddress()
                                .getHostAddress();
                        String s = new String(datagramPacket.getData());
                        System.out.println("receive：" + s + ",from: " + ip);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (datagramSocket != null) {
                            datagramSocket.close();
                        }
                    }
                } catch (SocketException e) {
                    System.out.println("报错" + e.toString());
                }
            }
        }
    }

    //android 发送广播
    private void sendMessage() {

        String message = "This is LJT";
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
            datagramSocket.setBroadcast(true);
            InetAddress address = InetAddress.getByName(BROADCAST_ADDR);
            DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.length(), address, SEND_BROADCAST_PORT);
            datagramSocket.send(datagramPacket);
            System.out.println("send: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        }
    }

}

