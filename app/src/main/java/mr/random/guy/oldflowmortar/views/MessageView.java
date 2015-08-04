package mr.random.guy.oldflowmortar.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MessageView extends TextView {
    public MessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("asdf", "MessageView constructor: " + this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e("asdf", "MessageView fininflate");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("asdf", "MessageView attached");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("asdf", "MessageView detached");
    }
} // end MessageView