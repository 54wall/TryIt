package pri.weiqiang.tryit.textview;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

/**
 * https://www.cnblogs.com/tiantianbyconan/p/3311658.html
 */
public class TextViewActivity extends BaseActivity {
    private static String TAG = TextViewActivity.class.getSimpleName();
    public BorderScrollView contentScroll;
    public TextView contentTv;
    public boolean isLoading;
    private BufferedReader br;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        context = this;

        contentTv = findViewById(R.id.content);
        contentScroll = findViewById(R.id.contentScroll);

        // 注册刚写的滚动监听器
        contentScroll.setOnScrollChangedListener(new OnScrollChangedListenerSimple() {
            @Override
            public void onScrollBottom() {
                Log.e(TAG, "contentScroll.setOnScrollChangedListener");
                synchronized (TextViewActivity.class) {
                    if (!isLoading) {
                        isLoading = true;
                        new AsyncTextLoadTask(context, br).execute();
                    }
                }
            }
        });

        try {
            InputStream is = context.getAssets().open("huanye.txt");
            br = new BufferedReader(new InputStreamReader(is));

            new AsyncTextLoadTask(context, br).execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (null != br) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
