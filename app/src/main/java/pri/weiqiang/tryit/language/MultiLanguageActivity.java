package pri.weiqiang.tryit.language;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;
import pri.weiqiang.tryit.util.MultiLanguageUtils;
import pri.weiqiang.tryit.util.SPUtils;

public class MultiLanguageActivity extends BaseActivity {

    private String TAG = MultiLanguageActivity.class.getSimpleName();
    private RadioGroup mRgLanguage;
    private RadioButton mRbZh;
    private RadioButton mRbEn;
    private TextView mTvMultiLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_language);
        mTvMultiLanguage = findViewById(R.id.tv_multi_language);
        mRgLanguage = findViewById(R.id.rg_language);
        mRbZh = findViewById(R.id.rb_zh);
        mRbEn = findViewById(R.id.rb_en);
        String spLanguage = (String) SPUtils.get(this, MultiLanguageUtils.SP_LANGUAGE, "zh");
        Log.e(TAG,"spLanguage:"+spLanguage);
        if (spLanguage.equalsIgnoreCase("zh")) {
            mRbZh.setChecked(true);
        } else {
            mRbEn.setChecked(true);
        }
        mRgLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_zh) {
                    MultiLanguageUtils.changeLanguage(MultiLanguageActivity.this, "zh", "CN");
                } else if (checkedId == R.id.rb_en) {
                    MultiLanguageUtils.changeLanguage(MultiLanguageActivity.this, "en", "US");
                }
//                mTvMultiLanguage.setText(R.string.content_multi_language);

            }
        });


        mTvMultiLanguage.setText(R.string.content_multi_language);
    }
}
