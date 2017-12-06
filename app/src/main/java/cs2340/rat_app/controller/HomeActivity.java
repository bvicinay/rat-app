package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatSighting;
import cs2340.rat_app.model.User;

/**
 * Home activity when the user logs in
 */

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // When register button is pressed
        Button viewRatSightings = findViewById(R.id.view_sightings);
        viewRatSightings.setOnClickListener(view -> {
            Intent intent = new Intent(getOuter(), RatSightingListActivity.class);
            startActivity(intent);
        });
        Button backButton = findViewById(R.id.logout);
        backButton.setOnClickListener(v -> {
            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mDatabaseRef = mDatabase.getReference();
            Intent intent = new Intent(getOuter(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            DatabaseReference newRequestReference2 = mDatabaseRef.child("security_logging").push();
            newRequestReference2.setValue(User.getInstance().getEmail() + " logged out at " +
                    RatSighting.getTimeStamp(Calendar.getInstance()));
            endActivity();
        });

    }

    /**
     * outer method returns an instance of this class
     * @return HomeActivity
     */
    private HomeActivity getOuter() { return this; }

    private void endActivity() {
        this.finish();
    }
}
