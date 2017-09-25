package cs2340.rat_app.model;

import java.util.HashMap;

/**
 * Created by Borja Vicinay on 9/24/2017.
 */

public class Account {
    //Credentials
    private String userName;
    private String password;

    //User Data
    private String firstName;
    private String lastName;
    //private String email;

    private static HashMap<String, String> userList;

    public Account(String username, String password, String firstName,
                   String lastName) {
        this.userName = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        userList.put(userName, password);
    }

    public static HashMap<String, String> getUserList() {
        return userList;
    }
}
