package mr.random.guy.oldflowmortar.support;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Lists;

import java.util.List;

import flow.Backstack;
import flow.Flow;
import mortar.Mortar;
import mortar.MortarScope;

public class FlowCoordinator implements Flow.Listener {
    public static final String FLOW_SERVICE = "FLOW_SERVICE";

    private final Context activityContext;
    private final Flow flow;
    private ViewGroup containerView;

    private FlowCoordinator(ViewGroup containerView, Screen firstScreen, Context context) {
        Log.e("asdf", "FlowCoordinator constructor");
        Backstack backstack = Backstack.fromUpChain(firstScreen);
        this.flow = new Flow(backstack, this);
        this.containerView = containerView;
        this.activityContext = context;
//        this.flow.goTo(firstScreen);
        showScreen(firstScreen, null, null);
    }

    public static FlowCoordinator create(ViewGroup containerView, Screen firstScreen, CoordinatorHolder coordinatorHolder, boolean isConfigChanging, Context activityContext) {
        Log.e("asdf", "FlowCoordinator create");
        FlowCoordinator coordinator = coordinatorHolder.getFlowCoordinator();
        if(isConfigChanging && coordinator != null) {
            Log.e("asdf", "FlowCoordinator configchange reuse create");
            // if just changing config (e.g. rotation), just update containerView
            coordinator = coordinator.withContainerView(containerView);
            coordinator.showScreen((Screen)coordinator.flow.getBackstack().current().getScreen(), null, null);

            return coordinator.withContainerView(containerView);
        }
        else {
            Log.e("asdf", "FlowCoordinator new create");
            return new FlowCoordinator(containerView, firstScreen, activityContext);
        }
    }

    /**
     * overriden from Flow.Listener
     */
    @Override
    public void go(Backstack backstack, Flow.Direction direction) {
        Log.e("asdf", "FlowCoordinator go");
        Screen newScreen = (Screen)backstack.current().getScreen();

        Screen oldScreen = null;
        if (direction == Flow.Direction.FORWARD && backstack.size() > 1) {
            List<Backstack.Entry> entries = Lists.newArrayList(backstack.reverseIterator());
            Backstack.Entry oldEntry = entries.get(entries.size() - 2);
            //noinspection unchecked
            oldScreen = (Screen) oldEntry.getScreen();
            Log.e("asdf", "FlowCoordinator go oldScreen: " + oldScreen);
        }

        showScreen(newScreen, oldScreen, direction);
    }

    private void showScreen(Screen newScreen, Screen oldScreen, final Flow.Direction direction) {
        Log.e("asdf", "FlowCoordinator showScreen| new: " + newScreen + " old: " + oldScreen + " dir: " + direction);
//        // Cancel previous transition and set end values
//        if (screenTransition != null) {
//            screenTransition.end();
//        }

        final View oldChild = getCurrentChild(containerView);
        Log.e("asdf", "FlowCoordinator showScreen oldChild: " + oldChild);

        if(destroyOldScope(newScreen, oldChild)) {
//            storeViewState(oldChild, oldScreen);
            final View newChild = createNewChildView(newScreen);

//            Transitions.Animators transitions = null;
//            if (oldChild != null) {
//                switch (direction) {
//                    case FORWARD:
//                        // Load animations from Transition annotations, store them into backstack and set them to views
//                        storeTransitions(oldScreen, newScreen);
//                        transitions = Transitions.forward(activityContext, newScreen);
//                        break;
//                    case BACKWARD:
//                        if (newScreen instanceof TransitionScreen) {
//                            // Try to load animations from a screen and set them
//                            int[] transitionIds = ((TransitionScreen) newScreen).getTransitions();
//                            transitions = Transitions.backward(activityContext, transitionIds);
//                        }
//                        break;
//                    case REPLACE:
//                        // no animations
//                        break;
//                }
//            }

            if(oldChild != null) {
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
            } // end if(oldChild != null)

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
        } // end if(destroyOldScope(newScreen, oldChild))

    } // end showScreen

    protected View getCurrentChild(final ViewGroup containerView) {
        if (containerView.getChildCount() > 0) {
            return containerView.getChildAt(0);
        } else {
            return null;
        }
    }

    /**
     * Destroys old child scope if it was different than the new one. Returns true
     * if successful
     */
    protected boolean destroyOldScope(Screen newScreen, View oldChild) {
        MortarScope myScope = Mortar.getScope(activityContext);
        if (oldChild != null) {
            MortarScope oldChildScope = Mortar.getScope(oldChild.getContext());
            Log.e("asdf", "FlowCoordinator oldChildScope: " + oldChildScope.getName() + " newScreenScope: " + newScreen.getMortarScopeName() + " " + newScreen);
            if (oldChildScope.getName().equals(newScreen.getMortarScopeName())) {
                Log.e("asdf", "FlowCoordinator destroyOldScope false");
                return false;
            }
            myScope.destroyChild(oldChildScope);
        }
        Log.e("asdf", "FlowCoordinator destroyOldScope true");
        return true;
    }

    /**
     * return this same instance of FlowCoordinator applying the given containerView
     */
    public FlowCoordinator withContainerView(ViewGroup containerView) {
        this.containerView = containerView;
        return this;
    }

    /**
     * Creates and inflates a new child View from a given screen
     */
    protected View createNewChildView(Screen newScreen) {
        Log.e("asdf", "FlowCoordinator createNewChildView");
        MortarScope myScope = Mortar.getScope(activityContext);
        MortarScope newChildScope = myScope.requireChild(newScreen);
        Context childContext = newChildScope.createContext(activityContext);
        return ScreenInflater.createView(childContext, newScreen, containerView);
    }

    public Flow getFlow() {
        return flow;
    }
} // end FlowCoordinator