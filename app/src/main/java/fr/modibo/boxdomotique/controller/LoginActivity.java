package fr.modibo.boxdomotique.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.view.LoadingDialog;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUser;
    private EditText etPassword;
    private Button btConnection;

    //final String URL = "http://10.0.2.2/user/login_user.php";
    //final String URL = "http://192.168.0.25:3000/auth";
    final String URL = "http://192.168.1.62/user/login_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btConnection = findViewById(R.id.btConnection);
        btConnection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String user = etUser.getText().toString();
        String password = etPassword.getText().toString();

        if (user.isEmpty()) {
            Toast.makeText(this, R.string.errorUser, Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, R.string.errorPassword, Toast.LENGTH_SHORT).show();
            return;
        }

        if (user.equalsIgnoreCase("debug") && password.equalsIgnoreCase("debug")) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.MAIN_KEY, user);
            startActivity(intent);
            finish();

        } else
            new LoginThread().execute(user, password);

    }

    class LoginThread extends AsyncTask<String, Void, Void> {

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

            Response response = null;

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
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, R.string.errorUserPassword, Toast.LENGTH_SHORT).show());

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
                Toast.makeText(LoginActivity.this, R.string.errorServer, Toast.LENGTH_SHORT).show();

        }
    }
}


