package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import cs2340.rat_app.R;

/**
 * Activity displayed on startup
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Button button = findViewById(R.id.get_started_button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getOuter(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            endActivity();
        });
    }

    /**
     * returns this activity
     * @return WelcomeActivity
     */
    private WelcomeActivity getOuter() {
        return this;
    }

    private void endActivity() {
        this.finish();
    }
}
