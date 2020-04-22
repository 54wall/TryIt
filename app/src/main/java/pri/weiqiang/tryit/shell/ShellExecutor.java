/*
 * Copyright (C) 2018 Jared Rummler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pri.weiqiang.tryit.shell;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import java.io.File;


public class ShellExecutor {

    private static String TAG = ShellExecutor.class.getSimpleName();
    private OnResultListener listener;
    private CommandResult commandResult;

    // 判断是否具有ROOT权限
    public static boolean isRoot() {
        boolean res = false;
        try {
            if ((!new File("/system/bin/su").exists()) &&
                    (!new File("/system/xbin/su").exists())) {
                res = false;
            } else {
                res = true;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return res;
    }

    public void destroy() {
        Shell.SU.closeConsole();
    }

    @SuppressLint("StaticFieldLeak")
    public void execute(final boolean asRoot, final String... commands) {
        new AsyncTask<String, Void, CommandResult>() {

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected CommandResult doInBackground(String... commands) {
                Log.e(TAG, "Comands" + commands);
                if (asRoot) {
                    return Shell.SU.run(commands);
                } else {
                    return Shell.SH.run(commands);
                }
            }

            @Override
            protected void onPostExecute(CommandResult commandResult) {
                Log.e(TAG, "onPostExecute commandResult:" + commandResult);
                Spanned result = resultToHtml(commandResult);
                ShellExecutor.this.commandResult = commandResult;
                /*需要判断listener是否为null，因为有的ShellExecutor对象listener没有设置也就是为null,但onPostExecute是一定执行的*/
                if (listener != null) {
                    listener.OnResult(ShellExecutor.this);
                }

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, commands);
    }

    public CommandResult getResult() {
        return commandResult;
    }

    public void setOnResultListener(OnResultListener listener) {
        this.listener = listener;
    }

    private Spanned resultToHtml(CommandResult result) {
        StringBuilder html = new StringBuilder();
        // exit status
        html.append("<p><strong>Edit Code:</strong> ");
        if (result.isSuccessful()) {
            html.append("<font color='green'>").append(result.exitCode).append("</font>");
        } else {
            html.append("<font color='red'>").append(result.exitCode).append("</font>");
        }
        html.append("</p>");
        // stdout
        if (result.stdout.size() > 0) {
            html.append("<p><strong>STDOUT:</strong></p><p>")
                    .append(result.getStdout().replaceAll("\n", "<br>"))
                    .append("</p>");
        }
        // stderr
        if (result.stderr.size() > 0) {
            html.append("<p><strong>STDERR:</strong></p><p><font color='red'>")
                    .append(result.getStderr().replaceAll("\n", "<br>"))
                    .append("</font></p>");
        }
        return Html.fromHtml(html.toString());
    }

    public void setDebug(boolean asRoot, String port) {
        execute(asRoot, "setprop service.adb.tcp.port " + port, "stop adbd", "start adbd");
    }

    /**
     * 检测端口是否处于监听状态,并且返回监听端口号
     */
    public void getCurState(boolean asRoot) {
        execute(asRoot, "busybox netstat -tlp");
    }
}
