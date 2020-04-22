package pri.weiqiang.tryit.shutdown;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class ShutDownActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shut_down);
        Button btnShutDown = findViewById(R.id.btn_shut_down);
        Button btnReboot = findViewById(R.id.btn_reboot);
        btnShutDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //使用Runtime.getRuntime().exec不需要权限
                try {
//                    Runtime.getRuntime().exec(new String[]{"su","-c","shutdown"});//有时无效
                    //reboot -p 依然表示关机
                    Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot -p"});
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        btnReboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Runtime.getRuntime().exec("su -c reboot");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Button btnApiShutDown = findViewById(R.id.btn_api_shutdown);
        //需要权限为系统级应用，会弹出关机进度条，推荐 https://blog.csdn.net/qq_31371757/article/details/83378288
        btnApiShutDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
                intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ShutDownActivity.this.startActivity(intent);

            }
        });
    }
}
