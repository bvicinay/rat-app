package cs2340.rat_app.model;

import java.util.HashMap;
import java.io.Serializable;

public class Account implements Serializable {

    //Account Type
    private AccountType AccountType;

    //Credentials
    private String userName;
    private String password;

    //User Data
    private String firstName;
    private String lastName;
    private String email;



    public Account(AccountType accountType, String username, String password, String firstName,
                   String lastName, String email) {
        this.AccountType = accountType;
        this.userName = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUsername() {
        return userName;
    }
    public String getPassword() {
        return password;
    }


}
