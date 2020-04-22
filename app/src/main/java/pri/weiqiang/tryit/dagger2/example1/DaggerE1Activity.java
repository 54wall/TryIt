package pri.weiqiang.tryit.dagger2.example1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import pri.weiqiang.tryit.R;

//单例模式 @Singleton（基于Component）
public class DaggerE1Activity extends AppCompatActivity {

    @Inject
    Person person;
    @Inject
    Person person2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_e1);
        MainComponent component = DaggerMainComponent.builder()
                .mainModule(new MainModule()).build();
        component.inject(this);
        //经典方式
//        person = new Person();

    }
}
