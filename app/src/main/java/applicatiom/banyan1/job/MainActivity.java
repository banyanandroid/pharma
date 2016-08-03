package applicatiom.banyan1.job;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import gobal.SessionManagement;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {

            case R.id.action_home:
                Intent inte = new Intent(getApplicationContext(), Activity_Select_Place.class);
                startActivity(inte);
                return true;
            case R.id.action_cancel:
                Intent intent = new Intent(Intent.ACTION_MAIN);

                intent.addCategory(Intent.CATEGORY_HOME);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

                finish();

                System.exit(0);
                return true;
        }
        return true;
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new FragmentHome();
                title = getString(R.string.title_hotjobs);
                break;
            case 1:
                fragment = new FragmentLatestNew();
                title = getString(R.string.title_latestnew);
                break;
            case 2:
                fragment = new FragmentProfile();
                title = getString(R.string.title_training);
                break;
            case 3:
                title = getString(R.string.title_alrdyapplied);

                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String username = pref.getString("username", "");

                Log.d(TAG, username);


                if (username.equals("")) {
                    //Toast.makeText(MainActivity.this, "if", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(MainActivity.this, Activity_login.class);
                    startActivity(in);


                } else {
                    //Toast.makeText(MainActivity.this, "else", Toast.LENGTH_LONG).show();
                    fragment = new FragmentAppliedJobs();
                }

                break;

            case 4:
                SharedPreferences pref1 = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                pref1.edit().clear().commit();
                Intent in = new Intent(MainActivity.this, Activity_Select_Place.class);
                startActivity(in);

                break;
            case 5:
                // Method to share either text or URL.
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "PharamaJobs-India");
                share.putExtra(Intent.EXTRA_TEXT, "Hi friends i have found new Job Search App to build our career: https://play.google.com/store/apps/details?id=applicatiom.banyan1.job&hl=en");

                startActivity(Intent.createChooser(share, "Share link!"));

                break;
            case 6:
                //PostJob
                Intent i = new Intent(MainActivity.this, Activity_Postjob.class);
                startActivity(i);

                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    public void onBackPressed() {

        super.onBackPressed();


        Intent it = new Intent(getApplicationContext(), Activity_JobOpp_Grid.class);
        startActivity(it);
    }


}