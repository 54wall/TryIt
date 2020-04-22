package pri.weiqiang.tryit.dagger2.example6;

import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
public abstract class ActivityComponent {

    public abstract void inject(DaggerE6Activity daggerE6Activity);
}
