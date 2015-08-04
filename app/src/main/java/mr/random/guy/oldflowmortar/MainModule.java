package mr.random.guy.oldflowmortar;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mr.random.guy.oldflowmortar.MainActivity;
import mr.random.guy.oldflowmortar.MainApp;
import mr.random.guy.oldflowmortar.support.FlowCoordinator;
import mr.random.guy.oldflowmortar.views.ButtonView;

@Module(injects = {
            MainApp.class
//        ButtonView.class
        },
        library = true
)
public final class MainModule {

    private final MainApp app;

    public MainModule(MainApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return this.app;
    }

}