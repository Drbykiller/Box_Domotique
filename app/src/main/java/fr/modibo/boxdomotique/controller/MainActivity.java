package fr.modibo.boxdomotique.controller;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.modibo.boxdomotique.controller.Fragment.MainFragment;
import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.controller.Fragment.ScenarioFragment;
import fr.modibo.boxdomotique.controller.Fragment.SensorFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String MAIN_KEY = "MAIN_PARAMS";
    private Toolbar tb;
    private DrawerLayout dl;
    private NavigationView nav_view;
    private TextView nav_view_tvUser;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        // TOOLBAR
        tb = findViewById(R.id.tb);
        setSupportActionBar(tb);

        //DRAWERLAYOUT
        dl = findViewById(R.id.dl);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, dl, tb, R.string.nav_dw_open, R.string.nav_dw_close);
        dl.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //NAVIGATION VIEW
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);

        view = nav_view.getHeaderView(0);
        nav_view_tvUser = view.findViewById(R.id.nav_view_tvUser);
        nav_view_tvUser.setText(intent.getStringExtra(MAIN_KEY));


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, new MainFragment()).commit();
            nav_view.setCheckedItem(R.id.nav_home);
        }

//        NOT WORKING
//        else {
//
//            String key[] = {"home", "sensor", "scenario", "setting", "about"};
//
//            for (String aKey : key) {
//                String result = savedInstanceState.getString(aKey);
//
//                if (result != null && !result.isEmpty()) {
//                    tb.setTitle(result);
//                }
//            }
//
//        }
    }

    // TOOLBAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Toast.makeText(this, "En cours !!!!!!!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //NAVIGATION VIEW
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, new MainFragment()).commit();
                tb.setTitle(R.string.app_name);
                break;
            case R.id.nav_sensor:
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, new SensorFragment()).commit();
                tb.setTitle(R.string.nav_sensor);
                break;
            case R.id.nav_scenario:
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, new ScenarioFragment()).commit();
                tb.setTitle(R.string.nav_scenario);
                break;
            case R.id.nav_setting:
                //getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, new SensorFragment()).commit();
                //tb.setTitle(R.string.nav_setting);
                break;
            case R.id.nav_about:
                //getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, new SensorFragment()).commit();
                //tb.setTitle(R.string.nav_about);
                break;
        }

        dl.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (dl.isDrawerOpen(GravityCompat.START))
            dl.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();

    }

    // Bundle
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//
//        switch (nav_view.getCheckedItem().getItemId()) {
//            case R.id.nav_home:
//                outState.putString("home", getString(R.string.nav_home));
//                break;
//            case R.id.nav_sensor:
//                outState.putString("sensor", getString(R.string.nav_sensor));
//                break;
//            case R.id.nav_scenario:
//                outState.putString("scenario", getString(R.string.nav_scenario));
//                break;
//            case R.id.nav_setting:
//                outState.putString("setting", getString(R.string.nav_setting));
//                break;
//            case R.id.nav_about:
//                outState.putString("about", getString(R.string.nav_about));
//                break;
//        }
//
//        super.onSaveInstanceState(outState);
}