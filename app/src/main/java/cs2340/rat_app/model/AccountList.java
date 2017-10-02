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

    public AccountList() {};

    public static void add(Account act) {
        AccountList.accounts.put(act.getUsername(), act);
        size++;
    }

    public static Account remove(Account act) {
        size--;
        return accounts.remove(act.getUsername());
    }
    public static Account remove(String username) {
        size--;
        return accounts.remove(username);
    }

    public static HashMap<String, Account> getAccounts() {return accounts;}

    public static int size() {
        return size;
    }

    public static boolean isEmpty() {return size == 0;}

}
