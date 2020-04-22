package pri.weiqiang.tryit.dagger2.example1;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent {

    void inject(DaggerE1Activity daggerE1Activity);
}
