package pri.weiqiang.tryit.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

import pri.weiqiang.tryit.MyApplication;
import pri.weiqiang.tryit.language.MultiLanguageActivity;

//import android.support.v4.os.ConfigurationCompat;
//import android.support.v4.os.LocaleListCompat;


/**
 * Todo 多语言设置
 * 来自：https://blog.csdn.net/m0_38074457/article/details/84993366
 * 使用步骤：
 * 1、Application中onCreate添加registerActivityLifecycleCallbacks(MultiLanguageUtils.callbacks);
 *
 * @Override protected void attachBaseContext(Context base) {
 * //系统语言等设置发生改变时会调用此方法，需要要重置app语言
 * super.attachBaseContext(MultiLanguageUtils.attachBaseContext(base));
 * }
 * 2、改变应用语言调用MultiLanguageUtils.changeLanguage(activity,type,type);
 */
//public final static String SP_LANGUAGE="SP_LANGUAGE";
//public final static String SP_COUNTRY="SP_COUNTRY";
public class MultiLanguageUtils {
    private static String TAG = MultiLanguageUtils.class.getSimpleName();
    public static final String SP_LANGUAGE = "SETTING_LANGUAGE";
    public static final String SP_COUNTRY = "SETTING_COUNTRY";

    /**
     * TODO 1、 修改应用内语言设置
     *
     * @param language 语言  zh/en
     * @param area     地区
     */
    public static void changeLanguage(Context context, String language, String area) {
        if (TextUtils.isEmpty(language) && TextUtils.isEmpty(area)) {
            //如果语言和地区都是空，那么跟随系统s
            SPUtils.put(context, SP_LANGUAGE, "");
            SPUtils.put(context, SP_COUNTRY, "");
        } else {
            //不为空，那么修改app语言，并true是把语言信息保存到sp中，false是不保存到sp中
            Locale newLocale = new Locale(language, area);
            setAppLanguage(context, newLocale);
            saveLanguageSetting(context, newLocale);
        }
        //重启app,这一步一定要加上，如果不重启app，可能打开新的页面显示的语言会不正确
//        Intent intent = new Intent(MyApplication.getContext(), MultiLanguageActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        MyApplication.getContext().startActivity(intent);
//        android.os.Process.killProcess(android.os.Process.myPid());

    }

    /**
     * Todo 更新应用语言
     *
     * @param context
     * @param locale
     */
    public static void setAppLanguage(Context context, Locale locale) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale);
            configuration.setLocales(new LocaleList(locale));
            context.createConfigurationContext(configuration);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
            resources.updateConfiguration(configuration, metrics);
        } else {
            configuration.locale = locale;
            resources.updateConfiguration(configuration, metrics);
        }
    }

    /**
     * TODO 3、 跟随系统语言
     */
    public static Context attachBaseContext(Context context) {
        String spLanguage = (String) SPUtils.get(context, SP_LANGUAGE, "");
        String spCountry = (String) SPUtils.get(context, SP_COUNTRY, "");
        if (!TextUtils.isEmpty(spLanguage) && !TextUtils.isEmpty(spCountry)) {
            Locale locale = new Locale(spLanguage, spCountry);
            setAppLanguage(context, locale);
        }
        return context;
    }

    /**
     * 判断sp中和app中的多语言信息是否相同
     */
    public static boolean isSameWithSetting(Context context) {
        Locale locale = getAppLocale(context);
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String sp_language = (String) SPUtils.get(context, SP_LANGUAGE, "");
        String sp_country = (String) SPUtils.get(context, SP_COUNTRY, "");
        Log.e(TAG,"isSameWithSetting-language:"+language+",country:"+country);
        Log.e(TAG,"isSameWithSetting-sp_language:"+language+",sp_country:"+country);
        if (language.equals(sp_language) && country.equals(sp_country)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保存多语言信息到sp中
     */
    public static void saveLanguageSetting(Context context, Locale locale) {
        SPUtils.put(context, SP_LANGUAGE, locale.getLanguage());
        SPUtils.put(context, SP_COUNTRY, locale.getCountry());
    }

    /**
     * 获取应用语言
     */
    public static Locale getAppLocale(Context context) {
        Locale local;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            local = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            local = context.getResources().getConfiguration().locale;
        }
        return local;
    }

    /**
     * 获取系统语言
     */
//    public static LocaleListCompat getSystemLanguage(){
//        Configuration configuration = Resources.getSystem().getConfiguration();
//        LocaleListCompat locales = ConfigurationCompat.getLocales(configuration);
//        return locales;
//    }



}
