package pri.weiqiang.tryit.scoket.chat.socket;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/*
MainActivity代码：
    Server server=new Server ( port );
    server.startListen ();
* */
public class Server {

    public static HashMap<String, Socket> socketList = new HashMap<>();
    public SocketHelper mSocketHelper = new SocketHelper();
    private String curSocketName;
    private String TAG = Server.class.getSimpleName();
    private ServerSocket server;

    /**
     * 绑定端口号,初始化服务端
     *
     * @param port 端口号
     */
    public Server(int port) {
        try {
            server = new ServerSocket(port);
            Log.e(TAG, "new ServerSocket:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * socket监听数据
     */
    public void startListen() {
        Log.e(TAG, "startListen");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "new Thread");
                while (true) {
                    try {
                        Log.e(TAG, "server.accept()前");
                        //获取多个Socket响应
                        Socket socket = server.accept();
                        Log.e(TAG, "82 socketList.size():" + Server.socketList.size() + ",getInetAddress():" + socket.getInetAddress());
                        Log.e(TAG, "远程IP:" + socket.getInetAddress() + ",本地端口:" + socket.getLocalPort() + ",远程端口:" + socket.getPort());
                        Socket socket_existed = Server.socketList.get(socket.getInetAddress().toString());
                        if (socket_existed != null) {
                            Log.e(TAG, "socket_existed 远程IP:" + socket_existed.getInetAddress() + ",本地端口:" + socket_existed.getLocalPort()
                                    + ",远程端口:" + socket_existed.getPort());
                        }
                        //这里不用加"/"
                        //socket.getInetAddress()必须toString，否则HashMap.get返回值一直是null
                        if (Server.socketList.get(socket.getInetAddress().toString()) == null) {
                            socketList.put(socket.getInetAddress().toString(), socket);
                            curSocketName = socket.getInetAddress().toString();
                            Log.e(TAG, "新建 Socket!!!! 远程IP:" + socket.getInetAddress() + ",本地端口:" + socket.getLocalPort() + ",远程端口:" + socket.getPort());
                            new Thread(new getMsgTask(socket)).start();
                        } else {
                            //socket与socket_existed虽然端口一样，但不是一个对象
                            getMsgFromOldScoket(socket_existed);
                            socket.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (server == null || server.isClosed()) {
                        Log.e(TAG, "server close");
                        break;
                    }
                }
            }
        }).start();
    }

    private void getMsgFromOldScoket(Socket socket) {
        Log.e(TAG, "getMsgFromOldScoket start");
        mSocketHelper.getMsgFromScoket(socket);
        Log.e(TAG, "getMsgFromOldScoket end");
    }

    /**
     * 处理Socket请求的线程类
     */
    private class getMsgTask implements Runnable {

        private Socket socket;

        getMsgTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            Log.e(TAG, "进入Task,即新建一个Scoket");
            mSocketHelper.getMsgFromScoket(socket);
        }
    }

}
