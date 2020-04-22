package pri.weiqiang.tryit.dagger2.example2;

import android.content.Context;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private String TAG = MainModule.class.getSimpleName();
    private Context context;

    public MainModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context providesContext() {
        return this.context;
    }

    @Singleton
    @Provides
    public Person providesPerson(Context context) {
        Log.e(TAG, "person from module");
        return new Person(context);
    }
}