package pri.weiqiang.tryit.scoket.chat.socket;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import pri.weiqiang.tryit.scoket.chat.bean.MsgBean;
import pri.weiqiang.tryit.scoket.chat.config.Constants;
import pri.weiqiang.tryit.scoket.chat.utils.Base64Utils;

import static pri.weiqiang.tryit.scoket.chat.config.Constants.DIR_NAME;

public class SocketHelper {
    public static Handler showMsgHandler;
    private boolean mCreateFile = true;
    private String TAG = SocketHelper.class.getSimpleName();
    private Gson gson = new Gson();
    private InputStream in;
    private FileOutputStream fos = null;

    /**
     * 发送文件
     */
    public void sendFile(String filePath, Socket socket) {
        if (socket == null) {
            Message message = new Message();
            message.what = Constants.SOCKET_NULL;
            showMsgHandler.sendMessage(message);
            Log.e(TAG, "socket==null");
            return;
        }
        FileInputStream fis = null;
        File file = new File(filePath);
        Gson mGson = new Gson();
        PrintWriter mPrintWriter = null;
        try {
            mPrintWriter = new PrintWriter(socket.getOutputStream());
            fis = new FileInputStream(file);

            MsgBean trans = new MsgBean.Builder()
                    .transmissionType(Constants.TRANSFER_FILE)
                    .fileName(file.getName())
                    .fileLength(file.length())
                    .transLength(0)
                    .build();
            //bytes原来为1024 导致传输效率极差
            byte[] bytes = new byte[1024 * 1024];
            int length = 0;
            while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
                trans.transLength += length;
                trans.content = Base64Utils.encode(bytes);
                mPrintWriter.write(mGson.toJson(trans) + "\r\n");
                mPrintWriter.flush();
                Message message = new Message();
                message.what = Constants.PROGRESS;
                message.obj = 100 * trans.transLength / trans.fileLength;
                showMsgHandler.sendMessage(message);
                Log.e(TAG, "上传百分比:" + message.obj);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            mPrintWriter.close();
        }
    }

    /**
     * 发送文本消息
     */
    public void sendStr(String content, Socket socket) {
        if (TextUtils.isEmpty(content)) {
            Log.e(TAG, "content isEmpty");
            return;
        }
        try {
            if (socket == null) {
                Log.e(TAG, "ClientScoket==null");
                Message message = new Message();
                message.what = Constants.SOCKET_NULL;
                showMsgHandler.sendMessage(message);
                return;
            }
            Log.e(TAG, "sendStr 155");
            byte[] str = content.getBytes("utf-8");//将内容转utf-8
            String aaa = new String(str);
            MsgBean messageBean = new MsgBean.Builder().content(aaa).transmissionType(Constants.TRANSFER_STR).build();
            /*
             * 因为服务器那边的readLine()为阻塞读取
             * 如果它读取不到换行符或者输出流结束就会一直阻塞在那里
             * 所以在json消息最后加上换行符，用于告诉服务器，消息已经发送完毕了
             * */
            OutputStream output = socket.getOutputStream();
            String messageJson = gson.toJson(messageBean) + "\n";// \r or \n均可
            output.write(messageJson.getBytes("utf-8"));// 换行打印
            output.flush(); // 刷新输出流，使Server马上收到该字符串
            Log.e(TAG, "sendStr 167");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "错误：" + e.toString());
        }
    }

    public void getMsgFromScoket(Socket socket) {

        try {
            //得到输入流
            in = socket.getInputStream();
            String line;
            BufferedReader bff = new BufferedReader(new InputStreamReader(in));
            while (true) {
                Thread.sleep(500);
                line = bff.readLine();
                // 获取客户端的信息
                Log.e(TAG, "getMsgFromScoket 循环接收:" + line);
                MsgBean msgBean = gson.fromJson(line, MsgBean.class);
                if (msgBean.getTransmissionType() == Constants.TRANSFER_STR) {
                    msgBean.setItemType(Constants.CHAT_FROM);
                    showMsg(msgBean);
                    //不要增加break;增加break后，只会接收一个数据就不再接收了while循环就是要保证只有当Scoket失效才有用
                } else if (msgBean.getTransmissionType() == Constants.TRANSFER_FILE) {
                    //如果使用else,则还是阻塞line=bff.readLine()不会再执行
                    Log.e(TAG, "getMsgFromScoket 进入传输文件");
                    long fileLength = msgBean.fileLength;
                    long transLength = msgBean.transLength;
                    if (mCreateFile) {
                        mCreateFile = false;
                        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + DIR_NAME;
                        File file = new File(path);
                        if (!file.exists()) {
                            file.mkdirs();//file.mkdir();仅构建一层
                        }
                        fos = new FileOutputStream(new File(path + File.separator + msgBean.fileName));
                    }
                    byte[] b = Base64Utils.decode(msgBean.content.getBytes());
                    fos.write(b, 0, b.length);
                    Log.e(TAG, "getMsgFromScoket 接收文件进度" + 100 * transLength / fileLength + "%...");
                    if (transLength == fileLength) {
                        mCreateFile = true;
                        fos.flush();
                        fos.close();
                        msgBean.setItemType(Constants.CHAT_FROM);
                        msgBean.content = msgBean.fileName;
                        showMsg(msgBean);
                    }
                }
                if (socket == null) {
                    Log.e(TAG, "getMsgFromScoket socket == null");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            socket.isClosed();
        }
    }

    /**
     * read();
     * socket服务端得到返回数据并发送到主界面
     */
    private void showMsg(MsgBean msgBean) {
        Message msg = new Message();
        msg.obj = msgBean;
        msg.what = msgBean.transmissionType;
        showMsgHandler.sendMessage(msg);
    }
}
