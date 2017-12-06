package cs2340.rat_app.model;

import com.google.firebase.auth.FirebaseUser;

public class User {
    public static FirebaseUser ourInstance;

    public static FirebaseUser getInstance() {
        return ourInstance;
    }

    private User() {
    }
}
