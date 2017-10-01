package cs2340.rat_app.model;

import java.util.HashMap;

public class Account {
    //Credentials
    private String userName;
    private String password;

    //User Data
    private String firstName;
    private String lastName;
    private String email;

    public Account(String username, String password, String firstName,
                   String lastName, String email) {
        this.userName = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Account getThis() {
        return this;
    }
}
