package pri.weiqiang.tryit.arouter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class App extends Application {
    private boolean isDebug = true;
    @Override
    public  void  onCreate() {
        super.onCreate();
//        Router.getInstance().register("/main/MainActivity", MainActivity.class);
//        Router.getInstance().register("/login/LoginActivity", LoginActivity.class);
        if (isDebug) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);
    }
}
