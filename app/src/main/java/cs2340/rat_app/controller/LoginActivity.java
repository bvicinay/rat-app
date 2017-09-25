package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cs2340.rat_app.model.Account;
import cs2340.rat_app.R;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public boolean login() {
        if (Account.getUserList().containsKey(username.getText().toString())) {
            String storedPassword = password.getText().toString();
            if (storedPassword.equals(Account.getUserList().get(username.getText().toString()).getPassword())) {
                System.out.println("Login successfull!");
                return true;
            } else {
                System.out.println("Invalid password");
                return false;
            }
        } else {
            System.out.println("Invalid username");
            return false;
        }
    }
}
