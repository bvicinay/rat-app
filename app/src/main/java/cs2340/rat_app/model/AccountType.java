package cs2340.rat_app.model;

/**
 * Created by Borja Vicinay on 10/1/2017.
 */

public enum AccountType {
    USER("USER"),
    ADMIN("ADMINISTRATOR");

    private String name;

    AccountType(String name) {
        this.name = name;
    }

}