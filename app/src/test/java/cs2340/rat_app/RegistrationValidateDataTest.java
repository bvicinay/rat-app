package cs2340.rat_app;

import org.junit.Test;

import cs2340.rat_app.controller.RegisterActivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Steven Tinsley
 */

public class RegistrationValidateDataTest {


    @Test
    public void testValidateData() {
        String firstFail = "";
        String firstPass = "Steven";

        String lastFail = "";
        String lastPass = "Tinsley";

        String passwordFail0 = "";
        String passwordFailShort = "testing";
        String passwordPass = "testingPassword";

        String emailFail0 = "";
        String emailFailNoAt = "steventinsley3.gmail.com";
        String emailFailNoPeriod = "steventinsley3@gmail@com";
        String emailPass = "steventinsley3@gmail.com";



        //Test First Name
        assertFalse(RegisterActivity.validateFirstName(firstFail));
        assertTrue(RegisterActivity.validateFirstName(firstPass));

        //Test Last Name
        assertFalse(RegisterActivity.validateLastName(lastFail));
        assertTrue(RegisterActivity.validateLastName(lastPass));

        //Test Password
        assertTrue(RegisterActivity.validatePassword(passwordFail0) == 1); //0 as password length
        assertTrue(RegisterActivity.validatePassword(passwordFailShort) == 2); //password too short
        assertTrue(RegisterActivity.validatePassword(passwordPass) == 3); //password passes

        //Test email
        assertTrue(RegisterActivity.validateEmail(emailFail0) == 1); //email length is 0
        assertTrue(RegisterActivity.validateEmail(emailFailNoAt) == 2); //no @ symbol
        assertTrue(RegisterActivity.validateEmail(emailFailNoPeriod) == 2); //no . in email
        assertTrue(RegisterActivity.validateEmail(emailPass) == 3);

    }
}

