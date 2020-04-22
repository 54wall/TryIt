package pri.weiqiang.tryit.dagger2.example3;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context providesContext() {
        return context;
    }
}


