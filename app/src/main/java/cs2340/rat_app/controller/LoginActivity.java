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

    //instance variables
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
     * Login method that is run when login button is pressed- if a correct
     * username and password have been entered, will login- otherwise,
     * produces an error message
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
     * is pressed- brings the user to the register screen
     * @param view the view that calls the method.
     */
    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * checkLogin is run when a user attempts to login- if there are no registered users, it will
     * throw an error message; it will then check username, and if that is valid, will then
     * check password- if a valid username/password combination are entered, will return true (which
     * will log the user in), else it will return false and produce an error message
     * @return true if combination is  was valid, false otherwise.
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
