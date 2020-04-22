package pri.weiqiang.tryit.handler;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class MyHandler extends Handler {
    //注意下面的“WeakReferenceHandlerActivity”类是MyHandler类所在的外部类，即所在的activity
    WeakReference<WeakReferenceActivity> mActivity;

    MyHandler(WeakReferenceActivity activity) {
        mActivity = new WeakReference<WeakReferenceActivity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        WeakReferenceActivity theActivity = mActivity.get();
        switch (msg.what) {
            //此处可以根据what的值处理多条信息
            case 0:
                //这里可以改变activity中的UI控件的状态
                theActivity.mTvHandler.setText("Msg = 0");
                break;
            case 1:
                //这里可以改变activity中的UI控件的状态
                theActivity.mTvHandler.setText("Msg = 1");
                break;
            /*这里可以有多条要处理信息的操作*/
            /*... ...*/
        }

    }
}
