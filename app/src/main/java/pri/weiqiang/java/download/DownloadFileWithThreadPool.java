package pri.weiqiang.java.download;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
* https://www.cnblogs.com/iwideal/p/6045118.html
*
* 思路：

1、基本思路是将文件分段切割、分段传输、分段保存。

2、分段切割用到HttpUrlConnection对象的setRequestProperty("Range", "bytes=" + start + "-" + end)方法。

3、分段传输用到HttpUrlConnection对象的getInputStream()方法。

4、分段保存用到RandomAccessFile的seek(int start)方法。

5、创建指定长度的线程池，循环创建线程，执行下载操作。

* */
class DownloadFileWithThreadPool {
    private static long getContentLength(String urlLocation) throws IOException {
        URL url = null;
        if (urlLocation != null) {
            url = new URL(urlLocation);
        }
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestMethod("GET");
        long len = conn.getContentLength();

        return len;
    }

    public void getFileWithThreadPool(String urlLocation, String filePath, int poolLength) throws IOException {
        Executor threadPool = Executors.newFixedThreadPool(poolLength);

        long len = getContentLength(urlLocation);
        for (int i = 0; i < poolLength; i++) {
            long start = i * len / poolLength;
            long end = (i + 1) * len / poolLength - 1;
            if (i == poolLength - 1) {
                end = len;
            }
            DownloadWithRange download = new DownloadWithRange(urlLocation, filePath, start, end);
            threadPool.execute(download);
        }
    }
}