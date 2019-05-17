package fr.modibo.boxdomotique.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import fr.modibo.boxdomotique.Controller.Fragment.MainFragment;
import fr.modibo.boxdomotique.Controller.Fragment.ScenarioFragment;
import fr.modibo.boxdomotique.Controller.Fragment.SensorFragment;
import fr.modibo.boxdomotique.R;

/**
 * Classe <b>MainActivity</b> qui gère l'affichage des differents fragments.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorFragment.errorFromDeviceThread, MainFragment.newFragmentFromHomeAdapter {

    private Toolbar tb;
    private DrawerLayout dl;
    private NavigationView nav_view;
    private CollapsingToolbarLayout collapsing;
    private AppBarLayout appbar;
    public static final String MAIN_KEY = "MAIN_PARAMS";
    private static final String[] KEY = {"home", "sensor", "scenario", "setting", "about"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //Collapsing
        collapsing = findViewById(R.id.collapsing);
        collapsing.setTitle(getResources().getString(R.string.app_name));

        appbar = findViewById(R.id.appbar);
        appbar.setExpanded(false);

        View view = nav_view.getHeaderView(0);
        TextView nav_view_tvUser = view.findViewById(R.id.nav_view_tvUser);
        Intent intent = getIntent();
        nav_view_tvUser.setText(intent.getStringExtra(MAIN_KEY));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, new MainFragment()).commit();
            nav_view.setCheckedItem(R.id.nav_home);
        } else {
            for (String aKey : KEY) {
                String result = savedInstanceState.getString(aKey);
                if (result != null && !result.isEmpty())
                    collapsing.setTitle(result);
            }
        }
    }

    /**
     * Permet de sauvegarder le nom de l'onglet que l'utilisateur a cliqué
     * dans le Drawer et qui sera restoré dans la méthode onCreate
     *
     * @param outState Passe en paramètre le Bundle.
     * @see fr.modibo.boxdomotique.Controller.MainActivity#onCreate(Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        switch (nav_view.getCheckedItem().getItemId()) {
            case R.id.nav_home:
                outState.putString(KEY[0], getString(R.string.app_name));
                break;
            case R.id.nav_sensor:
                outState.putString(KEY[1], getString(R.string.nav_sensor));
                break;
            case R.id.nav_scenario:
                outState.putString(KEY[2], getString(R.string.nav_scenario));
                break;
            case R.id.nav_setting:
                outState.putString(KEY[3], getString(R.string.nav_setting));
                break;
            case R.id.nav_about:
                outState.putString(KEY[4], getString(R.string.nav_about));
                break;
        }
        super.onSaveInstanceState(outState);
    }


    /* ************************
        NAVIGATION VIEW
    *************************/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, new MainFragment(this)).commit();
                collapsing.setTitle(getResources().getString(R.string.app_name));
                break;
            case R.id.nav_sensor:
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, new SensorFragment(this)).commit();
                collapsing.setTitle(getResources().getString(R.string.nav_sensor));
                appbar.setExpanded(true, true);
                break;
            case R.id.nav_scenario:
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, new ScenarioFragment()).commit();
                collapsing.setTitle(getResources().getString(R.string.nav_scenario));
                appbar.setExpanded(true);
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


     /* ************************
        INTERFACE + IMPLEMENTATION
     *************************/

    /**
     * Méthode implémenté de la classe <b>SensorFragment</b> qui permet d'obtenir,
     * si il y a une erreur, une erreur lors de la recuperation de la
     * liste des capteurs/actionneurs et de l'afficher a l'utilisateur.
     *
     * @param error L'Erreur passe en paramètre ce qui permet de le récuperer.
     * @see fr.modibo.boxdomotique.Controller.Fragment.SensorFragment#errorFromDeviceThread
     */
    @Override
    public void error(String error) {
        Snackbar snackbar = Snackbar.make(dl.getRootView(), getString(R.string.errorServer) + "\nCode : " + error, Snackbar.LENGTH_LONG).setDuration(5000);
        View snackbarView = snackbar.getView();
        TextView tv = snackbarView.findViewById(R.id.snackbar_text);
        tv.setMaxLines(5);
        snackbar.show();
    }

    @Override
    public void loadFragmentFromHomeAdapter(String title) {
        switch (title) {
            case "Gestion des Capteurs":
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, new SensorFragment(this)).commit();
                nav_view.setCheckedItem(R.id.nav_sensor);
                collapsing.setTitle(getResources().getString(R.string.nav_sensor));
                break;
            case "Gestion des Scénarios":
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, new ScenarioFragment()).commit();
                nav_view.setCheckedItem(R.id.nav_scenario);
                collapsing.setTitle(getResources().getString(R.string.nav_scenario));
                break;
        }
    }
}