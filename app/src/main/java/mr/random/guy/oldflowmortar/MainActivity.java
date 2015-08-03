package mr.random.guy.oldflowmortar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import flow.Flow;
import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarActivityScope;
import mortar.MortarScope;
import mr.random.guy.oldflowmortar.screens.ButtonScreen;
import mr.random.guy.oldflowmortar.support.CoordinatorHolder;
import mr.random.guy.oldflowmortar.support.FlowCoordinator;


public class MainActivity extends Activity {

    private MortarActivityScope activityScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("asdf", "activity oncreate");
        setContentView(R.layout.activity_main);
        ViewGroup containerView = (ViewGroup)findViewById(R.id.container);

        MortarScope parentScope = Mortar.getScope(getApplicationContext());
        Blueprint firstScreen = new ButtonScreen();
        activityScope = Mortar.requireActivityScope(parentScope, firstScreen);
        activityScope.onCreate(savedInstanceState);

//        Mortar.inject(this, this);

        CoordinatorHolder coordinatorHolder = (CoordinatorHolder)getApplication();
        coordinatorHolder.setFlowCoordinator(FlowCoordinator.create(containerView, firstScreen, coordinatorHolder, (savedInstanceState != null)));

    } // end onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        activityScope.onSaveInstanceState(outState);
    }

    @Override
    public Object getSystemService(String name) {
        Log.e("asdf", "activity getsystemservice: " + name);
        if(Mortar.isScopeSystemService(name)) {
            Log.e("asdf", "activity get activityscope service: " + name);
            return activityScope;
        }

        if(name.equals(FlowCoordinator.FLOW_SERVICE)) {
            Log.e("asdf", "activity get flow");
            return getApplication().getSystemService(name);
        }

        return super.getSystemService(name);
    }

    @Override
    public void onBackPressed() {
        if(((Flow)getSystemService(FlowCoordinator.FLOW_SERVICE)).goBack()) {
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("asdf", "activity ondestroy");
        if (isFinishing() && activityScope != null) {
            Log.e("asdf", "activity destroy activity scope");
            MortarScope parentScope = Mortar.getScope(getApplication());
            parentScope.destroyChild(activityScope);
            activityScope = null;
        }
    }
} // end MainActivity