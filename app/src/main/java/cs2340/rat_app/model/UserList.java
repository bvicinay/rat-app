package cs2340.rat_app.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by colton on 10/1/17.
 */

public class UserList extends HashMap<String, String>{

    private static UserList userList;
    private static int size;

    private UserList() {
        userList = new UserList();
        size = 0;
    }

    public static UserList getUserList() {
        return userList;
    }

    public int getSize() {
        return size;
    }

    public void incSize() {
        size++;
    }
}
