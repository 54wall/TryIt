package advert.sdk.com.advertlibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FileUtils {
    /**
     * 读取Assets文件里面的字符串
     *
     * @param fileName 绝对路径
     * @return 原文链接：https://blog.csdn.net/lyc088456/article/details/80239043
     */
    public static String readFile(Context context, String fileName) {
        StringBuilder newstringBuilder = new StringBuilder();
        InputStream inputStream = null;
        File file = new File(fileName);
        try {
            inputStream = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                newstringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = newstringBuilder.toString();

        return result;
    }

    public static Bitmap getBitmap(String path) {
        File mFile = new File(path);
        //若该文件存在
        if (mFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            return bitmap;
        }
        return null;
    }

    /**
     * 2.建立多级目录的形式如下：
     * 例如：在SD卡上建立多级目录（"/sdcard/meido/audio/")：
     */
    public static boolean isFolderExists(String strFolder) {
        File file = new File(strFolder);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 1.只创建一级目录的形式为：
     * 例如：只在SD卡上建立一级目录（"/sdcard/audio/")：
     */
    public static boolean isSingleeFolderExists(String strFolder) {
        File file = new File(strFolder);

        if (!file.exists()) {
            return file.mkdir();
        }
        return true;
    }

    /**
     * 判断文件是否存在
     */
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 空间过小时删除七天前日志
     */
    public static void deleteOldLog(String path, Long limitSpace) {
        long usableSpace = Environment.getExternalStorageDirectory().getUsableSpace() / 1024 / 1024;
        if (usableSpace < limitSpace) {
            File file2 = new File(path);
            File[] files = file2.listFiles();
            for (int i = 0; i < files.length; i++) {
                Calendar todayCalendar = Calendar.getInstance();
                String name = files[i].getName().substring(files[i].getName().lastIndexOf("/") + 1);
                if (files[i].exists() && !name.equals(getFileNameByCalendar(todayCalendar))
                        && !name.equals(getFileNameByCalendar(getCalendarByCorrection(todayCalendar, -1)))
                        && !name.equals(getFileNameByCalendar(getCalendarByCorrection(todayCalendar, -2)))
                        && !name.equals(getFileNameByCalendar(getCalendarByCorrection(todayCalendar, -3)))
                        && !name.equals(getFileNameByCalendar(getCalendarByCorrection(todayCalendar, -4)))
                        && !name.equals(getFileNameByCalendar(getCalendarByCorrection(todayCalendar, -5)))
                        && !name.equals(getFileNameByCalendar(getCalendarByCorrection(todayCalendar, -6)))) {
                    try {
                        boolean b = delete(files[i].getAbsolutePath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获取所给日期的修正后的日期
     *
     * @param calendar      原始日期
     * @param correctionDay 修正值,单位为天
     * @return 修正后的日期
     */
    private static Calendar getCalendarByCorrection(Calendar calendar, int correctionDay) {
        Calendar oldCalendar = (Calendar) calendar.clone();
        oldCalendar.set(Calendar.DAY_OF_MONTH, oldCalendar.get(Calendar.DAY_OF_MONTH) + correctionDay);
        return oldCalendar;
    }

    /**
     * 根据日期获取文件名
     *
     * @param calendar 日期
     * @return 文件名
     */
    private static String getFileNameByCalendar(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        Date date = new Date(calendar.getTimeInMillis());
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String fileName = formatter.format(date);
        return fileName;
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param delFile 要删除的文件夹或文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String delFile) {
        File file = new File(delFile);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                return deleteSingleFile(delFile);
            } else {
                return deleteDirectory(delFile);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private static boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            return file.delete();
        } else {
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    private static boolean deleteDirectory(String filePath) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(file.getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (file.isDirectory()) {
                flag = deleteDirectory(file
                        .getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        return dirFile.delete();
    }

}
