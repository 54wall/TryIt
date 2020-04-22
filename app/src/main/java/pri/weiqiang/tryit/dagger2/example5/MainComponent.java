package pri.weiqiang.tryit.dagger2.example5;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent {

    void inject(DaggerE5Activity daggerE5Activity);
}
