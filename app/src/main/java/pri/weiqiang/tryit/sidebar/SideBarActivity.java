package pri.weiqiang.tryit.sidebar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pri.weiqiang.charavatarlib.PinYin;
import pri.weiqiang.sidebarlib.SideBar;
import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.adapter.CityAdapter;
import pri.weiqiang.tryit.aop.aspectj.BehaviorTrace;
import pri.weiqiang.tryit.base.BaseActivity;

public class SideBarActivity extends BaseActivity {
    private String TAG = SideBarActivity.class.getSimpleName();
    private ListView mLvCity;
    private SideBar mSideBar;
    private TextView mTvHint;
    private CityAdapter cityAdapter;
    private List<Map<String, Object>> cityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_bar);
        mLvCity = findViewById(R.id.lv_city);
        mTvHint = findViewById(R.id.tv_center_hint);
        mSideBar = findViewById(R.id.sidebar);
        // 监听SideBar的手指按下和抬起事件
        mSideBar.setOnSelectListener(new SideBar.OnSelectListener() {

            @Override
            public void onSelect(String s) {
                // 手指按下时显示中央的字母
                mTvHint.setVisibility(View.VISIBLE);
                mTvHint.setText(s);
                // 如果SideBar按下的是#号，则ListView定位到位置0
                if ("#".equals(s)) {
                    mLvCity.setSelection(0);
                    return;
                }
                // 获取手指按下的字母所在的块索引
                int section = s.toCharArray()[0];
                // 根据块索引获取该字母首次在ListView中出现的位置
                int pos = cityAdapter.getPositionForSection(section);
                // 定位ListView到按下字母首次出现的位置
                mLvCity.setSelection(pos);
            }

            @Override
            public void onMoveUp(String s) {
                mTvHint.setVisibility(View.GONE);
                mTvHint.setText(s);
            }
        });
        getContactsData();

    }

    /**
     * 更新好友列表
     */
    @BehaviorTrace(value = "getContactsData", type = 1)
    private void getContactsData() {


        cityList.clear();
        String[] strings = {"#北京", "#Beijing", "#Shanghai", "上海", "北京", "杭州", "广州", "南京", "苏州", "深圳", "成都", "重庆", "天津", "宁波", "扬州", "无锡", "福州", "厦门", "武汉", "西安", "沈阳", "大连", "青岛", "济南", "海口", "石家庄",
                "唐山", "秦皇岛", "邯郸", "邢台", "保定", "张家口", "承德", "沧州", "廊坊", "衡水", "太原"};
        for (int i = 0; i < 20; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("Name", strings[i]);
            map.put("Phone", "18912345678");
            map.put("Id", "1");
            map.put("IP", "123.1.1.1");
            cityList.add(map);
        }
        sortChatByPinyin(cityList);
        if (cityList == null) {
            Log.e(TAG, "cityList == null");
        } else {
            if (cityAdapter == null) {
                cityAdapter = new CityAdapter(this, cityList);
                mLvCity.setAdapter(cityAdapter);
            } else {
                cityAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 非字母开头的条目在最前，如果出现前几位字母一致的情况，则顺序比较下一位 目前思路是排序后
     *
     * @param childList
     */
    @BehaviorTrace(value = "sortChatByPinyin", type = 1)
    private void sortChatByPinyin(List<Map<String, Object>> childList) {

        Collections.sort(childList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(final Map<String, Object> map1, final Map<String, Object> map2) {
                String displayname1 = map1.get("Name").toString();
                String displayname2 = map2.get("Name").toString();
                displayname1 = (displayname1.equals("") || displayname1 == null) ? (map1.get("IP").toString()) : displayname1;
                displayname2 = (displayname2.equals("") || displayname2 == null) ? (map2.get("IP").toString()) : displayname2;
                String py1 = PinYin.getPinYin(displayname1);
                String py2 = PinYin.getPinYin(displayname2);
                // 排序按照全部拼音，如果出现前几位字母一致的情况，则顺序比较下一位
                return (py1.compareTo(py2));
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }
}
