package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cs2340.rat_app.R;
import cs2340.rat_app.model.Account;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstname = (EditText) findViewById(R.id.editText3);
        lastname = (EditText) findViewById(R.id.editText6);
        username = (EditText) findViewById(R.id.editText4);
        password = (EditText) findViewById(R.id.editText5);
    }

    public void goToLogin(View view) {
        if (validateData()) {
            Account newPerson = new Account(username.getText().toString(), password.getText().toString(),
                    firstname.getText().toString(), lastname.getText().toString());
        }
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public boolean validateData() {
        String first = firstname.getText().toString();
        String last = lastname.getText().toString();
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (first.equals("") || last.equals("") || user.equals("") || pass.equals("")) {
            System.out.println("All fields must be filled");
            return false;
        }
        if (pass.length() < 8) {
            System.out.println("Password must be at least 8 characters long");
            return false;
        }
        if (Account.getUserList().containsKey(user)) {
            System.out.println("Sorry this username has already been taken please choose a new one!");
            return false;
        }
        return true;
    }
}
