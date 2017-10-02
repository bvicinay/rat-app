package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cs2340.rat_app.R;

/**
 * WelcomeActivity is the controller for the activity_welcome screen (screen the app
 * goes to after login is completed and successful).
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    /**
     * returnToLogin is the method that is called when the user presses the back button.
     * Returns the user to the original login screen and logs them out.
     * @param view the view that the calls the method.
     */
    public void returnToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }
}
