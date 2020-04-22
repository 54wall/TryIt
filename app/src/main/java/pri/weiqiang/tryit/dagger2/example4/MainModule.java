package pri.weiqiang.tryit.dagger2.example4;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module
public class MainModule {

    private static final String TAG = MainModule.class.getSimpleName();

    private Context context;

    public MainModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context providesContext() {
        return this.context;
    }

    @Named("context")
    @Provides
    public Person providesPersonWithContext(Context context) {
        return new Person(context);
    }

    @Named("string")
    @Provides
    public Person providesPersonWithName() {
        return new Person("yxm");
    }
}