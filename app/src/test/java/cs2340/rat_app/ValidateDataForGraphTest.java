package cs2340.rat_app;

import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import cs2340.rat_app.model.RatSighting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by colton on 11/21/17.
 */

public class ValidateDataForGraphTest {

    List<RatSighting> rats1 = new ArrayList<>();
    List<RatSighting> filteredList = new ArrayList<>();
    Calendar startDate;
    Calendar endDate;
    DateFormat formatter = new SimpleDateFormat("MM/dd/yy");

    public void testValidateTrue() {
        setStartAndEndDates();
        populateRatList();
        assertFalse(rats1 == filteredList);
        assertFalse(rats1.size() == filteredList.size());
        assertTrue(rats1.size() == 10);
        assertTrue(filteredList.size() == 5);
        assertTrue(rats1.get(1) == filteredList.get(0) );
        assertTrue(rats1.get(9) == filteredList.get(5));
    }

    private void setStartAndEndDates() {
        Date date = null;
        try {
            date = formatter.parse("01/29/02");
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        startDate = Calendar.getInstance();
        startDate.setTime(date);
        endDate = Calendar.getInstance();
    }

    private void populateRatList() {
        rats1.add(new RatSighting(50, "04/15/01", "Frat House",
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
