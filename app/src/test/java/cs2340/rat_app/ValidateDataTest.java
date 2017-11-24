package cs2340.rat_app;

import org.junit.Test;

import cs2340.rat_app.controller.AddSightingActivity;
import cs2340.rat_app.model.RatSighting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Thomas Belford
 * If all tests in this file work, validateData in AddSightingActivity works.
 * Cannot directly test validateData since it is a non static method,
 * but created static methods it depends on and if they all work,
 * validateData works.
 */

public class ValidateDataTest {
    /**
     * Tests if all data passed in are valid.
     */
    @Test
    public void testValidateTrue() {

        assertTrue(AddSightingActivity.validateLocType("City"));
        assertTrue(AddSightingActivity.validateZip("30313"));
        assertTrue(AddSightingActivity.validateAddress("159 5th Street"));
        assertTrue(AddSightingActivity.validateCity("Atlanta"));
        assertTrue(AddSightingActivity.validateBorough("Georgia"));
        assertFalse(AddSightingActivity.validateZip("Hey guys"));
        assertFalse(AddSightingActivity.validateLongitude("Heyo "));
        assertTrue(AddSightingActivity.validateLatitude("10000"));
        assertTrue(AddSightingActivity.validateLongitude("10000"));

    }

    /**
     * Tests if validateLocType works properly
     */
    @Test
    public void testValidateLocType() {
        assertFalse(AddSightingActivity.validateLocType(""));
        assertTrue(AddSightingActivity.validateLocType("Should be true"));
    }

    /**
     * Tests if validateZip works properly
     */
    @Test
    public void testValidateZip() {
        assertFalse(AddSightingActivity.validateZip(""));
        assertTrue(AddSightingActivity.validateZip("30313"));
    }

    /**
     * Tests if validateAddress works properly
     */
    @Test
    public void testValidateAddress() {
        assertFalse(AddSightingActivity.validateAddress(""));
        assertTrue(AddSightingActivity.validateAddress("159 5th"));
    }

    /**
     * Tests if validateCity works properly
     */
    @Test
    public void testValidateCity() {
        assertFalse(AddSightingActivity.validateCity(""));
        assertTrue(AddSightingActivity.validateCity("Atlanta"));
    }

    /**
     * Tests if validateBorough works properly
     */
    @Test
    public void testValidateBorough() {
        assertFalse(AddSightingActivity.validateBorough(""));
        assertTrue(AddSightingActivity.validateBorough("Manhattan"));
    }

    /**
     * Tests if validateLatitude works properly
     */
    @Test
    public void testValidateLatitude() {
        assertFalse(AddSightingActivity.validateLatitude(""));
        assertTrue(AddSightingActivity.validateLatitude("30331313"));
    }

    /**
     * Tests if validateLongitude works properly
     */
    @Test
    public void testValidateLongitude() {
        assertFalse(AddSightingActivity.validateLongitude(""));
        assertTrue(AddSightingActivity.validateLongitude("330313"));
    }

}
