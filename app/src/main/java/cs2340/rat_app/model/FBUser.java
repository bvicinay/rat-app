package cs2340.rat_app.model;

/**
 * Singleton representing whether the User is a facebook user.
 *
 *
 *
 *
 * Created by trobinson80 on 12/6/17.
 */

public class FBUser {

    //is FB user singleton
    private static boolean isFBUser = false;

    /**
     * gets the instance of the facebook user
     *
     * @return boolean representing whether a user
     */
    public static boolean getInstance() {return isFBUser;}

    public static void setIsFBUser(boolean b) {


        isFBUser = b;

    }

}
