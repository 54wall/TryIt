package pri.weiqiang.tryit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import pri.weiqiang.charavatarlib.CharAvatarView;
import pri.weiqiang.charavatarlib.PinYin;
import pri.weiqiang.tryit.R;

public class CityAdapter extends BaseAdapter implements SectionIndexer {

    private Context mContext;
    private List<Map<String, Object>> cityList;


    public CityAdapter(Context mContext, List<Map<String, Object>> cityList) {
        this.mContext = mContext;
        this.cityList = cityList;
        LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {

        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_contact_p2p, null);
            holder.chatAvatarView = convertView.findViewById(R.id.iv_avatar);
            holder.mTvSection = convertView.findViewById(R.id.tv_section);
            holder.mTvName = convertView.findViewById(R.id.tv_city);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int int_section = getSectionForPosition(position) + 65;
        int int_position_section = getPositionForSection(int_section);

        if (position == 0 && int_position_section == -1) {
            holder.mTvSection.setVisibility(View.VISIBLE);
            holder.mTvSection.setText("#");
        } else if (position == int_position_section) {
            holder.mTvSection.setVisibility(View.VISIBLE);
            // 把section index转化为大写字母
            holder.mTvSection.setText(String.valueOf((char) (int_section)));
        } else {
            holder.mTvSection.setVisibility(View.GONE);
        }
        String displayname = cityList.get(position).get("Name").toString();
        // 没有名称仅有IP地址则显示IP地址作为名称
        displayname = (displayname.equals("") || displayname == null) ? (cityList.get(position).get("IP").toString()) : displayname;
        holder.chatAvatarView.setText(displayname);
        holder.mTvName.setText(displayname);
        return convertView;
    }

    @Override
    // 关键方法，通过section index获取在ListView中的位置
    public int getPositionForSection(int arg0) {

        // 根据参数arg0，加上65后得到对应的大写字母
        char c = (char) (arg0 + 32);
        // 循环遍历ListView中的数据，遇到第一个首字母为上面的就是要找的位置
        for (int i = 0; i < getCount(); i++) {
            String displayname = cityList.get(i).get("Name").toString();
            displayname = (displayname.equals("") || displayname == null) ? (cityList.get(i).get("IP").toString()) : displayname;
            String py = PinYin.getPinYin(displayname);
            if (py.charAt(0) == c) {
                return i;
            }
        }
        return -1;
    }

    @Override
    // 关键方法，通过在ListView中的位置获取Section index
    public int getSectionForPosition(int arg0) {
        // 获取该位置的城市名首字母
        String displayname = cityList.get(arg0).get("Name").toString();
        displayname = (displayname.equals("") || displayname == null) ? (cityList.get(arg0).get("IP").toString()) : displayname;
        String py = PinYin.getPinYin(displayname);
        char c = py.charAt(0);
        if (c >= 'a' && c <= 'z') {
            return c - 'a';
        }
        // 如果首字母不是A到Z的字母，则返回26，该类型将会被分类到#下面
        return 26;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    private static class ViewHolder {
        CharAvatarView chatAvatarView;
        TextView mTvName;
        TextView mTvSection;

    }

}
