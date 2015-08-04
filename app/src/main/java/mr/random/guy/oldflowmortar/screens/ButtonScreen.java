package mr.random.guy.oldflowmortar.screens;

import android.util.Log;
import android.view.View;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import flow.Flow;
import flow.Layout;
import mortar.Blueprint;
import mortar.ViewPresenter;
import mr.random.guy.oldflowmortar.R;
import mr.random.guy.oldflowmortar.support.FlowCoordinator;
import mr.random.guy.oldflowmortar.MainModule;
import mr.random.guy.oldflowmortar.views.ButtonView;

@Layout(R.layout.layout_button)
public class ButtonScreen implements Blueprint {

    public ButtonScreen() {
        Log.e("asdf", "ButtonScreen constructor: " + this);
    }

    @Override
    public String getMortarScopeName() {
        Log.e("asdf", "ButtonScreen getMortarScopeName");
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        Log.e("asdf", "ButtonScreen getDaggerModule");
        return new ButtonModule();
    }

    @Module(injects = {
                ButtonView.class
            },
            addsTo = MainModule.class
    )
    public final class ButtonModule {
        public ButtonModule() {
            Log.e("asdf", "ButtonModule constructor: " + this);
        }

        @Provides
        @Singleton
        ButtonPresenter provideButtonPresenter() {
            Log.e("asdf", "ButtonModule provideButtonPresenter");
            return new ButtonPresenter();
        }
    }

@Singleton
public class ButtonPresenter extends ViewPresenter {
    public ButtonPresenter() {
        Log.e("asdf", "ButtonPresenter constructor: " + this);
    }

    public void onButtonClicked() {
        ((Flow)((View)getView()).getContext().getSystemService(FlowCoordinator.FLOW_SERVICE)).goTo(new MessageScreen());
//            ((Flow)((View)getView()).getContext().getSystemService(FlowCoordinator.FLOW_SERVICE)).replaceTo(new MessageScreen());
    }
}
} // end ButtonScreen