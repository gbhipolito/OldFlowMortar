package mr.random.guy.oldflowmortar.screens;

import android.util.Log;
import android.view.View;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import flow.Flow;
import mortar.Blueprint;
import mortar.ViewPresenter;
import mr.random.guy.oldflowmortar.MainModule;
import mr.random.guy.oldflowmortar.support.FlowCoordinator;
import mr.random.guy.oldflowmortar.support.Screen;
import mr.random.guy.oldflowmortar.views.ActivityView;

public class ActivityScreen extends Screen {
    @Override
    public String getMortarScopeName() {
        Log.e("asdf", "ActivityScreen getMortarScopeName");
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        Log.e("asdf", "ActivityScreen getDaggerModule");
        return new ActivityModule();
    }

    @Module(injects = {
            ActivityView.class
    },
            addsTo = MainModule.class
    )
    public final class ActivityModule {
        public ActivityModule() {
            Log.e("asdf", "ActivityModule constructor: " + this);
        }

    }

    @Singleton
    public class ActivityPresenter extends ViewPresenter {
        public ActivityPresenter() {
            Log.e("asdf", "ActivityPresenter constructor: " + this);
        }

    }
} // end ActivityScreen