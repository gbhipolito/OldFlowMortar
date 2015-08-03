package mr.random.guy.oldflowmortar.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import javax.inject.Inject;

import mortar.Mortar;
import mr.random.guy.oldflowmortar.screens.ButtonScreen;

public class ButtonView extends Button {

    @Inject
    ButtonScreen.ButtonPresenter presenter;

    public ButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("asdf", "ButtonView constructor");
        Mortar.inject(context, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e("asdf", "ButtonView fininflate");
        presenter.takeView(this);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onButtonClicked();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("asdf", "ButtonView attached");
        presenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("asdf", "ButtonView detached");
        presenter.dropView(this);
    }

} // end ButtonView