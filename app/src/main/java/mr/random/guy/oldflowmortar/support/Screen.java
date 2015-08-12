package mr.random.guy.oldflowmortar.support;

import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;

import mortar.Blueprint;

public abstract class Screen implements Blueprint {

    private int[] transitions;
    private SparseArray<Parcelable> viewState = new SparseArray<>();

    public final void saveViewState(View view) {
        viewState.clear();
        view.saveHierarchyState(viewState);
    }

    public final void restoreViewState(View view) {
        view.restoreHierarchyState(viewState);
    }

    public final void setTransitions(int[] transitions) {
        this.transitions = transitions;
    }

    public final int[] getTransitions() {
        return transitions;
    }

} // end Screen