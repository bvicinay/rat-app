package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import cs2340.rat_app.model.Account;


import cs2340.rat_app.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        //Button goToRegister = (Button) findViewById(R.id.button2);
        /*goToRegister.setOnClickListener((view) -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });*/

    }
    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void login() {
        if (Account.getUserList().containsKey(loginUser.getText())) {
            String storedPassword = usersMap.get(loginUser.getText());
            if (storedPassword.equals(loginPassword.getPassword())) {
                // valid login
            } else {
                System.out.println("Invalid password");
            }
        } else {
            System.out.println("Invalid username");
        }
    }
}
