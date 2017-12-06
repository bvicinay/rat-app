package cs2340.rat_app.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatSighting;
import cs2340.rat_app.model.User;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);

        // When sign-in button is pressed
        Button signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(view -> login());

        // When register button is pressed
        Button toRegisterButton = findViewById(R.id.toRegister_button);
        toRegisterButton.setOnClickListener(view -> {
            Intent intent = new Intent(getOuter(), RegisterActivity.class);
            startActivity(intent);
        });

        Button resetPassword = findViewById(R.id.password_Recovery);
        resetPassword.setOnClickListener(view -> {
            Intent intent = new Intent(getOuter(), ResetPasswordActivity.class);
            startActivity(intent);
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = FirebaseAuth -> {
            FirebaseUser user = FirebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
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
     * login is the method called when the login button is pressed- if the data is validated,
     * will call sign in (which attempts to login using Firebase authentication)- else, the login
     * is aborted
     */
    private void login() {
        // Reset errors
        emailField.setError(null);
        passwordField.setError(null);
        if (validateData()) {
            Editable email = emailField.getText();
            Editable password = passwordField.getText();
            signIn(email.toString(), password.toString());
        } else {
            abortLogin();
        }
    }

    /**
     * called if login attempt is successful - calls new intent
     */
    private void proceedLogin() { //May pass FirebaseUser user as param in future
        Toast toast = Toast.makeText(LoginActivity.this, "Signed-in",
                Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }

    /**
     * called if there is an unsuccessful login attempt- clears login password field
     */
    private void abortLogin() {
        passwordField.setText("");
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     * @return boolean if the entered data is valid and should be checked against Firebase
     */
    private boolean validateData() {
        // Store input values
        Editable em = emailField.getText();
        String email = em.toString();
        Editable pass = passwordField.getText();
        String password = pass.toString();

        boolean valid = true;
        View focusView = null; //the view to be highlighted in case of error

        if (password.isEmpty()) {
            passwordField.setError(getString(R.string.error_field_required));
            focusView = passwordField;
            valid = false;
        } else if (password.length() < 8) {
            emailField.setError(getString(R.string.error_invalid_email));
            focusView = passwordField;
            valid = false;
        }
        if (email.isEmpty()) {
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
     * Sign in method- attempts to login user using Firebase authentication- if successful, calls
     * proceedLogin, else calls abort login
     * @param email the email entered in
     * @param password the password entered in
     */
    private void signIn(String email, String password) {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = mDatabase.getReference();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                    // If sign in succeeds the auth state listener will be notified and logic
                    // to handle the signed in user can be handled in the listener.
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        DatabaseReference newRequestReference = mDatabaseRef.child("security_logging").push();
                        newRequestReference.setValue(email + " logged in at " +
                                RatSighting.getTimeStamp(Calendar.getInstance()));
                        // Sign-in successful, proceed passing user as parameter
                        FirebaseUser user = mAuth.getCurrentUser();
                        proceedLogin();
                    }
                    else {
                        // Sign-in failed, display message to user
                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                        DatabaseReference newRequestReference = mDatabaseRef.child("security_logging").push();
                        newRequestReference.setValue("failed login attempt at " +
                                RatSighting.getTimeStamp(Calendar.getInstance()));
                        Toast toast = Toast.makeText(LoginActivity.this, "Sign-in failed",
                                Toast.LENGTH_SHORT);
                        toast.show();
                        abortLogin();
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

