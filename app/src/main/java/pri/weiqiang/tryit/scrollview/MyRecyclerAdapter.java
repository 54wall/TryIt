package pri.weiqiang.tryit.scrollview;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Apple on 2016/9/21.
 */
class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {


    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView tv = new TextView(parent.getContext());
        tv.setPadding(10, 20, 10, 20);
        tv.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, (int) v.getTag());
                }
            }
        });
        return new MyRecyclerViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {
        holder.tv.setText("RECYCLER " + position);
        holder.tv.setTag(position);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
