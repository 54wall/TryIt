package pri.weiqiang.tryit.dagger2.example3;

import android.content.Context;

import dagger.Component;

@Component(modules = {AppModule.class})
public interface AppComponent {
    // 向下层提供Context
    Context getContext();
}
