package pri.weiqiang.tryit.scrollview;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pri.weiqiang.tryit.R;

/**
 * Created by 青青-子衿 on 2018/1/15.
 */


public class MySimpleRecyclerViewAdapter extends RecyclerView.Adapter<MySimpleRecyclerViewAdapter.ViewHolder> {
    private List<String> list;

    public MySimpleRecyclerViewAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public MySimpleRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scroll_view, parent, false);
        MySimpleRecyclerViewAdapter.ViewHolder viewHolder = new MySimpleRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MySimpleRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mText.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mText;

        ViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.tv_name);
        }
    }
}
