package fr.modibo.boxdomotique.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import fr.modibo.boxdomotique.R;
import fr.modibo.boxdomotique.model.Person;
import fr.modibo.boxdomotique.model.webservices.UserData;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUser;
    private EditText etPassword;
    private Button btConnection;
    protected ArrayList<Person> listUser;
    private LoginThread loginThread;
    private byte check = 0;
    private byte idUser;

    private void findViews() {
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btConnection = findViewById(R.id.btConnection);
        btConnection.setOnClickListener(this);
        loginThread = new LoginThread();
    }

    /* CYCLE DE VIE */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        loginThread.cancel(true);
    }

    /* FIN CYCLE DE VIE */

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

        loginThread.execute();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Person lol : listUser) {
            if (lol.getName().equals(user) && lol.getPassword().equals(password)) {
                Toast.makeText(this, R.string.successful, Toast.LENGTH_SHORT).show();
                check = 1;
                break;
            } else {
                Toast.makeText(this, R.string.errorUserPassword, Toast.LENGTH_SHORT).show();
                check = 0;
            }
        }

        if (check == 1) {
            Intent intent = new Intent(this, MainActivity.class);

            /*for (byte i = 0; i < listUser.size(); i++) {
                if (user.equals(listUser.get(i).getName())) {
                    idUser = i;
                }
            }*/
            intent.putExtra(MainActivity.MAIN_KEY, user);
            startActivity(intent);
        }

    }


    class LoginThread extends AsyncTask<Void, Void, Void> {

        private Exception e;

        @Override
        protected void onPreExecute() {
            listUser = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                listUser = UserData.getPersonServer();
            } catch (Exception p) {
                e = p;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (e != null)
                Toast.makeText(LoginActivity.this, R.string.errorServer, Toast.LENGTH_SHORT).show();

        }
    }
}


