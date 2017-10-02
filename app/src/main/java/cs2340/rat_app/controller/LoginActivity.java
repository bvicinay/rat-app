package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cs2340.rat_app.R;
import cs2340.rat_app.model.AccountList;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        errorMessage = (TextView) findViewById(R.id.ErrorMessage);
        username = (EditText) findViewById(R.id.usernameField);
        password = (EditText) findViewById(R.id.passwordField);
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
        if (AccountList.size() == 0) {
            errorMessage.setText("There are no registered users!");
            return false;
        } else if (AccountList.getAccounts().containsKey(username.getText().toString())) {
            String storedPassword = password.getText().toString();
            if (storedPassword.equals(AccountList.getAccounts().get(username.getText().toString()).getPassword())) {
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
