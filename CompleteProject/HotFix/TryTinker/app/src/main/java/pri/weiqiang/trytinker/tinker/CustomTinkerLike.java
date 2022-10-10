package pri.weiqiang.trytinker.tinker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

@DefaultLifeCycle(application = ".MyTinkerApplication",flags = ShareConstants.TINKER_ENABLE_ALL,
loadVerifyFlag = false)
public class CustomTinkerLike extends ApplicationLike {

    public CustomTinkerLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        TinkerManager.installTinker(this);
    }
}
