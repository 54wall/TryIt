package advert.sdk.com.advertlibrary.engine;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import advert.sdk.com.advertlibrary.receiver.AdvertReceiver;
import advert.sdk.com.advertlibrary.service.AdvertService;
import advert.sdk.com.advertlibrary.utils.AdvertUtils;

public class AdvertEngine {
    private volatile static AdvertEngine instance = null;
    private final String TAG = AdvertEngine.class.getSimpleName();
    private boolean flag = false;
    private AdvertReceiver advertReceiver;
    private Context context;

    public static AdvertEngine getInstance() {
        if (instance == null) {
            synchronized (AdvertEngine.class) {
                if (instance == null) {
                    instance = new AdvertEngine();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化广告程序,在程序启动时候调用
     */
    public void init(Context context) {
        Intent service = new Intent(context, AdvertService.class);
        this.context = context;
        context.startService(service);
        if (!flag) {
            flag = true;
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
            advertReceiver = new AdvertReceiver();
            context.registerReceiver(advertReceiver, intentFilter);
        }
    }

    /**
     * 销毁服务和广播,在程序停止时候调用
     * 但这里会重新调用AdvertService onCreate 重新new AdvertManager(this);导致广告的remove无效
     */
    public void burning(Context context) {
        AdvertUtils.getInstance().remove();
        AdvertUtils.getInstance().destory();
        Intent service = new Intent(context, AdvertService.class);
        context.stopService(service);
        if (flag) {
            flag = false;
            context.unregisterReceiver(advertReceiver);
        }
    }


}
