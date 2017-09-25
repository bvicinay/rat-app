package cs2340.rat_app.model;

import java.util.HashMap;

public class Account {
    //Credentials
    private String userName;
    private String password;

    //User Data
    private String firstName;
    private String lastName;
    //private String email;

    private static HashMap<String, Account> userList;
    private static int size = 0;

    public Account(String username, String password, String firstName,
                   String lastName) {
        this.userName = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        size++;
        userList.put(userName, this);
    }

    public static HashMap<String, Account> getUserList() {
        return userList;
    }

    public String getPassword() {
        return password;
    }

    public static int getSize() {
        return size;
    }
}
