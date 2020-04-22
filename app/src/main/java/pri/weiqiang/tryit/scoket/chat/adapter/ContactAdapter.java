package pri.weiqiang.tryit.scoket.chat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.scoket.chat.bean.ContactBean;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>
        implements View.OnClickListener {

    private List<ContactBean> contactBeanList;

    private OnItemClickListener mOnItemClickListener = null;

    public ContactAdapter(List<ContactBean> contactBeanList) {
        this.contactBeanList = contactBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scoket_contact, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder Holder, int position) {

        ContactBean contactBean = contactBeanList.get(position);
        Holder.mTvHost.setText(contactBean.getHost());
        //将position保存在itemView的Tag中，以便点击时进行获取
        Holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return contactBeanList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvHost;

        ViewHolder(View view) {
            super(view);
            mTvHost = view.findViewById(R.id.tv_host);
        }
    }
}
