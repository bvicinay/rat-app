package cs2340.rat_app.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

//Firebase
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.util.Log;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import cs2340.rat_app.R;
import cs2340.rat_app.model.AccountType;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    //instance variables
    private EditText firstField;
    private EditText lastField;
    private EditText emailField;
    private EditText passwordField;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        firstField = findViewById(R.id.first_name);
        lastField = findViewById(R.id.last_name);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        Spinner actTypeField = findViewById(R.id.account_type_spinner);

        //Add options to account type spinner
        SpinnerAdapter spinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, AccountType.values());
        actTypeField.setAdapter(spinnerAdapter);

        // When register button is pressed
        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener((view) -> register());


        //Firebase
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
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
     * Register method- called when person attempts to register a new user- if successful,
     * registers them- else clears password field
     */
    private void register() {
        if (validateData()) {
            Editable em = emailField.getText();
            Editable pass = passwordField.getText();
            createAccount(em.toString(), pass.toString());
        } else {
            abortRegister();
        }
    }


    /**
     * Called if a registration attempt is successful
     */
    private void proceedRegister() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }

    /**
     * Called if registration attempt is unsuccessful- clears password field
     */
    private void abortRegister() {
        passwordField.setText("");
    }

    /**
     * This method ensures that the information passed into the registration fields
     * are acceptable. Prints on the app what needs to be fixed if something is wrong.
     * @return true if all data is acceptable, false otherwise.
     */
    private boolean validateData() {
        Editable firstName = firstField.getText();
        Editable lastName = lastField.getText();
        Editable em = emailField.getText();
        Editable pass = passwordField.getText();
        String first = firstName.toString();
        String last = lastName.toString();
        String email = em.toString();
        String password = pass.toString();

        boolean valid = true;
        View focusView = null; //the view to be highlighted in case of error

        if (!validateFirstName(first)) {
            firstField.setError(getString(R.string.error_field_required));
            focusView = firstField;
            valid = false;
        }
        if (validateLastName(last)) {
            lastField.setError(getString(R.string.error_field_required));
            focusView = lastField;
            valid = false;
        }
        if (validatePassword(password) == 1) {
            passwordField.setError(getString(R.string.error_field_required));
            focusView = passwordField;
            valid = false;
        } else if (validatePassword(password) == 2) {
            emailField.setError(getString(R.string.error_invalid_password));
            focusView = passwordField;
            valid = false;
        }
        if (validateEmail(email) == 1) {
            emailField.setError(getString(R.string.error_field_required));
            focusView = emailField;
            valid = false;
        } else if (validateEmail(email) == 2) {
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
     * This method ensures that the information passed into the first name field
     * is acceptable.
     * @param firstName first name input
     * @return true if all data is acceptable, false otherwise.
     */

    public static boolean validateFirstName(String firstName) {
        return !firstName.isEmpty();
    }

    /**
     * This method ensures that the information passed into the last name field
     * is acceptable.
     * @param lastName last name input
     * @return true if all data is acceptable, false otherwise.
     */

    public static boolean validateLastName(String lastName) {
        return !lastName.isEmpty();
    }

    /**
     * This method ensures that the information passed into the password field
     * is acceptable.
     * @param password psw input
     * @return 1 if the length of the password is 0, 2 if the password is
     * shorter than 8 characters, and 3 if the password is acceptable
     */

    public static int validatePassword(CharSequence password) {
        if (password.length() == 0 ) {
            return 1;
        } else if (password.length() < 8) {
            return 2;
        }
        return 3;
    }


    /**
     * This method ensures that the information passed into the email field
     * is acceptable.
     * @param email email input
     * @return 1 if the length of the email is 0, 2 if the password
     * does not contain either an @ symbol or a period;
     * and 3 if the password is acceptable
     */

    public static int validateEmail(String email) {
        if (email.isEmpty()) {
            return 1;
        } else if (!email.contains("@") || !email.contains(".")) {
            return 2;
        }
        return 3;

    }

    /**
     * attempts to create a new account for firebase authentication
     * @param email the email of the account being created
     * @param password the password of the account being created
     */
    private void createAccount(final String email, final String password) {
        // Data is already validated
        Log.d(TAG, "createAccount:" + email);

        // Create user on database
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration successful, proceed passing user as parameter
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast toast = Toast.makeText(RegisterActivity.this, "Account created",
                                Toast.LENGTH_SHORT);
                        toast.show();
                        proceedRegister();
                    } else {
                        // Registration failed, display message to the user, abort.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast toast = Toast.makeText(RegisterActivity.this, "Failed to register",
                                Toast.LENGTH_SHORT);
                        toast.show();
                        abortRegister();
                    }
                });
    }
}

