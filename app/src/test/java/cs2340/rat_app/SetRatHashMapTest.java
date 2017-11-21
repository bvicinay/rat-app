package cs2340.rat_app;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Map;

import cs2340.rat_app.model.RatSighting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by trobinson80 on 11/21/17.
 */

/*
 * Test class to see if hash map works properly
 */
@SuppressWarnings("DefaultFileTemplate")
public class SetRatHashMapTest {

    /*
     * Test method to see if hash map works properly
     */
    @Test
    public void testSetRatHashMap() {

        Collection<RatSighting> testRats = new ArrayList<>();

        testRats.add(new RatSighting(303932, "10/02/15", "Apartment", "5050 Knowher St",
                "Brooklyn", 38238, "Columbus", "74.245", "33.211"));
        testRats.add(new RatSighting(303933, "10/04/15", "Apartment", "5050 Knowher St",
                "Brooklyn", 38238, "Columbus", "74.245", "33.211"));
        testRats.add(new RatSighting(303934, "11/02/15", "Apartment", "5050 Knowher St",
                "Brooklyn", 38238, "Columbus", "74.245", "33.211"));
        testRats.add(new RatSighting(303935, "11/04/15", "Apartment", "5050 Knowher St",
                "Brooklyn", 38238, "Columbus", "74.245", "33.211"));
        testRats.add(new RatSighting(303936, "12/02/15", "Apartment", "5050 Knowher St",
                "Brooklyn", 38238, "Columbus", "74.245", "33.211"));


        Map<Calendar, Integer> testRatMap = new HashMap<>();

        Calendar cal1 = Calendar.getInstance();
        cal1.clear();
        cal1.set(Calendar.MONTH, 10);
        cal1.set(Calendar.YEAR, 15);

        Calendar cal2 = Calendar.getInstance();
        cal2.clear();
        cal2.set(Calendar.MONTH, 11);
        cal2.set(Calendar.YEAR, 15);

        Calendar cal3 = Calendar.getInstance();
        cal3.clear();
        cal3.set(Calendar.MONTH, 12);
        cal3.set(Calendar.YEAR, 15);

        testRatMap.put(cal1, 2);
        testRatMap.put(cal2, 2);
        testRatMap.put(cal3, 1);

        assertEquals(testRatMap, RatSighting.setRatHashMap(testRats));

        testRats.add(new RatSighting(303936, "12/04/15", "Apartment", "5050 Knowher St",
                "Brooklyn", 38238, "Columbus", "74.245", "33.211"));

        assertNotEquals(testRatMap, RatSighting.setRatHashMap(testRats));
    }

}
