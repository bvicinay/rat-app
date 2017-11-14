package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import cs2340.rat_app.R;

public class HomeActivity extends AppCompatActivity {

    private Button viewRatSightings;
    private Button backButton;
    private Button addRatSighting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // When register button is pressed
        viewRatSightings = (Button) findViewById(R.id.view_sightings);
        viewRatSightings.setOnClickListener(view -> {
            Intent intent = new Intent(getOuter(), RatSightingListActivity.class);
            startActivity(intent);
        });
        backButton = (Button) findViewById(R.id.logout);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(getOuter(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getOuter().finish();
        });
        addRatSighting = (Button) findViewById(R.id.add_sighting);
        addRatSighting.setOnClickListener(v -> {
            Intent intent = new Intent(getOuter(), AddSightingActivity.class);
            startActivity(intent);
        });


    }

    /**
     * outer method returns an instance of this class
     * @return HomeActivity
     */
    private HomeActivity getOuter() { return this; }

}
