package pri.weiqiang.tryit.scrollview;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Apple on 2016/9/21.
 */

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView tv;

    public MyRecyclerViewHolder(TextView itemView) {
        super(itemView);
        tv = itemView;
    }

}
