package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cs2340.rat_app.model.Account;
import cs2340.rat_app.R;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView errorMessage;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        errorMessage = (TextView) findViewById(R.id.ErrorMessage);
        login = (Button) findViewById(R.id.Login);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(login()) {
                    Intent intent = new Intent(this, WeclomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public boolean login() {
        if (Account.getUserList().containsKey(username.getText().toString())) {
            String storedPassword = password.getText().toString();
            if (storedPassword.equals(Account.getUserList().get(username.getText().toString()).getPassword())) {
                errorMessage.setText("Login successfull!");
                return true;
            } else {
                errorMessage.setText("Invalid password");
                return false;
            }
        } else {
            errorMessage.setText("Invalid username");
            return false;
        }
    }
}
