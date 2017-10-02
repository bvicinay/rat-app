package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import cs2340.rat_app.R;
import cs2340.rat_app.model.Account;
import cs2340.rat_app.model.AccountType;
import cs2340.rat_app.model.AccountList;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText username;
    private EditText password;
    private EditText email;
    private TextView errorMessage;

    //Spinner
    private Spinner accountType;
    private String[] accountTypes;
    private ArrayAdapter<String> spinnerAdapter;

    private AccountList AccountList = new AccountList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstname = (EditText) findViewById(R.id.editFirst);
        lastname = (EditText) findViewById(R.id.editLast);
        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);
        email = (EditText) findViewById(R.id.editEmail);
        accountType = (Spinner) findViewById(R.id.edit_accountType);
        errorMessage = (TextView) findViewById(R.id.Error);

        //Add options to spinner
        //accountTypes = new String[] {AccountType.USER, AccountType.ADMIN};
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, AccountType.values());
        accountType.setAdapter(spinnerAdapter);

    }

    public void goToLogin(View view) {
        if (validateData()) {
            Account newPerson = new Account((AccountType) accountType.getSelectedItem(), username.getText().toString(), password.getText().toString(),
                    firstname.getText().toString(), lastname.getText().toString(), email.getText().toString());
            AccountList.add(newPerson);
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
        String emailStr = email.getText().toString();

        if (first.equals("") || last.equals("") || user.equals("") || pass.equals("")) {
            errorMessage.setText("All fields must be filled");
            return false;
        }
        if (pass.length() < 8) {
            errorMessage.setText("Password must be at least 8 characters long");
            return false;
        }
        if (emailStr.isEmpty() || !emailStr.contains("@") || !emailStr.contains(".")) {
            errorMessage.setText("Invalid email!");
            return false;
        }
        if (!AccountList.isEmpty()) {
            if (AccountList.getAccounts().containsKey(user)) {
                errorMessage.setText("Sorry this username has already been taken please choose a new one!");
                return false;
            }
        }
        return true;
    }
}
