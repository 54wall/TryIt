package pri.weiqiang.tryit.scoket.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.scoket.chat.adapter.MsgAdapter;
import pri.weiqiang.tryit.scoket.chat.bean.MsgBean;
import pri.weiqiang.tryit.scoket.chat.config.Constants;
import pri.weiqiang.tryit.scoket.chat.socket.Client;
import pri.weiqiang.tryit.scoket.chat.socket.Server;
import pri.weiqiang.tryit.scoket.chat.socket.SocketHelper;
import pri.weiqiang.tryit.scoket.chat.utils.IpUtils;

import static pri.weiqiang.tryit.scoket.chat.ui.ScoketChatActivity.REQUEST_CODE_IMAGE;

public class ChatFragment extends Fragment {

    private final static String TAG = ChatFragment.class.getSimpleName();
    public static String host = "";
    private static int i_et_msg;
    public boolean isClient = true;
    private EditText mEtMsg;
    private Client client;
    private List<MsgBean> msgBeanList = new ArrayList<>();
    private RecyclerView mRvMsg;
    private MsgAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scoket_chat, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRvMsg = getView().findViewById(R.id.rv_msg);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRvMsg.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgBeanList);
        mRvMsg.setAdapter(adapter);

        mEtMsg = getView().findViewById(R.id.et_msg);
        mEtMsg.setText(IpUtils.instance.getWiFiIpAddressString());
        Button mBtnMsg = getView().findViewById(R.id.btn_msg);
        Button mBtnFile = getView().findViewById(R.id.btn_file);

        client = new Client();
        mBtnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //不添加getActivity(),则调用的是Fragment中的方法，则REQUEST_CODE_IMAGE变为65536
                getActivity().startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });
        mBtnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送信息，应该先判断是Server还是Client,然后再判断二者各自的Socket是否已经建立
                if (Server.socketList.get("/" + host) == null) {
                    Log.e(TAG, "作为Clinet发送信息");
                    client.clintValue(getActivity(), host, Constants.PORT);
                    client.openClientThread();
                    client.mSocketHelper.sendStr(mEtMsg.getText().toString(), Client.socketList.get("/" + host));
                    //获取EditText中的内容
                    String content = mEtMsg.getText().toString();
                    //内容不为空则创建一个新的Msg对象，并把它添加到msgList列表中
                    if (!"".equals(content)) {
                        MsgBean msgBean = new MsgBean.Builder().content(content).itemType(Constants.CHAT_SEND).build();
                        msgBeanList.add(msgBean);
                        //调用适配器的notifyItemInserted()用于通知列表有新的数据插入，这样新增的一条消息才能在RecyclerView中显示
                        adapter.notifyItemInserted(msgBeanList.size() - 1);
                        //调用scrollToPosition()方法将显示的数据定位到最后一行，以保证可以看到最后发出的一条消息
                        mRvMsg.scrollToPosition(msgBeanList.size() - 1);
                        mEtMsg.setText(IpUtils.instance.getWiFiIpAddressString() + ":" + (++i_et_msg));
                    }
                } else {

                    Socket server_one = Server.socketList.get("/" + host);
                    Log.e(TAG, "作为Server发送信息:" + server_one.getInetAddress());
                    /*socket发送数据*/
                    ((ScoketChatActivity) getActivity()).getServer().mSocketHelper.sendStr(mEtMsg.getText().toString(), server_one);
                    String content = mEtMsg.getText().toString();
                    //内容不为空则创建一个新的Msg对象，并把它添加到msgList列表中
                    if (!"".equals(content)) {
                        MsgBean msgBean = new MsgBean.Builder().content(content).itemType(Constants.CHAT_SEND).build();
                        msgBeanList.add(msgBean);
                        //调用适配器的notifyItemInserted()用于通知列表有新的数据插入，这样新增的一条消息才能在RecyclerView中显示
                        adapter.notifyItemInserted(msgBeanList.size() - 1);
                        //调用scrollToPosition()方法将显示的数据定位到最后一行，以保证可以看到最后发出的一条消息
                        mRvMsg.scrollToPosition(msgBeanList.size() - 1);
                        mEtMsg.setText(IpUtils.instance.getWiFiIpAddressString() + ":" + (++i_et_msg));
                    }
                }

            }
        });

        /*socket收到消息线程*/
        SocketHelper.showMsgHandler = new MsgHandler(ChatFragment.this);
    }

    public void sendFile(String path) {
        Log.e(TAG, "sendFile:" + path);
        //发送信息，应该先判断是Server还是Client,然后再判断二者各自的Socket是否已经建立
        if (Server.socketList.get("/" + host) == null) {
            Log.e(TAG, "作为Clinet发送信息");
            client.clintValue(getActivity(), host, 8888);
            client.openClientThread();
            client.mSocketHelper.sendFile(path, Client.socketList.get("/" + host));
            MsgBean msgBean = new MsgBean.Builder().content(path).itemType(Constants.CHAT_SEND).build();
            msgBeanList.add(msgBean);
            //调用适配器的notifyItemInserted()用于通知列表有新的数据插入，这样新增的一条消息才能在RecyclerView中显示
            adapter.notifyItemInserted(msgBeanList.size() - 1);
            //调用scrollToPosition()方法将显示的数据定位到最后一行，以保证可以看到最后发出的一条消息
            mRvMsg.scrollToPosition(msgBeanList.size() - 1);
            mEtMsg.setText(IpUtils.instance.getWiFiIpAddressString() + ":" + (++i_et_msg));

        } else {

            Socket server_one = Server.socketList.get("/" + host);
            Log.e(TAG, "作为Server发送信息:" + server_one.getInetAddress());
            /*socket发送数据*/
            ((ScoketChatActivity) getActivity()).getServer().mSocketHelper.sendFile(path, server_one);
            MsgBean msgBean = new MsgBean.Builder().content(path).itemType(Constants.CHAT_SEND).build();
            msgBeanList.add(msgBean);
            //调用适配器的notifyItemInserted()用于通知列表有新的数据插入，这样新增的一条消息才能在RecyclerView中显示
            adapter.notifyItemInserted(msgBeanList.size() - 1);
            //调用scrollToPosition()方法将显示的数据定位到最后一行，以保证可以看到最后发出的一条消息
            mRvMsg.scrollToPosition(msgBeanList.size() - 1);
            mEtMsg.setText(IpUtils.instance.getWiFiIpAddressString() + ":" + (++i_et_msg));

        }
    }

    private static class MsgHandler extends Handler {

        private final WeakReference<ChatFragment> weakReference;

        private MsgHandler(ChatFragment chatFragment) {
            this.weakReference = new WeakReference<>(chatFragment);
        }

        @Override

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChatFragment chatFragment = weakReference.get();
            if (chatFragment != null) {
                switch (msg.what) {
                    case Constants.PROGRESS:
                        Log.e(TAG, "进度" + msg.obj.toString());
                        break;
                    case Constants.SOCKET_NULL:
                        Toast.makeText(chatFragment.getActivity(), "Scoket为NULL", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        //包含显示文件和文本，还未细分，所以没有具体的case
                        String content = ((MsgBean) msg.obj).getContent();
                        //内容不为空则创建一个新的Msg对象，并把它添加到msgList列表中
                        if (!"".equals(content)) {
                            chatFragment.msgBeanList.add(new MsgBean.Builder().content(content).itemType(Constants.CHAT_FROM).build());
                            //调用适配器的notifyItemInserted()用于通知列表有新的数据插入，这样新增的一条消息才能在RecyclerView中显示
                            chatFragment.adapter.notifyItemInserted(chatFragment.msgBeanList.size() - 1);
                            //调用scrollToPosition()方法将显示的数据定位到最后一行，以保证可以看到最后发出的一条消息
                            chatFragment.mRvMsg.scrollToPosition(chatFragment.msgBeanList.size() - 1);
                            chatFragment.mEtMsg.setText(IpUtils.instance.getWiFiIpAddressString() + ":" + (++i_et_msg));

                        }
                        break;
                }
            }
        }
    }
}
