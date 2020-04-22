package pri.weiqiang.tryit.dagger2.example5;

import android.content.Context;
import android.util.Log;

public class Person {

    private static final String TAG = Person.class.getSimpleName();

    private Context context;
    private String name;

    public Person(Context context) {
        Log.e(TAG, "a person created with context:" + context);
    }

    public Person(String name) {
        this.name = name;
        Log.e(TAG, "a person created with name:" + name);
    }
}
