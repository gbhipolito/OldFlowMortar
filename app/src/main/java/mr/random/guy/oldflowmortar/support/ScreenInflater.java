package mr.random.guy.oldflowmortar.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import flow.Layout;

public class ScreenInflater {
    private ScreenInflater(){}

    /** Create an instance of the view specified in a {@link Layout} annotation. */
    public static android.view.View createView(Context context, Object screen, ViewGroup parent) {
        return createView(context, screen.getClass(), parent);
    }

    /** Create an instance of the view specified in a {@link Layout} annotation. */
    public static android.view.View createView(Context context, Class<?> screenType, ViewGroup parent) {
        Layout screen = screenType.getAnnotation(Layout.class);
        if (screen == null) {
            throw new IllegalArgumentException(
                    String.format("@%s annotation not found on class %s", Layout.class.getSimpleName(),
                            screenType.getName()));
        }

        int layout = screen.value();
        return inflateLayout(context, layout, parent);
    }

    private static android.view.View inflateLayout(Context context, int layoutId, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(layoutId, parent, false);
    }
} // end ScreenInflater