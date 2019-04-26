package fr.modibo.boxdomotique.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import fr.modibo.boxdomotique.Model.Thread.LoginThread;
import fr.modibo.boxdomotique.Model.UrlServer;
import fr.modibo.boxdomotique.R;

/**
 * Classe <b>LoginActivity</b> qui gère l'affichage de l'ecran de connexion
 * et la connexion au serveur.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginThread.loginSuccessful {

    private TextInputEditText etServer, etUser, etPassword;
    private TextInputLayout til_Server, til_User, til_Password;
    private CheckBox checkSave;
    private FragmentManager fragmentManager;

    // Les keys pour la Sauvegarde des identifiants.
    private final String SHARED_PREF = "SAVE_DATA";
    private final String KEY_IP_ADDRESS = "IP";
    private final String KEY_USER = "USER";
    private final String KEY_PASSWORD = "PASSWORD";
    private final String KEY_CHECK = "CHECK";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etServer = findViewById(R.id.etServer);
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        til_Server = findViewById(R.id.til_Server);
        til_User = findViewById(R.id.til_User);
        til_Password = findViewById(R.id.til_Password);
        checkSave = findViewById(R.id.checkSave);

        Button btConnection = findViewById(R.id.btConnection);
        btConnection.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();

        loadUpdateID();
    }

    @Override
    public void onClick(View v) {
        String server = etServer.getText().toString();
        String user = etUser.getText().toString();
        String password = etPassword.getText().toString();

        String result = check_if_the_fields_are_correct(server, user, password);

        if (result.equalsIgnoreCase("error"))
            return;

        if (checkSave.isChecked())
            saveID();

        // Défini l'adresse IP du serveur
        UrlServer.setUrlServer(server);

        if (user.equalsIgnoreCase("debug") && password.equalsIgnoreCase("debug")) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.MAIN_KEY, user);
            startActivity(intent);
            finish();
        } else
            new LoginThread(fragmentManager, this).execute(user, password);
    }

    /**
     * Supprime les identifiants de l'utilisateur
     * si il a décocher la case 'Se souvenir de moi'.
     */
    @Override
    protected void onStop() {
        if (!checkSave.isChecked()) {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(KEY_IP_ADDRESS, "");
            editor.putString(KEY_USER, "");
            editor.putString(KEY_PASSWORD, "");
            editor.putBoolean(KEY_CHECK, checkSave.isChecked());

            editor.apply();
        }
        super.onStop();
    }

    private String check_if_the_fields_are_correct(String server, String user, String password) {
        if (server.isEmpty()) {
            til_Server.setError(getString(R.string.errorEnterServer));

            if (user.isEmpty())
                til_User.setError(getString(R.string.errorUser));
            else
                til_User.setError(null);

            if (password.isEmpty())
                til_Password.setError(getString(R.string.errorPassword));
            else
                til_Password.setError(null);

            return "error";
        } else if (user.isEmpty()) {
            til_User.setError(getString(R.string.errorUser));

            if (server.isEmpty())
                til_Server.setError(getString(R.string.errorEnterServer));
            else
                til_Server.setError(null);

            if (password.isEmpty())
                til_Password.setError(getString(R.string.errorPassword));
            else
                til_Password.setError(null);

            return "error";
        } else if (password.isEmpty()) {
            til_Password.setError(getString(R.string.errorPassword));
            til_Server.setError(null);
            til_User.setError(null);
            return "error";
        } else {
            til_Server.setError(null);
            til_User.setError(null);
            til_Password.setError(null);
        }
        return "ok";
    }

    private void saveID() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_IP_ADDRESS, etServer.getText().toString());
        editor.putString(KEY_USER, etUser.getText().toString());
        editor.putString(KEY_PASSWORD, etPassword.getText().toString());
        editor.putBoolean(KEY_CHECK, checkSave.isChecked());

        editor.apply();
    }

    private void loadUpdateID() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        etServer.setText(sharedPreferences.getString(KEY_IP_ADDRESS, ""));
        etUser.setText(sharedPreferences.getString(KEY_USER, ""));
        etPassword.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
        checkSave.setChecked(sharedPreferences.getBoolean(KEY_CHECK, false));
    }


     /* ************************
        INTERFACE + IMPLEMENTATION
     *************************/

    /**
     * Méthode implémenté de la classe <b>LoginThread</b>
     * qui permet de lancer l'activité principal.
     *
     * @param user L'Utilisateur passe en paramètre ce qui permet de le récuperer.
     * @see fr.modibo.boxdomotique.Model.Thread.LoginThread
     */
    @Override
    public void login(String user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.MAIN_KEY, user);
        startActivity(intent);
        finish();
    }

    /**
     * Méthode implémenté de la classe <b>LoginThread</b>
     * qui retourne soit une erreur au niveau des identifiants
     * ou une erreur au niveau du serveur.
     *
     * @param whatIsError L'Erreur passe en paramètre ce qui permet de le récuperer.
     * @see fr.modibo.boxdomotique.Model.Thread.LoginThread
     */
    @Override
    public void errorLogin(String whatIsError) {
        if (whatIsError.equalsIgnoreCase("userPassword"))
            Snackbar.make(etServer.getRootView(), R.string.errorUserPassword, Snackbar.LENGTH_SHORT).show();
        else
            Snackbar.make(etServer.getRootView(), R.string.errorServer, Snackbar.LENGTH_SHORT).show();
    }
}
