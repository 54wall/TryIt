package pri.weiqiang.tryit.scoket.chat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.scoket.chat.bean.MsgBean;
import pri.weiqiang.tryit.scoket.chat.config.Constants;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<MsgBean> msgBeanList;

    public MsgAdapter(List<MsgBean> msgBeanList) {
        this.msgBeanList = msgBeanList;
    }

    //onCreateViewHolder()用于创建ViewHolder实例
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scoket_msg, parent, false);
        //把加载出来的布局传到构造函数中，再返回
        return new ViewHolder(view);
    }

    //onBindViewHolder()用于对RecyclerView子项的数据进行赋值，会在每个子项被滚动到屏幕内的时候执行
    @Override
    public void onBindViewHolder(@NonNull ViewHolder Holder, int position) {

        MsgBean msgBean = msgBeanList.get(position);
        //增加对消息类的判断，如果这条消息是收到的，显示左边布局，是发出的，显示右边布局
        if (msgBean.getItemType() == Constants.CHAT_FROM) {
            Holder.leftLayout.setVisibility(View.VISIBLE);
            Holder.rightLayout.setVisibility(View.GONE);
            Holder.leftMsg.setText(msgBean.getContent());
        } else if (msgBean.getItemType() == Constants.CHAT_SEND) {
            Holder.rightLayout.setVisibility(View.VISIBLE);
            Holder.leftLayout.setVisibility(View.GONE);
            Holder.rightMsg.setText(msgBean.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return msgBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout leftLayout;
        RelativeLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;

        ViewHolder(View view) {
            super(view);
            leftLayout = view.findViewById(R.id.left_layout);
            rightLayout = view.findViewById(R.id.right_layout);
            leftMsg = view.findViewById(R.id.left_msg);
            rightMsg = view.findViewById(R.id.right_msg);
        }
    }
}
