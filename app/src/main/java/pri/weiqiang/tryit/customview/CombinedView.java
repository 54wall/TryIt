package pri.weiqiang.tryit.customview;


import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import pri.weiqiang.tryit.R;


public class CombinedView extends FrameLayout {

    private TextView mTvName;
    private TextView mTvState;
    private TextView mTvInfo;
    private TextView mTvTip;
    private TextView mTvProgress;
    private LinearLayout mLlChargeDetail;

    public CombinedView(Context context) {
        super(context);
    }

    public CombinedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_combined, this);
        mTvName = findViewById(R.id.tv_name);
        mTvState = findViewById(R.id.tv_state);
        mTvInfo = findViewById(R.id.tv_info);
        mTvTip = findViewById(R.id.tv_tip);
        mTvProgress = findViewById(R.id.tv_progress);
        mLlChargeDetail = findViewById(R.id.ll_charge_detail);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CombinedView);
        if (attributes != null) {
            //处理titleBar背景色
            int backGround = attributes.getResourceId(R.styleable.CombinedView_combined_view_background, R.drawable.android_logo);
            setBackgroundResource(backGround);
            //设置左边按钮的文字
            String tvName = attributes.getString(R.styleable.CombinedView_combined_view_tv_name);
            if (!TextUtils.isEmpty(tvName)) {
                mTvName.setText(tvName);
            }

            //先处理左边按钮
            //获取是否要显示左边按钮
            boolean isUse = attributes.getBoolean(R.styleable.CombinedView_combined_view_state_use, false);
            if (isUse) {
                mTvState.setText("充电中");
                mTvState.setTextColor(getResources().getColor(R.color.colorAccent));
                mTvTip.setVisibility(VISIBLE);
                mTvProgress.setVisibility(VISIBLE);
            } else {
                mLlChargeDetail.setBackgroundColor(getResources().getColor(R.color.transparent));
                mTvTip.setVisibility(INVISIBLE);
                mTvProgress.setVisibility(INVISIBLE);
                mTvState.setText("空  闲");
                mTvState.setTextColor(getResources().getColor(R.color.colorPrimary));
                StringBuilder sb = new StringBuilder();
                sb.append("接口类型：国标2015");
                sb.append("\n功率：60kW");
                sb.append("\n电压：500V");
                sb.append("\n单价：0.5元/kW·h");
                mTvInfo.setText(sb);

            }
            attributes.recycle();
        }
    }


    public void setTvInfo(String info) {
        mTvInfo.setText(info);
    }

    public void setTvProgress(String info) {
        mTvProgress.setText(info);
    }

    // 设置标题的方法
    public void setUse(boolean isUse) {
        if (isUse) {
            mTvState.setText("充电中");
            mTvTip.setVisibility(VISIBLE);
            mTvProgress.setVisibility(VISIBLE);
        } else {
            mLlChargeDetail.setBackgroundColor(getResources().getColor(R.color.transparent));
            mTvTip.setVisibility(INVISIBLE);
            mTvProgress.setVisibility(INVISIBLE);
            mTvState.setText("空  闲");
            mTvState.setTextColor(getResources().getColor(R.color.colorPrimary));
            StringBuilder sb = new StringBuilder();
            sb.append("接口类型：国标2015");
            sb.append("\n功率：60kW");
            sb.append("\n电压：500V");
            sb.append("\n单价：0.5元/kW·h");
            mTvInfo.setText(sb);
        }
    }


}