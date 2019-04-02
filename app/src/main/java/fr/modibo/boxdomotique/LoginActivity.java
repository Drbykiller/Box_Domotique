package fr.modibo.boxdomotique;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import fr.modibo.boxdomotique.model.Person;
import fr.modibo.boxdomotique.model.webservices.UserData;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUser;
    private EditText etPassword;
    private Button btConnection;
    protected ArrayList<Person> id;
    private MonThread thread;

    private void findViews() {
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btConnection = findViewById(R.id.btConnection);
        btConnection.setOnClickListener(this);
        thread = new MonThread();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        thread.cancel(true);
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

        thread.execute();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Person lol : id) {
            if (lol.getName().equals(user) && lol.getPassword().equals(password)) {
                Toast.makeText(this, "Connexion REUSSI !!!!", Toast.LENGTH_SHORT).show();
                break;
            } else {
                Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
            }
        }
    }


    class MonThread extends AsyncTask<Void, Void, Void> {

        private Exception e;

        @Override
        protected void onPreExecute() {
            id = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                id = UserData.getPersonServer();
            } catch (Exception p) {
                e = p;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            if (e != null) {
                Toast.makeText(LoginActivity.this, R.string.errorServer, Toast.LENGTH_SHORT).show();
            }

        }
    }
}


