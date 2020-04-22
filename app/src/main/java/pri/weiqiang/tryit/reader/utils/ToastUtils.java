package pri.weiqiang.tryit.reader.utils;

import android.widget.Toast;

import pri.weiqiang.tryit.MyApplication;

/**
 * Created by newbiechen on 17-5-11.
 */

public class ToastUtils {

    public static void show(String msg) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
