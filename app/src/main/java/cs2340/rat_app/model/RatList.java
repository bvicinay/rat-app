package cs2340.rat_app.model;

import java.util.ArrayList;

/**
 * Created by colton on 10/25/17.
 */

public class RatList {

    public static ArrayList<RatSighting> ratSightings = new ArrayList<>();

    static ArrayList<RatSighting> getInstance() {
        return ratSightings;
    }

    private RatList() {
    }
}
