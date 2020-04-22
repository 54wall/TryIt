package pri.weiqiang.tryit;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.squareup.leakcanary.LeakCanary;

import java.lang.reflect.Field;

public class MyApplication extends Application {

    public static Typeface typeFace;
    private static Context sInstance;
    private String TAG = MyApplication.class.getSimpleName();

    public static Context getContext() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
        setTypeface();
        sInstance = this;


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

}
