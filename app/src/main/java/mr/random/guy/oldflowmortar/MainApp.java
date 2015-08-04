package mr.random.guy.oldflowmortar;

import android.app.Application;
import android.util.Log;

import dagger.ObjectGraph;
import mortar.Mortar;
import mortar.MortarScope;
import mr.random.guy.oldflowmortar.support.CoordinatorHolder;
import mr.random.guy.oldflowmortar.support.FlowCoordinator;

public class MainApp extends Application implements CoordinatorHolder {
    private FlowCoordinator flowCoordinator;
    private MortarScope applicationScope;

    @Override
    public Object getSystemService(String name) {
        Log.e("asdf", "app getsystemservice: " + name);
        // if asking for mortar
        if(Mortar.isScopeSystemService(name)) {
            Log.e("asdf", "app get applicationscope service: " + name);
            if(applicationScope == null) {
                Log.e("asdf", "app create applicationscope: " + name);
                ObjectGraph objectGraph = ObjectGraph.create(new MainModule(this));
                objectGraph.inject(this);
                applicationScope = Mortar.createRootScope(BuildConfig.DEBUG, objectGraph);
            }
            Log.e("asdf", "app return service: " + applicationScope);
            return applicationScope;
        }

        // if asking for flow
        if(name.equals(FlowCoordinator.FLOW_SERVICE)) {
            Log.e("asdf", "app get flow");
            if(flowCoordinator != null) {
                Log.e("asdf", "app flow not null");
                return flowCoordinator.getFlow();
            }
        }

        return super.getSystemService(name);
    } // end getSystemService

    @Override
    public FlowCoordinator getFlowCoordinator() {
        return flowCoordinator;
    }

    @Override
    public void setFlowCoordinator(FlowCoordinator flowCoordinator) {
        this.flowCoordinator = flowCoordinator;
    }
} // end MainApp