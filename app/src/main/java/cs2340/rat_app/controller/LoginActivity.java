package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cs2340.rat_app.R;
import cs2340.rat_app.model.AccountList;

/**
 * LoginActivity is the controller for the activity_login screen (first
 * screen where you can choose to either login or register).
 */
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

    /**
     * login is the method called when the login button is pressed.
     * @param view the view that calls the method.
     */
    public void login(View view) {
        if (checkLogin()) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
        }
    }

    /**
     * goToRegister is the method that is called when the register button
     * is pressed.
     * @param view the view that calls the method.
     */
    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * checkLogin checks to see that there is at least one registered user, then
     * checks to see if the username exists, if it does then if the password matches, if
     * not, then it returns true and prints invalid password. If the username doesn't exist, returns
     * false and prints invalid username.
     * @return true if login was successful, false otherwise.
     */
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
