package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import cs2340.rat_app.R;

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
            Intent intent = new Intent(getOuter(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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
