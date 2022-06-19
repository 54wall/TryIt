package pri.weiqiang.tryit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Locale;

import pri.weiqiang.tryit.util.MultiLanguageUtils;
import pri.weiqiang.tryit.util.SPUtils;

public class MyApplication extends Application {

    public static Typeface typeFace;
    private static Context sInstance;
    private static String TAG = MyApplication.class.getSimpleName();

    public static Context getContext() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Normal app init code...
        setTypeface();
        sInstance = this;
        registerActivityLifecycleCallbacks(/*MultiLanguageUtils.*/callbacks);

    }

    public void setTypeface() {
        //华文行楷
        typeFace = Typeface.createFromAsset(getAssets(), "fonts/huawen_xingkai.ttf");
        try {
            //与values/styles.xml中的<item name="android:typeface">sans</item>对应
//			Field field = Typeface.class.getDeclaredField("SERIF");
//			field.setAccessible(true);
//			field.set(null, typeFace);

//			Field field_1 = Typeface.class.getDeclaredField("DEFAULT");
//			field_1.setAccessible(true);
//			field_1.set(null, typeFace);

            //与monospace对应
//			Field field_2 = Typeface.class.getDeclaredField("MONOSPACE");
//			field_2.setAccessible(true);
//			field_2.set(null, typeFace);

            //与values/styles.xml中的<item name="android:typeface">sans</item>对应
            Field field_3 = Typeface.class.getDeclaredField("SANS_SERIF");
            field_3.setAccessible(true);
            field_3.set(null, typeFace);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //注册Activity生命周期监听回调，此部分一定加上，因为有些版本不加的话多语言切换不回来
    //registerActivityLifecycleCallbacks(callbacks);
    public Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            String language = (String) SPUtils.get(activity, MultiLanguageUtils.SP_LANGUAGE, "");
            String country = (String) SPUtils.get(activity, MultiLanguageUtils.SP_COUNTRY, "");
            Log.e(TAG, language);
            if (!TextUtils.isEmpty(language) && !TextUtils.isEmpty(country)) {
                //强制修改应用语言
                if (!MultiLanguageUtils.isSameWithSetting(activity)) {
                    Locale locale = new Locale(language, country);
                    MultiLanguageUtils.setAppLanguage(activity, locale);
                }
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

}
