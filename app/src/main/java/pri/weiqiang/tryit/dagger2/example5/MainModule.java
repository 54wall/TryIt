package pri.weiqiang.tryit.dagger2.example5;

import android.content.Context;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;


@Module
public class MainModule {

    private static final String TAG = MainModule.class.getSimpleName();

    private Context context;

    @Inject
    public MainModule() {

    }

    public MainModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context providesContext() {
        return this.context;
    }

    @PersonWithContext
    @Provides
    public Person providesPersonWithContext(Context context) {
        return new Person(context);
    }

    @PersonWithName
    @Provides
    public Person providesPersonWithName() {
        return new Person("yxm");
    }
}