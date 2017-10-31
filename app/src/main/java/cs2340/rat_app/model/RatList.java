package cs2340.rat_app.model;

/**
 * Created by colton on 10/25/17.
 */

class RatList {
    private static final RatList ourInstance = new RatList();

    static RatList getInstance() {
        return ourInstance;
    }

    private RatList() {
    }
}
