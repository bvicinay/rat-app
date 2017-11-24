package cs2340.rat_app;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cs2340.rat_app.model.RatSighting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by colton on 11/21/17.
 */

/*
 * Test class to see if the graph filter works correctly
 */
@SuppressWarnings("DefaultFileTemplate")
public class ValidateDataForGraphTest {

    private final List<RatSighting> rats1 = new ArrayList<>();
    private List<RatSighting> filteredList = new ArrayList<>();
    private Calendar startDate;
    private Calendar endDate;


    /**
     * Test method to see if the graph filter works correctly
     */
    @Test
    public void testValidateTrue() {
        setStartAndEndDates();
        populateRatList();
        assertFalse(rats1 == filteredList);
        assertFalse(rats1.size() == filteredList.size());
        assertTrue(rats1.size() == 10);
        assertTrue(filteredList.size() == 5);
        assertTrue(rats1.get(1) == filteredList.get(0));
        assertTrue(rats1.get(9) == filteredList.get(4));
    }

    private void setStartAndEndDates() {
        startDate = Calendar.getInstance();
        startDate.clear();
        startDate.set(Calendar.YEAR, 2);
        startDate.set(Calendar.MONTH, 1);
        startDate.set(Calendar.DATE, 29);
        endDate = Calendar.getInstance();
        endDate.clear();
        endDate.set(Calendar.YEAR, 17);
        endDate.set(Calendar.MONTH, 11);
        endDate.set(Calendar.DATE, 21);
    }

    private void populateRatList() {
        rats1.add(new RatSighting(50, null, "Frat House",
                "5th St", "Tau Kap", 30313, "Atl", "50",
                "50"));
        rats1.add(new RatSighting(50, "09/17/04", "Frat House",
                "5th St", "Tau Kap", 30313, "Atl", "50",
                "50"));
        rats1.add(new RatSighting(50, "01/14/01", "Frat House",
                "5th St", "Tau Kap", 30313, "Atl", "50",
                "50"));
        rats1.add(new RatSighting(50, "02/22/00", "Frat House",
                "5th St", "Tau Kap", 30313, "Atl", "50",
                "50"));
        rats1.add(new RatSighting(50, "06/03/15", "Frat House",
                "5th St", "Tau Kap", 30313, "Atl", "50",
                "50"));
        rats1.add(new RatSighting(50, "08/18/08", "Frat House",
                "5th St", "Tau Kap", 30313, "Atl", "50",
                "50"));
        rats1.add(new RatSighting(50, "10/10/18", "Frat House",
                "5th St", "Tau Kap", 30313, "Atl", "50",
                "50"));
        rats1.add(new RatSighting(50, "08/19/17", "Frat House",
                "5th St", "Tau Kap", 30313, "Atl", "50",
                "50"));
        rats1.add(new RatSighting(50, "12/12/17", "Frat House",
                "5th St", "Tau Kap", 30313, "Atl", "50",
                "50"));
        rats1.add(new RatSighting(50, "11/20/17", "Frat House",
                "5th St", "Tau Kap", 30313, "Atl", "50",
                "50"));
        filteredList = RatSighting.validateDataForMapAndGraph(rats1, startDate, endDate);
    }
}
