package pri.weiqiang.tryit.dagger2.example3;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @Provides
    Person providePerson(Context context) {
        return new Person(context);
    }
}
