package pri.weiqiang.tryit.dagger2.example1;

import android.util.Log;

public class Person {
    private String TAG = Person.class.getSimpleName();

    public Person() {
        Log.e(TAG, "a person example1 created:" + Person.this);
    }
}
