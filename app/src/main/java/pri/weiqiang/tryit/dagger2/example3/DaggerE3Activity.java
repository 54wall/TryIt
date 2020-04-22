package pri.weiqiang.tryit.dagger2.example3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import pri.weiqiang.tryit.R;

//依赖一个组件
public class DaggerE3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_e3);
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule())
                .build();
        activityComponent.inject(this);

    }
}
