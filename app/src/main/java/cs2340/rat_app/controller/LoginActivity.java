package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cs2340.rat_app.R;

//Firebase
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

/**
 * LoginActivity is the controller for the activity_login screen (first
 * screen where you can choose to either login or register).
 */
public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView errorMessage;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        errorMessage = (TextView) findViewById(R.id.ErrorMessage);
        email = (EditText) findViewById(R.id.usernameField);
        password = (EditText) findViewById(R.id.passwordField);

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
     * login is the method called when the login button is pressed.
     * @param view the view that calls the method.
     */
    public void login(View view) {
        if (validateData()) {
            signin(email.getText().toString(), password.getText().toString());
        } else {
            abortLogin();
        }
    }

    public void proceedLogin(FirebaseUser user) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }
    public void abortLogin() {
        password.setText("");
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
    public boolean validateData() {
        String eMail = email.getText().toString();
        String pass = password.getText().toString();
        if (eMail.length() == 0 || pass.length() == 0 ) {
            errorMessage.setText("Password must be at least 8 characters long");
            return false;
        }
        return true;
    }

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
}
