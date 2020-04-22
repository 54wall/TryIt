package pri.weiqiang.tryit.dagger2.example3;

import dagger.Component;

@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(DaggerE3Activity daggerE3Activity);
}
