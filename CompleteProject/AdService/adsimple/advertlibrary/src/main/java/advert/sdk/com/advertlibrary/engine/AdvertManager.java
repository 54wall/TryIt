package advert.sdk.com.advertlibrary.engine;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import advert.sdk.com.advertlibrary.ui.AdPresentation;
import advert.sdk.com.advertlibrary.utils.AdvertUtils;

public class AdvertManager {
    private final Context context;
    private final DisplayManager mDisplayManager;   //使用DisplayManagerAPI可以获得当前连接的所有显示屏的枚举
    private AdPresentation mAdPresentation;
    private final String TAG = AdvertManager.class.getSimpleName();

    public AdvertManager(Context context) {
        this.context = context;
        mDisplayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        ShowPresentationByDisplaymanager();
        AdvertUtils.getInstance().initLoopView(mAdPresentation);
    }

    protected void ShowPresentationByDisplaymanager() {
        Display[] presentationDisplays = mDisplayManager.getDisplays();
        if (presentationDisplays.length > 0) {
            Display displayMain = presentationDisplays[0];
            showPresentationMain(displayMain);
        }
    }

    private void showPresentationMain(Display presentationDisplay) {
        // Dismiss the current presentation if the display has changed.
        if (mAdPresentation != null && mAdPresentation.getDisplay() != presentationDisplay) {
            Log.i(TAG, "Dismissing presentation because the current route no longer "
                    + "has a presentation display.");
            mAdPresentation.dismiss();
            mAdPresentation = null;
        }
        // Show a new presentation if needed.
        if (mAdPresentation == null && presentationDisplay != null) {
            Log.i(TAG, "Showing presentation on display: " + presentationDisplay);
            mAdPresentation = new AdPresentation(context, presentationDisplay);
            //  mPresentation.setOnDismissListener(mOnDismissListener);
            mAdPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            if (Build.VERSION.SDK_INT >= 26) {//8.0新特性
                mAdPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            } else {
                mAdPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            }

            try {
                mAdPresentation.show();
            } catch (WindowManager.InvalidDisplayException ex) {
                Log.w(TAG, "Couldn't show presentation!  Display was removed in "
                        + "the meantime.", ex);
                mAdPresentation = null;
            }
        }
    }

    public void destoryPresentation() {
        if (mAdPresentation != null) {
            mAdPresentation.dismiss();
            mAdPresentation.destoryThread();
            mAdPresentation = null;
        }
    }
}
