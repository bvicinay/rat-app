package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import cs2340.rat_app.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Button button = (Button) findViewById(R.id.get_started_button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getOuter(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getOuter().finish();
        });
    }

    /**
     * returns this activity
     * @return WelcomeActivity
     */
    private WelcomeActivity getOuter() {
        return this;
    }
}
