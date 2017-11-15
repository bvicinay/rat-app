package cs2340.rat_app;

import org.junit.Test;

import cs2340.rat_app.controller.AddSightingActivity;

import static org.junit.Assert.assertEquals;

/**
 * Created by thomasbelford on 11/15/17.
 */

public class ValidateDataTest {
    @Test
    public void testValidateTrue() {

        assertEquals(true, AddSightingActivity.validateData("a", "30313",
                "159 5th Street", "Atlanta", "None", "500", "200"));


    }
}
