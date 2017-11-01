package cs2340.rat_app.model;

import java.util.ArrayList;

/**
 * Created by colton on 10/25/17.
 */

public class RatList {

    //ratSighting singleton
    public static ArrayList<RatSighting> ratSightings = new ArrayList<>();

    /**
     * returns the singleton
     * @return arraylist of ratsightings
     */
    static ArrayList<RatSighting> getInstance() {
        return ratSightings;
    }

    private RatList() {
    }
}
