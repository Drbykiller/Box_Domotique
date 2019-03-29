package fr.modibo.boxdomotique;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsers;
    private EditText etPassword;
    private Button btConnection;

    private void findViews() {
        etUsers = findViewById(R.id.etUsers);
        etPassword = findViewById(R.id.etPassword);
        btConnection = findViewById(R.id.btConnection);
        btConnection.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
    }

    @Override
    public void onClick(View v) {
        String user = etUsers.getText().toString();
        String password = etUsers.getText().toString();

        if (user.isEmpty()) {
            Toast.makeText(this, "Vous n'avez pas entré d'utilisateur !", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Vous n'avez pas entré de Mot de passe !", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "L'utilisateur : " + user + " MDP :" + password, Toast.LENGTH_SHORT).show();
    }
}


