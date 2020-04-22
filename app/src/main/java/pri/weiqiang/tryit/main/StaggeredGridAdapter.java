package pri.weiqiang.tryit.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pri.weiqiang.tryit.R;


/**
 * Created by android on 2016/6/4.
 */
public class StaggeredGridAdapter extends RecyclerView.Adapter<StaggeredGridHolder> {
    private View view;
    private LayoutInflater mInflater;
    private OnItemClickLitener mOnItemClickLitener;
    private List<ModelMainItem> list;

    public StaggeredGridAdapter(Context context, List<ModelMainItem> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public StaggeredGridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //利用反射将item的布局加载出来
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_main, null);
        //new一个我们的ViewHolder，findViewById操作都在LinearHolder的构造方法中进行了
        return new StaggeredGridHolder(view);
    }

    @Override
    public void onBindViewHolder(final StaggeredGridHolder holder, int position) {

        String title = list.get(position).getName();
        holder.mTvTitle.setText(title);
        holder.mLlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(holder.itemView, pos);
            }
        });
        holder.mLlContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Class getItemLessonId(int postion) {
        return list.get(postion).getActivity();
    }

}

class StaggeredGridHolder extends RecyclerView.ViewHolder {

    TextView mTvTitle;
    LinearLayout mLlContent;

    public StaggeredGridHolder(final View view) {
        super(view);
        mTvTitle = view.findViewById(R.id.tv_title);
        mLlContent = view.findViewById(R.id.ll_content);
    }
}

