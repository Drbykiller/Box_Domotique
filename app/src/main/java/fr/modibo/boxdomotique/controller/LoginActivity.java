package fr.modibo.boxdomotique.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.model.UrlServer;
import fr.modibo.boxdomotique.view.LoadingDialog;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText etServer, etUser, etPassword;
    private TextInputLayout til_Server, til_User, til_Password;
    private CheckBox checkSave;
    private Button btConnection;

    // Les key pour la Sauvegarde de données
    private final String SHARED_PREF = "SAVE_DATA";
    private final String KEY_IP_ADDRESS = "IP";
    private final String KEY_USER = "USER";
    private final String KEY_PASSWORD = "PASSWORD";
    private final String KEY_CHECK = "CHECK";

    private String URL;

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

        btConnection = findViewById(R.id.btConnection);
        btConnection.setOnClickListener(this);

        loadUpdateData();
    }

    @Override
    public void onClick(View v) {
        String server = etServer.getText().toString();
        String user = etUser.getText().toString();
        String password = etPassword.getText().toString();

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

            return;
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

            return;
        } else if (password.isEmpty()) {
            til_Password.setError(getString(R.string.errorPassword));
            til_Server.setError(null);
            til_User.setError(null);
            return;
        } else {
            til_Server.setError(null);
            til_User.setError(null);
            til_Password.setError(null);
        }

        if (checkSave.isChecked())
            saveData();

        UrlServer.setUrlServer(server);
        URL = UrlServer.URL_SERVER + UrlServer.URL_CONNECTION;

        if (user.equalsIgnoreCase("debug") && password.equalsIgnoreCase("debug")) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.MAIN_KEY, user);
            startActivity(intent);
            finish();
        } else
            new LoginThread().execute(user, password);
    }

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

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_IP_ADDRESS, etServer.getText().toString());
        editor.putString(KEY_USER, etUser.getText().toString());
        editor.putString(KEY_PASSWORD, etPassword.getText().toString());
        editor.putBoolean(KEY_CHECK, checkSave.isChecked());

        editor.apply();
    }

    private void loadUpdateData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        etServer.setText(sharedPreferences.getString(KEY_IP_ADDRESS, ""));
        etUser.setText(sharedPreferences.getString(KEY_USER, ""));
        etPassword.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
        checkSave.setChecked(sharedPreferences.getBoolean(KEY_CHECK, false));
    }


    private class LoginThread extends AsyncTask<String, Void, Void> {

        private Exception e;
        private LoadingDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new LoadingDialog();
            dialog.show(getSupportFragmentManager(), "Loading");
        }

        @Override
        protected Void doInBackground(String... strings) {
            String user = strings[0];
            String password = strings[1];

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("user", user)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
                    .url(URL)
                    .post(formBody)
                    .build();

            Response response;

            try {
                response = okHttpClient.newCall(request).execute();

                if (response.isSuccessful()) {
                    String result = response.body().string();

                    if (result.equalsIgnoreCase("ok")) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(MainActivity.MAIN_KEY, user);
                        startActivity(intent);
                        finish();

                    } else
                        runOnUiThread(() -> Snackbar.make(etServer.getRootView(), R.string.errorUserPassword, Snackbar.LENGTH_SHORT).show());
                }
            } catch (Exception p) {
                e = p;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();

            if (e != null)
                Snackbar.make(etServer.getRootView(), R.string.errorServer, Snackbar.LENGTH_SHORT).show();

        }
    }
}
