package cs2340.rat_app.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by colton on 10/1/17.
 */

public class AccountList {

    private static HashMap<String, Account> accounts = new HashMap<>();
    private static ArrayList<Account> accountsArray = new ArrayList();

    private static int size = 0;

    public AccountList(Account[] inputActs) {
        for (Account act: inputActs) {
            accounts.put(act.getUsername(), act);
            accountsArray.add(act);
            size++;
        }
    }

    public AccountList() {};

    public static void add(Account act) {
        AccountList.accounts.put(act.getUsername(), act);
        accountsArray.add(act);
        size++;
    }

    public static Account remove(Account act) {
        accountsArray.remove(act.getUsername());
        size--;
        return accounts.remove(act.getUsername());
    }
    public static Account remove(String username) {
        accountsArray.remove(username);
        size--;
        return accounts.remove(username);
    }

    public static ArrayList<Account> getAccountsArray() {
        return accountsArray;
    }
    public static HashMap<String, Account> getAccounts() {return accounts;}

    public static int size() {
        return size;
    }

    public static boolean isEmpty() {return size == 0;}

}
