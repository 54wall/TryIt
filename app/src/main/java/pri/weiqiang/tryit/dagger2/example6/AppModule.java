package pri.weiqiang.tryit.dagger2.example6;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @ApplicationScope
    @Provides
    public Context providesContext() {
        return context;
    }
}


