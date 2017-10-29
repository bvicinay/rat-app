package cs2340.rat_app.model;

/**
 * AccountType is an enum representing the types of accounts that are possible.
 */
public enum AccountType {
    USER("USER"),
    ADMIN("ADMINISTRATOR");

    private String name;

    /**
     * Constructor for the enum type.
     * @param name String that will be used to determine the type of the account.
     */
    AccountType(String name) {
        this.name = name;
    }

    public String getName() {return name;}

}