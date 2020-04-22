package pri.weiqiang.tryit.dagger2.example2;

import android.content.Context;
import android.util.Log;

public class Person {
    private String TAG = Person.class.getSimpleName();
    private Context context;

    public Person(Context context) {
        Log.e(TAG, "a person created with context:" + context);
    }
}
