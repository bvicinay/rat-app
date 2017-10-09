package cs2340.rat_app.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

//Firebase
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import cs2340.rat_app.R;
import cs2340.rat_app.model.AccountType;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText firstField;
    private EditText lastField;
    private EditText emailField;
    private EditText passwordField;
    private Spinner actTypeField;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        firstField = (EditText) findViewById(R.id.first_name);
        lastField = (EditText) findViewById(R.id.last_name);
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);
        actTypeField = (Spinner) findViewById(R.id.account_type_spinner);

        //Add options to account type spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, AccountType.values());
        actTypeField.setAdapter(spinnerAdapter);

        // When register button is pressed
        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });


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
    public void onStart() { //made public for firebase
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() { //made public for firebase
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * Returns to login screen if registration was completed successfully.
     */
    public void register() {
        if (validateData()) {
            createAccount(emailField.getText().toString(), passwordField.getText().toString());
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
        passwordField.setText("");
    }

    /**
     * This method ensures that the information passed into the registration fields
     * are acceptable. Prints on the app what needs to be fixed if something is wrong.
     * @return true if all data is acceptable, false otherwise.
     */
    public boolean validateData() {
        String first = firstField.getText().toString();
        String last = lastField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        boolean valid = true;
        View focusView = null; //the view to be highlighted in case of error

        if (first.length() == 0) {
            firstField.setError(getString(R.string.error_field_required));
            focusView = firstField;
            valid = false;
        }
        if (last.length() == 0) {
            lastField.setError(getString(R.string.error_field_required));
            focusView = lastField;
            valid = false;
        }
        if (password.length() == 0) {
            passwordField.setError(getString(R.string.error_field_required));
            focusView = passwordField;
            valid = false;
        } else if (password.length() < 8) {
            emailField.setError(getString(R.string.error_invalid_email));
            focusView = passwordField;
            valid = false;
        }
        if (email.length() == 0) {
            emailField.setError(getString(R.string.error_field_required));
            focusView = emailField;
            valid = false;
        } else if (!email.contains("@") || !email.contains(".")) {
            emailField.setError(getString(R.string.error_invalid_email));
            focusView = emailField;
            valid = false;
        }
        if (!valid) {
            // There was an error, highlight field with error
            focusView.requestFocus();
        }
        return valid;
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
                            Toast.makeText(RegisterActivity.this, "Account created",
                                    Toast.LENGTH_SHORT).show();
                            proceedRegister(emailFinal, passFinal, user);
                        } else {
                            // Registration failed, display message to the user, abort.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Failed to register",
                                    Toast.LENGTH_SHORT).show();
                            abortRegister();
                        }
                    }
                });
    }
    private RegisterActivity getOuter() {
        return this;
    }
}

