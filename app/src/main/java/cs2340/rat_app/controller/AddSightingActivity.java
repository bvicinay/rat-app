package cs2340.rat_app.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatSighting;
import cs2340.rat_app.model.RatSightingRaw;
import cs2340.rat_app.model.RatList;

/**
 * Activity for the User to add a rat sighting
 */
public class AddSightingActivity extends AppCompatActivity {

    //instance variables
    private TextView key;
    private TextView date;
    private TextView errorText;
    private Calendar creation_date;
    private EditText locTypeField;
    private EditText zipCodeField;
    private EditText addressField;
    private EditText cityField;
    private EditText boroughField;
    private EditText latitudeField;
    private EditText longitudeField;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sighting);

        //add rat button
        Button addRat = findViewById(R.id.create_rat);
        addRat.setOnClickListener((view) -> createRat());

        //back button
        Button backButton = findViewById(R.id.cancel);
        backButton.setOnClickListener(v -> {
            Toast toast = Toast.makeText(AddSightingActivity.this, "Rat Report Cancelled",
                    Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(getOuter(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            endActivity();
        });

        //non-editable fields
        key = findViewById(R.id.key);
        date = findViewById(R.id.date);
        errorText = findViewById(R.id.error_text);

        creation_date = Calendar.getInstance();
        date.setText(getDateStr());
        List<RatSighting> ratList = RatList.getInstance();
        if (!ratList.isEmpty()) {
            RatSighting r1 = ratList.get(0);
            String key1 = r1.getKey() + 1;
            key.setText(key1);
        }

        //editable fields
        locTypeField = findViewById(R.id.locType);
        zipCodeField = findViewById(R.id.zipCode);
        addressField = findViewById(R.id.address);
        cityField = findViewById(R.id.city);
        boroughField = findViewById(R.id.borough);
        latitudeField = findViewById(R.id.latitude);
        longitudeField = findViewById(R.id.longitude);

    }

    /**
     * returns a String representation of calender object- mm/dd/yyyy
     * @return a String representation of a Calendar object- date
     */
    private CharSequence getDateStr() {
        String month = Integer.toString(creation_date.get(Calendar.MONTH));
        String day = Integer.toString(creation_date.get(Calendar.DATE));
        String year = Integer.toString(creation_date.get(Calendar.YEAR));
        return (Integer.parseInt(month) + 1) + "/" + day + "/" + year;
    }

    /**
     * boolean that returns whether all the editable fields have data in them
     * @return a boolean stating if all the fields have been filled
     */

    private boolean validateData() {

        Editable lc = locTypeField.getText();
        String locType = lc.toString();
        Editable zC = zipCodeField.getText();
        String zipCode = zC.toString();
        Editable aD = addressField.getText();
        String address = aD.toString();
        Editable ct = cityField.getText();
        String city = ct.toString();
        Editable br = boroughField.getText();
        String borough = br.toString();
        Editable lat = latitudeField.getText();
        String latitude = lat.toString();
        Editable lon = longitudeField.getText();
        String longitude = lon.toString();

        boolean valid = true;
        View focusView = null;

        if (!validateLocType(locType)) {
            locTypeField.setError("This field is required");
            focusView = locTypeField;
            valid = false;
        }

        if (!validateZip(zipCode)) {
            zipCodeField.setError("this field is required");
            focusView = zipCodeField;
            valid = false;
        }

        if (!validateAddress(address)) {
            addressField.setError("this field is required");
            focusView = addressField;
            valid = false;
        }

        if (!validateCity(city)) {
            cityField.setError("this field is required");
            focusView = cityField;
            valid = false;
        }

        if (!validateBorough(borough)) {
            boroughField.setError("this field is required");
            focusView = boroughField;
            valid = false;
        }

        if (!validateLatitude(latitude)) {
            latitudeField.setError("this field is required");
            focusView = latitudeField;
            valid = false;
        }

        if (!validateLongitude(longitude)) {
            longitudeField.setError("this field is required");
            focusView = longitudeField;
            valid = false;
        }

        if (!valid) {
            focusView.requestFocus();
        }
        return valid;
    }

    /**
     * Method to return true if locType is a valid entry and false otherwise
     * @param locType string to be validated
     * @return true if valid, false otherwise
     */
    public static boolean validateLocType(String locType) {
        return !locType.isEmpty();
    }

    /**
     * Method to return true if zipCode is a valid entry and false otherwise
     * @param zipCode string to be validated
     * @return true if valid, false otherwise
     */
    public static boolean validateZip(String zipCode) {
        return !zipCode.isEmpty();
    }

    /**
     * Method to return true if address is a valid entry and false otherwise
     * @param address string to be validated
     * @return true if valid, false otherwise
     */
    public static boolean validateAddress(String address) {
        return !address.isEmpty();
    }

    /**
     * Method to return true if city is a valid entry and false otherwise
     * @param city string to be validated
     * @return true if valid, false otherwise
     */
    public static boolean validateCity(String city) {
        return !city.isEmpty();
    }

    /**
     * Method to return true if borough is a valid entry and false otherwise
     * @param borough string to be validated
     * @return true if valid, false otherwise
     */
    public static boolean validateBorough(String borough) {
        return !borough.isEmpty();
    }

    /**
     * Method to return true if latitude is a valid entry and false otherwise
     * @param latitude string to be validated
     * @return true if valid, false otherwise
     */
    public static boolean validateLatitude(String latitude) {
        return !latitude.isEmpty();
    }

    /**
     * Method to return true if longitude is a valid entry and false otherwise
     * @param longitude string to be validated
     * @return true if valid, false otherwise
     */
    public static boolean validateLongitude(String longitude) {
        return !longitude.isEmpty();
    }

    /**
     * method that creates a new rat object if all the data is properly validated
     */
    @SuppressLint("SetTextI18n")
    private void createRat() {

        if (validateData()) {

            try {
                CharSequence k1 = key.getText();
                Editable zC = zipCodeField.getText();
                CharSequence zip = zC.toString();
                Editable lc = locTypeField.getText();
                Editable aD = addressField.getText();
                Editable ct = cityField.getText();
                Editable br = boroughField.getText();
                Editable lat = latitudeField.getText();
                Editable lon = longitudeField.getText();
                CharSequence date1 = date.getText();
                RatSighting newSighting = new RatSighting(
                        (String) k1,
                        date1.toString(),
                        lc.toString(),
                        aD.toString(),
                        br.toString(),
                        (String) zip,
                        ct.toString(),
                        lat.toString(),
                        lon.toString());
                RatSightingRaw raw = new RatSightingRaw(newSighting);
                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                DatabaseReference mDatabaseRef = mDatabase.getReference();
                DatabaseReference newRequestReference = mDatabaseRef.child("rat_sightings").push();
                newRequestReference.setValue(raw);
                Toast toast = Toast.makeText(AddSightingActivity.this,
                        "Rat Report Added", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getOuter(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                endActivity();
            } catch (Exception e) {
                errorText.setText("Please enter valid data");
            }
        } else {
            errorText.setText("Please fill in all fields");
        }
    }

    /**
     * outer method that returns instance of this class
     * @return AddSightingActivity
     */
    private AddSightingActivity getOuter() {
        return this;
    }

    private void endActivity() {
        this.finish();
    }
}
