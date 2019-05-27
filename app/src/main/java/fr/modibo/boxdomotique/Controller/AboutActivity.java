package fr.modibo.boxdomotique.Controller;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.View.Adapter.ViewPagerAdapter;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar tb_about = findViewById(R.id.tb_about);
        setSupportActionBar(tb_about);

        getSupportActionBar().setTitle(getString(R.string.nav_about));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);

        Context context = getApplicationContext();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), context);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
