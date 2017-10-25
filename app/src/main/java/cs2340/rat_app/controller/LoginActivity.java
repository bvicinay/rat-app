package cs2340.rat_app.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);

        // When sign-in button is pressed
        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        // When register button is pressed
        Button toRegisterButton = (Button) findViewById(R.id.toRegister_button);
        toRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getOuter(), RegisterActivity.class);
                startActivity(intent);
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
     * login is the method called when the login button is pressed- if the data is validated,
     * will call signin (which attempts to login using firebase authentication)- else, the login
     * is aborted
     */
    public void login() {
        // Reset errors
        emailField.setError(null);
        passwordField.setError(null);
        if (validateData()) {
            signin(emailField.getText().toString(), passwordField.getText().toString());
        } else {
            abortLogin();
        }
    }

    /**
     * called if login attempt is succesfull- calls new intent
     * @param user the user that has logged in
     */
    public void proceedLogin(FirebaseUser user) {
        Toast.makeText(LoginActivity.this, "Signed-in",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }

    /**
     * called if there is an unsuccesfull login attempt- clears login password field
     */
    public void abortLogin() {
        passwordField.setText("");
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     * @return boolean if the entered data is valid and should be checked against firebase
     */
    private boolean validateData() {
        // Store input values
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        boolean valid = true;
        View focusView = null; //the view to be highlighted in case of error

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

    /**
     * Signin method- attempts to login user using firebase authentication- if successful, calls
     * proceedLogin, else calls abort login
     * @param email the email entered in
     * @param password the password entered in
     */
     public void signin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in succeeds the auth state listener will be notified and logic
                        // to handle the signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            // Sign-in successful, proceed passing user as parameter
                            FirebaseUser user = mAuth.getCurrentUser();
                            proceedLogin(user);
                        }
                        else {
                            // Sign-in failed, display message to user
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Sign-in failed",
                                    Toast.LENGTH_SHORT).show();
                            abortLogin();
                        }
                    }
                });
    }

    /**
     * outer method returns instance of this class
     * @return LoginActivity
     */
    private LoginActivity getOuter() {
        return this;
    }
}

