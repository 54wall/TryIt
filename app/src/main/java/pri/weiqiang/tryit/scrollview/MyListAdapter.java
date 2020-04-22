package pri.weiqiang.tryit.scrollview;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Apple on 2016/9/21.
 */
class MyListAdapter extends BaseAdapter {


    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            tv = new TextView(parent.getContext());
            tv.setPadding(10, 20, 10, 20);
            tv.setTextColor(Color.WHITE);
            convertView = tv;
        } else {
            tv = (TextView) convertView;
        }
        tv.setText("LIST " + position);
        return convertView;
    }
}
