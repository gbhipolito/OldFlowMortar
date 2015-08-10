package mr.random.guy.oldflowmortar.support;

import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;

import mortar.Blueprint;

public abstract class Screen implements Blueprint {

    private SparseArray<Parcelable> viewState = new SparseArray<>();

    public void saveViewState(View view) {
        viewState.clear();
        view.saveHierarchyState(viewState);
    }

    public void restoreViewState(View view) {
        view.restoreHierarchyState(viewState);
    }

} // end Screen