package pri.weiqiang.tryit.progressbar;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Name: ProgressBarAsyncTask
 * Author: liuan
 * creatTime:2017-01-11 11:33
 * AsyncTask<Params, Progress, Result>
 */
class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, String> {

    private static String TAG = ProgressBarAsyncTask.class.getSimpleName();
    private final TextView textView;
    private final ProgressBar progressBar;

    public ProgressBarAsyncTask(TextView textView, ProgressBar progressBar) {
        this.textView = textView;
        this.progressBar = progressBar;
    }

    /**
     * 该方法步运行在子线程  主要用于异步操作 更新UI的话 应该调用publicshProgress方法（会触发onProgressUpdate对UI进行操作）
     * 首先执行的方法
     *
     * @param params
     * @return 就是参数3 也就是String result
     * <p>
     * Params
     */
    @Override
    protected String doInBackground(Integer... params) {
        NetOperator netOperator = new NetOperator();
        int i = 0;
        for (i = 0; i <= 100; i += 10) {

            netOperator.opeartor();
            publishProgress(i);
        }
        return i + "%";
//        return  i+"1";
    }

    /**
     * doInbackgrout之后执行
     *
     * @param s result
     */
    @Override
    protected void onPostExecute(String s) {
        Log.e(TAG, "s:" + s);
        //一般写下载完成
        textView.setText("异步操作执行结束" + s);
    }

    //此方法运行在Ui线程中 可以对UI控件进行设置
    @Override
    protected void onPreExecute() {
        textView.setText("开始执行异步任务");
    }

    //参数2 在主线程中执行 所有可以对UI控件进行操作
    //param Progress
    @Override
    protected void onProgressUpdate(Integer... values) {
        int value = values[0];
        Log.e(TAG, "values.length:" + values.length);
        for (int v = 0; v < values.length; v++) {
            Log.e(TAG, "v-" + v + ":" + values[v]);

        }
        progressBar.setProgress(value);
    }
}

