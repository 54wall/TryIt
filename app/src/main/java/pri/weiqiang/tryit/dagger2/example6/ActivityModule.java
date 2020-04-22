package pri.weiqiang.tryit.dagger2.example6;

import android.content.Context;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {

    @ActivityScope
    @PersonWithContext
    @Provides
    Person providesPersonWithContext(Context context) {
        return new Person(context);
    }

    @ActivityScope
    @PersonWithName
    @Provides
    Person providesPersonWithString() {
        return new Person("yxm");
    }
}
