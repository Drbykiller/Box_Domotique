package fr.modibo.boxdomotique.View.Adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mikepenz.aboutlibraries.LibsBuilder;

import fr.modibo.boxdomotique.Controller.Fragment.AboutInfoFragment;
import fr.modibo.boxdomotique.R;

/**
 * Classe <b>ViewPagerAdapter</b> qui gère l'affichage des données
 * dans le widget TabLayout.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AboutInfoFragment();
            case 1:
                return new LibsBuilder()
                        .withFields(R.string.class.getFields())
                        .withLicenseShown(true)
                        .withVersionShown(true)
                        .withAutoDetect(true)
                        .supportFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.strAbout);
            case 1:
                return context.getResources().getString(R.string.strLibraries);
            default:
                return null;
        }
    }
}

