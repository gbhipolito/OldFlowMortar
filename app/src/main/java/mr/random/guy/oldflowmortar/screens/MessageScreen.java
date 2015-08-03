package mr.random.guy.oldflowmortar.screens;


import android.util.Log;

import flow.Layout;
import mortar.Blueprint;
import mr.random.guy.oldflowmortar.R;

@Layout(R.layout.layout_message)
public class MessageScreen implements Blueprint {

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
}