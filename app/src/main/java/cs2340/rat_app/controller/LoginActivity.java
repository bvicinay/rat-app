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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        errorMessage = (TextView) findViewById(R.id.ErrorMessage);
    }

    public void login(View view) {
        if (checkLogin()) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public boolean checkLogin() {
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
