package cs2340.rat_app.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatSighting;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String TAG = "Password Reset";
    private EditText email;
    private Button submit;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.email);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(view -> auth());
        email.setOnClickListener(vie -> auth());

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = FirebaseAuth -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Log.d(TAG, "Password Reset:" + user.getUid());
                // email verified
            } else {
                Log.d(TAG, "No reset");
                // email not verified
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

    private void auth() {
        // Reset errors
        email.setError(null);
        if (validateData()) {
            Editable email = this.email.getText();
            authenticateEmail(email.toString());
        }
    }

    private boolean validateData() {
        boolean valid = true;
        // Store input values
        Editable em = email.getText();
        String email = em.toString();
        if (email.isEmpty()) {
            this.email.setError(getString(R.string.error_field_required));
            valid = false;
        } else if (!email.contains("@") || !email.contains(".")) {
            this.email.setError(getString(R.string.error_invalid_email));
            valid = false;
        }
        return valid;
    }

    private void authenticateEmail(String email) {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = mDatabase.getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast toast = Toast.makeText(ResetPasswordActivity.this,
                                    "Password Reset Sent", Toast.LENGTH_SHORT);
                            toast.show();
                            DatabaseReference newRequestReference = mDatabaseRef.child("security_logging").push();
                            newRequestReference.setValue(email + " sent a password reset at "
                                    + RatSighting.getTimeStamp(Calendar.getInstance()));
                            Log.d(TAG, "Email sent.");
                            Intent intent = new Intent(getOuter(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            DatabaseReference newRequestReference = mDatabaseRef.child("security_logging").push();
                            newRequestReference.setValue(email + " failed to reset their password at "
                                    + RatSighting.getTimeStamp(Calendar.getInstance()));
                            Log.d(TAG, "Invalid email");
                        }
                    }
                });
    }

    private ResetPasswordActivity getOuter() {
        return this;
    }

}
