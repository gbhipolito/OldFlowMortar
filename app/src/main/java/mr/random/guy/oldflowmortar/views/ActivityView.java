package mr.random.guy.oldflowmortar.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class ActivityView extends RelativeLayout {
    public ActivityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("asdf", "ActivityView constructor: " + this);
    }
}