package pri.weiqiang.tryit.constants;

import android.app.Service;


public class Constants {


    //广播接收
    public static final String RESULT_EXCUTOR = "pri.weiqiang.home.RESULT_EXCUTOR";
    //开机广播,系统默认不要更改
    public static final String BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    //安装广播
    public static final String MANAGE_INSTALL_SILENT = "pri.weiqiang.home.MANAGE_INSTALL_SILENT";
    public static final String MANAGE_UN_INSTALL_SILENT = "pri.weiqiang.home.MANAGE_UNINSTALL_SILENT";
    public static final String START_SERVICE = "pri.weiqiang.home.RUN";
    public static final String ACTION_INSTALL_SILENT = "pri.weiqiang.home.service.INSTALL_SILENCE";
    public static final String ACTION_UN_INSTALL_SILENT = "pri.weiqiang.home.service.UN_INSTALL_SILENCE";

    public static final int returnValue = Service.START_STICKY;

    public static final int NOTIFICATION_DOWNLOAD_PROGRESS_ID = 0x0001;
    public static final String EXTRA_PACKAGE = "pri.weiqiang.home.EXTRA_PACKAGE";
    public static final String EXTRA_APK_PATH = "pri.weiqiang.home.EXTRA_APK_PATH";
    public static final String EXTRA_APK_NAME = "pri.weiqiang.home.EXTRA_APK_NAME";
    public static final String EXTRA_IS_OPEN = "pri.weiqiang.home.EXTRA_IS_OPEN";
    public static final String EXTRA_RESULT = "pri.weiqiang.home.EXTRA_RESULT";
    public static final String EXTRA_VISIBLE = "pri.weiqiang.home.EXTRA_VISIBLE";
    //调用本项目时，调用端需要统一参数，这里不再使用PACKAGE_NAME，因为是另外项目使用
    public static final String EXTRA_MODE = "pri.weiqiang.home.EXTRA_MODE";
    public static final String EXTRA_MODE_INSTALL_SILENT = "pri.weiqiang.home.EXTRA_MODE_INSTALL_SILENT";
    public static final String EXTRA_MODE_INSTALL = "pri.weiqiang.home.EXTRA_MODE_INSTALL";
    public static final String EXTRA_MODE_UNINSTALL_SILENT = "pri.weiqiang.home.EXTRA_MODE_UNINSTALL_SILENT";
    public static final String EXTRA_MODE_UNINSTALL = "pri.weiqiang.home.EXTRA_MODE_UNINSTALL";
    public static final String EXTRA_MODE_UNINSTALL_INSTALL = "pri.weiqiang.home.EXTRA_MODE_UNINSTALL_INSTALL";
    //先静默卸载再静默安装
    public static final String EXTRA_MODE_UNINSTALL_INSTALL_SILENT = "pri.weiqiang.home.EXTRA_MODE_UNINSTALL_INSTALL_SILENT";

}
