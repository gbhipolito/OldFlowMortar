package mr.random.guy.oldflowmortar.screens;


import android.util.Log;

import flow.HasParent;
import flow.Layout;
import mortar.Blueprint;
import mr.random.guy.oldflowmortar.R;
import mr.random.guy.oldflowmortar.support.Screen;

@Layout(R.layout.layout_message)
public class MessageScreen extends Screen implements HasParent<ButtonScreen> {

    public MessageScreen() {
        Log.e("asdf", "MessageScreen constructor: " + this);
    }

    @Override
    public String getMortarScopeName() {
        Log.e("asdf", "MessageScreen getMortarScopeName");
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        Log.e("asdf", "MessageScreen getDaggerModule");
        return null;
    }

    @Override
    public ButtonScreen getParent() {
        Log.e("asdf", "MessageScreen getParent");
        return new ButtonScreen();
    }
}