package mr.random.guy.oldflowmortar.support;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

import flow.Backstack;
import flow.Flow;
import flow.Layouts;
import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarScope;
import mr.random.guy.oldflowmortar.screens.ButtonScreen;

public class FlowCoordinator implements Flow.Listener {
    public static final String FLOW_SERVICE = "FLOW_SERVICE";

    private final Flow flow;
    private ViewGroup containerView;

    private FlowCoordinator(ViewGroup containerView, Blueprint firstScreen) {
        Log.e("asdf", "FlowCoordinator constructor");
        Backstack backstack = Backstack.fromUpChain(firstScreen);
        this.flow = new Flow(backstack, this);
        this.containerView = containerView;

//        this.flow.goTo(firstScreen);
        showScreen(firstScreen, null, null);
    }

    public static FlowCoordinator create(ViewGroup containerView, Blueprint firstScreen, CoordinatorHolder coordinatorHolder, boolean isConfigChanging) {
        Log.e("asdf", "FlowCoordinator create");
        FlowCoordinator coordinator = coordinatorHolder.getFlowCoordinator();
        if(isConfigChanging && coordinator != null) {
            Log.e("asdf", "FlowCoordinator configchange create");
            // if just changing config (e.g. rotation), just update containerView
            coordinator = coordinator.withContainerView(containerView);
            coordinator.showScreen((Blueprint)coordinator.flow.getBackstack().current().getScreen(), null, null);

            return coordinator.withContainerView(containerView);
        }
        else {
            Log.e("asdf", "FlowCoordinator new create");
            return new FlowCoordinator(containerView, firstScreen);
        }
    }

    /**
     * overriden from Flow.Listener
     */
    @Override
    public void go(Backstack backstack, Flow.Direction direction) {
        Log.e("asdf", "FlowCoordinator go");
        Blueprint newScreen = (Blueprint) backstack.current().getScreen();

        Blueprint oldScreen = null;
        if (direction == Flow.Direction.FORWARD && backstack.size() > 1) {
            List<Backstack.Entry> entries = Lists.newArrayList(backstack.reverseIterator());
            Backstack.Entry oldEntry = entries.get(entries.size() - 2);
            //noinspection unchecked
            oldScreen = (Blueprint) oldEntry.getScreen();
        }

        showScreen(newScreen, oldScreen, direction);
    }

    private void showScreen(Blueprint newScreen, Blueprint oldScreen, final Flow.Direction direction) {
        Log.e("asdf", "FlowCoordinator showScreen");
//        // Cancel previous transition and set end values
//        if (screenTransition != null) {
//            screenTransition.end();
//        }

        final View oldChild = getCurrentChild(containerView);

//        if (destroyOldScope(newScreen, oldChild)) {
//            storeViewState(oldChild, oldScreen);
            final View newChild = createNewChildView(newScreen);

//            Transitions.Animators transitions = null;
//            if (oldChild != null) {
//                switch (direction) {
//                    case FORWARD:
//                        // Load animations from Transition annotations, store them into backstack and set them to views
//                        storeTransitions(oldScreen, newScreen);
//                        transitions = Transitions.forward(context, newScreen);
//                        break;
//                    case BACKWARD:
//                        if (newScreen instanceof TransitionScreen) {
//                            // Try to load animations from a screen and set them
//                            int[] transitionIds = ((TransitionScreen) newScreen).getTransitions();
//                            transitions = Transitions.backward(context, transitionIds);
//                        }
//                        break;
//                    case REPLACE:
//                        // no animations
//                        break;
//                }
//            }

//            if (oldChild != null) {
//                // Settings animator for each view and removing the old view
//                // after animation ends
//                if (transitions != null) {
//                    transitions.out.setTarget(oldChild);
//                    transitions.in.setTarget(newChild);
//                    screenTransition = new AnimatorSet();
//                    screenTransition.playTogether(transitions.out, transitions.in);
//                    screenTransition.addListener(new SimpleAnimatorListener() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            containerView.removeView(oldChild);
//                        }
//                    });
//                } else {
                    // remove view immediately if no transitions to run
                    containerView.removeView(oldChild);
//                }
//            }

        containerView.addView(newChild, 0);

//            if (screenTransition != null) {
//                screenTransition.start();
//            }

//            // Makes the new view z-index higher than the old view
//            // for transitions forward to make feel more natural
//            if (direction == Flow.Direction.FORWARD) {
//                containerView.post(new Runnable() {
//                    @Override public void run() {
//                        containerView.bringChildToFront(newChild);
//                        containerView.requestLayout();
//                        containerView.invalidate();
//                    }
//                });
//            }
//        }

    }

    protected View getCurrentChild(final ViewGroup containerView) {
        if (containerView.getChildCount() > 0) {
            return containerView.getChildAt(0);
        } else {
            return null;
        }
    }

    /**
     * return this same instance of FlowCoordinator applying the given containerView
     */
    public FlowCoordinator withContainerView(ViewGroup containerView) {
        this.containerView = containerView;
        return this;
    }

    protected View createNewChildView(Blueprint screen) {
//        MortarScope myScope = Mortar.getScope(context);
//        MortarScope newChildScope = myScope.requireChild(screen);
//        Context childContext = newChildScope.createContext(context);
        View newChild = ScreenInflater.createView(containerView.getContext(), screen, containerView);
//        newChild.setId(viewId);
        return newChild;
    }

    public Flow getFlow() {
        return flow;
    }
} // end FlowCoordinator