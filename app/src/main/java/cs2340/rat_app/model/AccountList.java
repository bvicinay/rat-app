package cs2340.rat_app.model;

import java.util.HashMap;

public class AccountList {

    private static HashMap<String, Account> accounts = new HashMap<>();

    private static int size = 0;

    public AccountList(Account[] inputActs) {
        for (Account act: inputActs) {
            accounts.put(act.getUsername(), act);
            size++;
        }
    }

    public AccountList() {}

    /**
     * Adds an account to the account list
     * @param act account object to be added
     */
    public static void add(Account act) {
        AccountList.accounts.put(act.getUsername(), act);
        size++;
    }

    /**
     * Removes an account from the account list
     * @param act account object to be removed
     * @return removed account
     */
    public static Account remove(Account act) {
        size--;
        return accounts.remove(act.getUsername());
    }

    /**
     * Removes an account from the account list
     * @param username username string of account to be removed
     * @return removed account
     */
    public static Account remove(String username) {
        size--;
        return accounts.remove(username);
    }

    /**
     * Accounts getter
     * @return hashmap with usernames and accounts
     */
    public static HashMap<String, Account> getAccounts() {return accounts;}

    /**
     * Size getter
     * @return int number of accounts
     */
    public static int size() {
        return size;
    }

    /**
     * Empty list getter
     * @return boolean representing whether list is empty
     */
    public static boolean isEmpty() {return size == 0;}

}
