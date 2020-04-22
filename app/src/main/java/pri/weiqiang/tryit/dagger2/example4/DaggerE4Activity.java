package pri.weiqiang.tryit.dagger2.example4;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;
import javax.inject.Named;

import pri.weiqiang.tryit.R;

//自定义标记 @Qualifier 和 ＠Named:＠Named
public class DaggerE4Activity extends AppCompatActivity {
    @Named("string")
    @Inject
    Person p1;

    @Named("context")
    @Inject
    Person p2;

//    @Inject
//    Lazy<Person> lazyPerson;
//    @Inject
//    Provider<Person> providerPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_e4);
        MainComponent component = DaggerMainComponent.builder()
                .mainModule(new MainModule(this)).build();
        component.inject(this);

//        Person p1 = lazyPerson.sayHelloFromJNI();
//        Person p2 = lazyPerson.sayHelloFromJNI();
//        Person p3 = providerPerson.sayHelloFromJNI();
//        Person p4 = providerPerson.sayHelloFromJNI();
    }
}
