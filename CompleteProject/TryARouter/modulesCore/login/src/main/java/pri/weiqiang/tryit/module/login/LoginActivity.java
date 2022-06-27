package pri.weiqiang.tryit.module.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import androidx.appcompat.app.AppCompatActivity;
import pri.weiqiang.tryit.libbase.model.User;

@Route(path = "/login/LoginActivity")
public class LoginActivity extends AppCompatActivity {
    private String TAG = LoginActivity.class.getSimpleName();
    @Autowired
    long key1;
    @Autowired(name = "key2")
    String str;
    @Autowired()
    User data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ARouter.getInstance().inject(this);
        Log.e(TAG,"key1:"+key1);
        Log.e(TAG,"str:"+str);
        Log.e(TAG,"data:"+data);
    }

    public void onClickView(View view) {
//        Router.getInstance().startActivity(this,"/main/MainActivity");
        ARouter.getInstance().build("/main/MainActivity").navigation();
    }
}