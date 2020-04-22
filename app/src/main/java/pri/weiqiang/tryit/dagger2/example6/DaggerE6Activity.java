package pri.weiqiang.tryit.dagger2.example6;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import pri.weiqiang.tryit.R;


public class DaggerE6Activity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @PersonWithContext
    @Inject
    Person p1;

    @PersonWithContext
    @Inject
    Person p2;

    @PersonWithName
    @Inject
    Person p3;

    @PersonWithName
    @Inject
    Person p4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_e6);

        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .appComponent(App.appComponent)
                .activityModule(new ActivityModule())
                .build();
        activityComponent.inject(this);

        Log.d(TAG, "" + p1);
        Log.d(TAG, "" + p2);
        Log.d(TAG, "" + p3);
        Log.d(TAG, "" + p4);

    }
}