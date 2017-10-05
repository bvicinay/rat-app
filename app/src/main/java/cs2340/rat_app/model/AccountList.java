package cs2340.rat_app.model;

import java.util.HashMap;

//Singleton for holding the list of accounts
public class AccountList {

    //hash map to store the accounts
    private static HashMap<String, Account> accounts = new HashMap<>();

    private static int size = 0;

    //Constructor that adds accounts to HashMap that are passed in
    public AccountList(Account[] inputActs) {
        for (Account act: inputActs) {
            accounts.put(act.getUsername(), act);
            size++;
        }
    }

    //default no args constructor
    public AccountList() {

    }

    /**
     * Method that adds an account to the HashMap and increments its size
     * @param act the account being added
     */
    public static void add(Account act) {
        AccountList.accounts.put(act.getUsername(), act);
        size++;
    }

    /**
     * method that removes an account from the HashMap
     * @param username the username (key) of the account being removed
     * @return the removed account
     */
    public static Account remove(String username) {
        size--;
        return accounts.remove(username);
    }

    /**
     * static method that returns a reference to the Account HashMap singleton
     * @return the account HashMap
     */
    public static HashMap<String, Account> getAccounts() {return accounts;}

    /**
     * static method that returns the size of the Account HashMap singleton
     * @return the size of the singleton
     */
    public static int size() {
        return size;
    }

    /**
     * static method that returns if the Account singleton is empty (true = empty)
     * @return a boolean stating if it is empty
     */
    public static boolean isEmpty() {return size == 0;}

}
