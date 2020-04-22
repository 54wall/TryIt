package pri.weiqiang.tryit.scoket.chat.socket;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

/*
MainActivity代码：
        Client  client=new Client ();
        client.clintValue ( ContactFragment.this,"172.18.74.64" ,1935);
        client.openClientThread ();
*/
public class Client {

    public static HashMap<String, Socket> socketList = new HashMap<>();
    public SocketHelper mSocketHelper = new SocketHelper();
    private String TAG = Client.class.getSimpleName();
    private Context context;
    //IP
    private int port;
    //端口
    private String site;
    private Thread thread;

    /**
     * 开启线程建立连接开启客户端
     */
    public void openClientThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "site:" + site + ",socketList.get:" + Client.socketList.get("/" + site));
                if (Client.socketList.get("/" + site) == null) {
                    try {
                        Socket client = new Socket(site, port);
                        Log.e(TAG, "81 socketList.put 被执行");
                        //put操作一定要放在getMsg之前，getMsg()内部将会一直阻塞，放在getMsg之后将永远不会被执行
                        Client.socketList.put("/" + site, client);
                        Log.e(TAG, "85 client:" + client);
//                    client.setSoTimeout ( 5000 );//设置超时时间
                        if (client != null) {
                            mSocketHelper.getMsgFromScoket(client);
                        } else {
                            Toast.makeText(context, "网络连接失败", Toast.LENGTH_LONG).show();
                        }
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "Client已经建立，不再重新建立，这里什么也没有做,我在ChatFragment中已经制定了要传入Scoket才能发送文件");
                }
            }
        });
        thread.start();
    }

    /**
     * 调用时向类里传值
     */
    public void clintValue(Context context, String site, int port) {
        this.context = context;
        this.site = site;
        this.port = port;
    }
}