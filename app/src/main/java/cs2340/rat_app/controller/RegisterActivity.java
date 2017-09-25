package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cs2340.rat_app.R;
import cs2340.rat_app.model.Account;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText username;
    private EditText password;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstname = (EditText) findViewById(R.id.editFirst);
        lastname = (EditText) findViewById(R.id.editLast);
        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);
        errorMessage = (TextView) findViewById(R.id.Error);
    }

    public void goToLogin(View view) {
        if (validateData()) {
            Account newPerson = new Account(username.getText().toString(), password.getText().toString(),
                    firstname.getText().toString(), lastname.getText().toString());
            Account.getUserList().put(username.getText().toString(), newPerson);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void backButton(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public boolean validateData() {
        String first = firstname.getText().toString();
        String last = lastname.getText().toString();
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (first.equals("") || last.equals("") || user.equals("") || pass.equals("")) {
            errorMessage.setText("All fields must be filled");
            return false;
        }
        if (pass.length() < 8) {
            errorMessage.setText("Password must be at least 8 characters long");
            return false;
        }
        if (Account.getSize() != 0) {
            if (Account.getUserList().containsKey(user)) {
                errorMessage.setText("Sorry this username has already been taken please choose a new one!");
                return false;
            }
        }
        return true;
    }
}
