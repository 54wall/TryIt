package pri.weiqiang.tryit.dagger2.example2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import pri.weiqiang.tryit.R;

//带有参数的依赖对象
public class DaggerE2Activity extends AppCompatActivity {
    @Inject
    Person person;
    @Inject
    Person person2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_e2);
        MainComponent component = DaggerMainComponent.builder()
                .mainModule(new MainModule(this)).build();
        component.inject(this);
    }
}
