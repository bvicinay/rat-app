package cs2340.rat_app.model;

public enum AccountType {
    USER("USER"),
    ADMIN("ADMINISTRATOR");

    private String name;

    AccountType(String name) {
        this.name = name;
    }

}