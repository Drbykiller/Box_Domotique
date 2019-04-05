package fr.modibo.boxdomotique.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import fr.modibo.boxdomotique.R;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String MAIN_KEY = "MAIN_PARAMS";
    private Toolbar tb;
    private DrawerLayout dl;
    private NavigationView nav_view;
    private TextView tvUser;

    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvUser = findViewById(R.id.tvUser);

        /*Intent intent = getIntent();
        user = intent.getStringExtra(MAIN_KEY);*/

        // TOOLBAR
        tb = findViewById(R.id.tb);
        setSupportActionBar(tb);

        //DRAWERLAYOUT
        dl = findViewById(R.id.dl);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, dl, tb, R.string.nav_dw_close, R.string.nav_dw_open);
        dl.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //NAVIGATION VIEW
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);

        /*if (user != null) {
            tvUser.setText(user);
        } else
            tvUser.setText("NULL"); // NULL POINTER EXCEPTION*/

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
        return false;
    }

    @Override
    public void onBackPressed() {

        if (dl.isDrawerOpen(GravityCompat.START))
            dl.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();

    }
}
