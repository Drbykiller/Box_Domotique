package fr.modibo.boxdomotique.Controller.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.balysv.materialripple.MaterialRippleLayout;

import fr.modibo.boxdomotique.R;

/**
 * Classe <b>AboutInfoFragment</b> qui affiche les différents informations
 * concernant le projet.
 */
public class AboutInfoFragment extends Fragment {

    public AboutInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about_info, container, false);

        MaterialRippleLayout fai_source = v.findViewById(R.id.fai_source);

        fai_source.setOnClickListener(v1 -> {
            Uri uri = Uri.parse("https://github.com/Drbykiller/Box_Domotique");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        return v;
    }
}
