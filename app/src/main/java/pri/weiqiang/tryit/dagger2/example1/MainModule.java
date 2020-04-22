package pri.weiqiang.tryit.dagger2.example1;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    @Singleton
    @Provides
    Person providesPerson() {
        System.out.println("a person example1 created from MainModule");
        return new Person();
    }
}