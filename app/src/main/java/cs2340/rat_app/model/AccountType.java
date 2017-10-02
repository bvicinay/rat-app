package cs2340.rat_app.model;

/**
 * AccountType is an enum representing the types of accounts that are possible.
 * Right now, just User and Admin.
 */
public enum AccountType {
    USER("USER"),
    ADMIN("ADMINISTRATOR");

    private String name;

    /**
     * Constructor for the account type.
     * @param name String that will be used to determine the type of the account.
     */
    AccountType(String name) {
        this.name = name;
    }

}