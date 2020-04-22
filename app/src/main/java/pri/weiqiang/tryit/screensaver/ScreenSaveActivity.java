package pri.weiqiang.tryit.screensaver;

import android.os.Bundle;

import pri.weiqiang.tryit.R;

public class ScreenSaveActivity extends ScreenSaverBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_sreen_save;
    }
}
