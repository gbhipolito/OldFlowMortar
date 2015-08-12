package mr.random.guy.oldflowmortar.screens;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import flow.Flow;
import flow.Layout;
import mortar.Blueprint;
import mortar.MortarScope;
import mortar.ViewPresenter;
import mr.random.guy.oldflowmortar.R;
import mr.random.guy.oldflowmortar.support.FlowCoordinator;
import mr.random.guy.oldflowmortar.MainModule;
import mr.random.guy.oldflowmortar.support.Screen;
import mr.random.guy.oldflowmortar.support.Transition;
import mr.random.guy.oldflowmortar.views.ButtonView;

@Transition({R.animator.scale_fade_in, R.animator.scale_fade_out, R.animator.scale_fade_in, R.animator.scale_fade_out})
@Layout(R.layout.layout_button)
public class ButtonScreen extends Screen {

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
    } // end ButtonModule

    @Singleton
    public class ButtonPresenter extends ViewPresenter<ButtonView> {
        public ButtonPresenter() {
            Log.e("asdf", "ButtonPresenter constructor: " + this);
        }

        public void onButtonClicked() {
            FlowCoordinator.getFlow(getView()).goTo(new MessageScreen());
        }

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            Log.e("asdf", "ButtonPresenter onEnterScope");
            saveViewState(getView());
        }

        @Override
        protected void onExitScope() {
            super.onExitScope();
            Log.e("asdf", "ButtonPresenter onExitScope");
            restoreViewState(getView());
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            Log.e("asdf", "ButtonPresenter onLoad");
        }

        @Override
        protected void onSave(Bundle outState) {
            super.onSave(outState);
            Log.e("asdf", "ButtonPresenter onSave");
        }
    } // end ButtonPresenter
} // end ButtonScreen