package pri.weiqiang.tryit.dagger2.example5;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import pri.weiqiang.tryit.R;

//自定义标记 @Qualifiery 及 懒加载Lazy和强制重新加载Provider
public class DaggerE5Activity extends AppCompatActivity {
    @PersonWithContext
    @Inject
    Person p1;
    @PersonWithName
    @Inject
    Person p2;
    private String TAG = DaggerE5Activity.class.getSimpleName();

//    @Inject
//    Lazy<Person> lazyPerson;
//    @Inject
//    Provider<Person> providerPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_e5);
        MainComponent component = DaggerMainComponent.builder()
                .mainModule(new MainModule(this)).build();
        component.inject(this);
        Log.e(TAG, "");
//        Person p1 = lazyPerson.sayHelloFromJNI();
//        Person p2 = lazyPerson.sayHelloFromJNI();
//        Person p3 = providerPerson.sayHelloFromJNI();
//        Person p4 = providerPerson.sayHelloFromJNI();
    }
}
