package pri.weiqiang.tryit.shell;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

/**
 * 监听当前adbd端口监听状态，并可以进行设置,注意必须root,否则无效
 *
 * @author weiqiang
 */
public class ShellActivity extends BaseActivity {
    private static String TAG = ShellActivity.class.getSimpleName();
    boolean asRoot = true;
    boolean isOnChecked = true;
    EditText et;
    CheckBox cb;
    private ShellExecutor shellExecutor;

    /**
     * 解析得到adbd监听端口号
     */
    public void spiltString(String string) {
        String[] s = string.split("\\s+");
        Log.e(TAG, "spiltString s.length:" + s.length);
        if (s.length > 6) {
            if (s[6].substring(s[6].indexOf("/") + 1).equals("adbd")) {
                isOnChecked = false;
                cb.setChecked(true);
                et.setText(s[3].substring(s[3].lastIndexOf(":") + 1));
                et.setEnabled(false);
                isOnChecked = true;
            } else {
                cb.setChecked(false);
                et.setEnabled(true);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        shellExecutor = new ShellExecutor();
        shellExecutor.setOnResultListener(new OnResultListener() {
            @Override
            public void OnResult(ShellExecutor executor) {
                int j = executor.getResult().stdout.size();
                if (j > 1) {
                    Log.e(TAG, "获取adb监听接口");
                    int i = 2;
                    while (i < j) {
                        spiltString(executor.getResult().stdout.get(i));
                        i++;
                    }
                } else {
                    Log.e(TAG, "设置adb端口号");
                }
            }
        });
        et = findViewById(R.id.et_port);
        cb = findViewById(R.id.checkBox);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.e(TAG, "onCheckedChanged " + isChecked);
                    et.setEnabled(false);
                    if (isOnChecked) {
                        shellExecutor.setDebug(asRoot, et.getText().toString());
                    }

                } else {
                    Log.e(TAG, "onCheckedChanged " + isChecked);
                    et.setEnabled(true);
                    shellExecutor.setDebug(asRoot, "-1");


                }
            }
        });


        shellExecutor.getCurState(asRoot);

    }


}
