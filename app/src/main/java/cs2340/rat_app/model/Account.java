package cs2340.rat_app.model;

import java.io.Serializable;

/**
 * Account is the class that represents an account in the system. Each account has a username,
 * password, account type, first name, last name, and email
 */
class Account implements Serializable {

    //Credentials
    private String userName;
    private String password;

    /**
     * The constructor for the account. Sets the instance data.
     * @param accountType AccountType enum to declare the account type (user or admin).
     * @param username String that will be set as the username.
     * @param password String that will be set as the password.
     * @param firstName String that will be set as the first name for the account.
     * @param lastName String that will be set as the last name for the account.
     * @param email String that will be set as the email of the account.
     */
  /*  public Account(AccountType accountType, String username, String password, String firstName,
                   String lastName, String email) {
        cs2340.rat_app.model.AccountType accountType1 = accountType;
        this.userName = username;
        this.password = password;
        String firstName1 = firstName;
        String lastName1 = lastName;
        String email1 = email;
    }*/

    /**
     * Getter method for the username.
     * @return String representing the userName.
     */
    public String getUsername() {
        return userName;
    }

    /**
     * Getter method for the password.
     * @return String representing the password.
     */
    public String getPassword() {
        return password;
    }


}
