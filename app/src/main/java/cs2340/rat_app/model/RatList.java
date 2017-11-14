package cs2340.rat_app.model;

import java.util.List;
import java.util.ArrayList;

public final class RatList {

    //ratSighting singleton
    public static List<RatSighting> ratSightings = new ArrayList<>();

    /**
     * returns the singleton
     * @return array list of rat sightings
     */
    public static List<RatSighting> getInstance() {
        return ratSightings;
    }

    private RatList() {
    }
}
