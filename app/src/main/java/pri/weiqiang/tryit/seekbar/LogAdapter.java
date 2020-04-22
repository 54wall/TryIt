package pri.weiqiang.tryit.seekbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pri.weiqiang.tryit.R;

public class LogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String TAG = LogAdapter.class.getSimpleName();
    private List<String> list;

    public LogAdapter(List<String> list) {
        Log.e(TAG, "LogAdapter");
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        RecyclerView.ViewHolder holder = null;
        holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder");
        ((ViewHolder) holder).title.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
//        Log.e(TAG,"getItemCount");
        return list.size();
    }

}

class ViewHolder extends RecyclerView.ViewHolder {
    TextView title;

    ViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.tv_log);

    }
}
