package pri.weiqiang.tryit.dagger2.example4;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent {

    void inject(DaggerE4Activity daggerE4Activity);
}
