package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

//Firebase
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.Toast;
import android.util.Log;
import android.support.annotation.NonNull;

import cs2340.rat_app.R;
import cs2340.rat_app.model.AccountType;
import cs2340.rat_app.model.AccountList;

/**
 * RegisterActivity is the controller for the activity_register screen (registration screen).
 */
public class RegisterActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText username;
    private EditText password;
    private EditText email;
    private TextView errorMessage;

    //Spinner
    private Spinner accountType;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "RegisterActivity";

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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, AccountType.values());
        accountType.setAdapter(spinnerAdapter);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * Returns to login screen if registration was completed successfully.
     * @param view the view that calls the method.
     */
    public void goToLogin(View view) {
        if (validateData()) {
            createAccount(email.getText().toString(), password.getText().toString());
        } else {
            abortRegister();
        }
    }

    public void proceedRegister(String email, String password, FirebaseUser user) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }
    public void abortRegister() {
        password.setText("");
    }

    /**
     * This cancels the registration when the Back button was pressed.
     * @param view the view that calls the method.
     */
    public void backButton(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }

    /**
     * This method ensures that the information passed into the registration fields
     * are acceptable. Prints on the app what needs to be fixed if something is wrong.
     * @return true if all data is acceptable, false otherwise.
     */
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

    //Firebase
    private void createAccount(String email, String password) {
        // Data is already validated
        Log.d(TAG, "createAccount:" + email);
        final String emailFinal = email;
        final String passFinal = password;

        // Create user on database
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful, proceed passing user as parameter
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            proceedRegister(emailFinal, passFinal, user);
                        } else {
                            // Registration failed, display message to the user, abort.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            abortRegister();
                        }
                    }
                });
    }


}
