package pri.weiqiang.tryit.dagger2.example6;

import android.content.Context;

import dagger.Component;

@ApplicationScope
@Component(modules = {AppModule.class})
public interface AppComponent {

    // 向下层提供Context
    Context getContext();

}
