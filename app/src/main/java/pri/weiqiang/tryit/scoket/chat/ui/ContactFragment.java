package pri.weiqiang.tryit.scoket.chat.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.scoket.chat.adapter.ContactAdapter;
import pri.weiqiang.tryit.scoket.chat.bean.ContactBean;
import pri.weiqiang.tryit.scoket.chat.utils.IpUtils;

public class ContactFragment extends Fragment {

    private final String TAG = ContactFragment.class.getSimpleName();
    private EditText mEtIpRemote;
    private EditText mEtPortLocal;
    private EditText mEtPortRemote;
    private int pite;
    private String ip = "";

    private List<ContactBean> contactBeanList = new ArrayList<>();
    private RecyclerView mRvContact;
    private ContactAdapter contactAdapter;
    private int n = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scoket_contact, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRvContact = getView().findViewById(R.id.rv_contact);
        mEtIpRemote = getView().findViewById(R.id.et_ip_remote);
        mEtPortRemote = getView().findViewById(R.id.et_port_remote);
        mEtPortLocal = getView().findViewById(R.id.et_port_local);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRvContact.setLayoutManager(layoutManager);
        contactAdapter = new ContactAdapter(contactBeanList);
        mRvContact.setAdapter(contactAdapter);

        TextView mTvLocalIp = getView().findViewById(R.id.tv_ip_local);
        mTvLocalIp.setText(IpUtils.instance.getWiFiIpAddressString());
        getActivity().setTitle(IpUtils.instance.getWiFiIpAddressString());
        mEtIpRemote.setText(IpUtils.instance.getWiFiIpAddressString());
        final Button mBtnServer = getView().findViewById(R.id.btn_server);
        Button mBtnAdd = getView().findViewById(R.id.btn_add);
        mBtnServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pite = Integer.parseInt(mEtPortLocal.getText().toString());
                ((ScoketChatActivity) getActivity()).startServer(pite);
                mBtnServer.setEnabled(false);

            }
        });
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContactBean contactBean = new ContactBean();
                contactBean.setHost(mEtIpRemote.getText().toString());
                contactBeanList.add(contactBean);
                contactAdapter.notifyItemInserted(contactBeanList.size() - 1);

            }
        });
        contactAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.e(TAG, "host" + contactBeanList.get(position).getHost());
                ((ScoketChatActivity) getActivity()).showChat(contactBeanList.get(position).getHost());
            }
        });
    }
}
