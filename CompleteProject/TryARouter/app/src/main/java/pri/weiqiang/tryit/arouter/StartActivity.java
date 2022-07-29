package pri.weiqiang.tryit.arouter;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;

import androidx.appcompat.app.AppCompatActivity;
import pri.weiqiang.tryit.libbase.model.User;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//        Router.getInstance().startActivity(this,"/login/LoginActivity");
        ARouter.getInstance().build("/login/LoginActivity")
                .withLong("key1",666L)
                .withString("key2","100")
                .withSerializable("data",new User("test"))
                .navigation();
    }
}