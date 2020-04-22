package pri.weiqiang.tryit.textview;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-9-6
 * Time: 上午11:34
 */
public class AsyncTextLoadTask extends AsyncTask<Object, String, String> {
    private static String TAG = "Task";
    private Context context;
    private TextViewActivity activity;
    private BufferedReader br;

    public AsyncTextLoadTask(Context context, BufferedReader br) {
        Log.e(TAG, "AsyncTextLoadTask");
        this.context = context;
        this.br = br;
        activity = (TextViewActivity) context;
    }

    @Override
    protected String doInBackground(Object... params) {
        Log.e(TAG, "doInBackground");
        StringBuilder paragraph = new StringBuilder();
        try {

            String line;

            int index = 0;
            while (index < 50 && (line = br.readLine()) != null) {
                paragraph.append(line + "\n");
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return paragraph.toString();
    }


    @Override
    protected void onPreExecute() {
        Log.e(TAG, "onPreExecute");
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        Log.e(TAG, "onPostExecute()result:" + result);
        super.onPostExecute(result);
        activity.contentTv.setText(result);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                activity.contentScroll.scrollTo(0, 0); // 记载完新数据后滚动到顶部
            }
        }, 100);
        activity.isLoading = false;
    }

}
