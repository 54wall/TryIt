package com.shy.word2html;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.shy.poi.word2html.BasicSet;
import com.shy.poi.word2html.WordUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bnConver, bnDelete;
    Button mBtnCombine;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd-HHmmss");
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnConver = findViewById(R.id.bn_conver);
        bnDelete = findViewById(R.id.bn_delete);
        bnConver.setOnClickListener(this);
        bnDelete.setOnClickListener(this);
        mBtnCombine = findViewById(R.id.btn_combine);
        mBtnCombine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bn_conver:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String sourceFilePath = Environment.getExternalStorageDirectory() + "/aihub/1.docx";
                        String htmlFilePath = Environment.getExternalStorageDirectory() + "/aihub/html";
                        String htmlFileName = "picTextDoc";
                        BasicSet basicSet = new BasicSet(
                                MainActivity.this,
                                sourceFilePath,//源文件
                                htmlFilePath,//保存后的文件路径
                                htmlFileName);//保存后的文件名称
                        //BasicSet 基础设置
                        //可以修改标签样式等 具体看BasicSet的属性
                        //如：取消网页自适应手机屏幕
                        //String htmlBegin =
                        //        "<!DOCTYPE html>" +
                        //                "<html>" +
                        //                "<head>" +
                        //                "</head>" +
                        //                "<body>";
                        //basicSet.setHtmlBegin(htmlBegin);
                        String htmlSavePath = WordUtils.getInstance(basicSet).word2html();
                        //跳转到webview界面预览转换html文件
                        Intent i = new Intent(MainActivity.this, PreviewActivity.class);
                        i.putExtra("path", htmlSavePath);
                        startActivity(i);
                    }
                }).start();

                break;
            case R.id.btn_combine:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String htmlFilePath = Environment.getExternalStorageDirectory() + "/54wall/combine";
                        String htmlFileName = "Combine_" + dateFormat.format(new Date());
                        File dir = new File(Environment.getExternalStorageDirectory() + "/54wall/disperse");
                        if (dir.isDirectory()) {
                            String[] path = dir.list();
                            Arrays.sort(path);
                            for (String p : path) {
                                Log.e(TAG, "p:" + p);
                                BasicSet basicSet = new BasicSet(
                                        MainActivity.this,
                                        dir + "/" + p,//源文件
                                        htmlFilePath,//保存后的文件路径
                                        htmlFileName);//保存后的文件名称
                                WordUtils.getInstance(basicSet).word2html();

                            }
                        }

                    }
                }).start();
                break;
            case R.id.bn_delete:
                break;
            default:
                break;
        }
    }
}
