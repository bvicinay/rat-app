package cs2340.rat_app.controller;

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
    private String locTypeString;
    private String zipCodeString;
    private String addressString;
    private String cityString;
    private String boroughString;
    private String latitudeString;
    private String longitudeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sighting);

        //add rat button
        Button addRat = (Button) findViewById(R.id.create_rat);
        addRat.setOnClickListener((view) -> createRat());

        //back button
        Button backButton = (Button) findViewById(R.id.cancel);
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
        key = (TextView) findViewById(R.id.key);
        date = (TextView) findViewById(R.id.date);
        errorText = (TextView) findViewById(R.id.error_text);

        creation_date = Calendar.getInstance();
        date.setText(getDateStr());
        List<RatSighting> ratList = RatList.getInstance();
        if (!ratList.isEmpty()) {
            RatSighting r1 = ratList.get(0);
            int key1 = r1.getKey() + 1;
            key.setText(Integer.toString(key1));
        }

        //editable fields
        locTypeField = (EditText) findViewById(R.id.locType);
        zipCodeField = (EditText) findViewById(R.id.zipCode);
        addressField = (EditText) findViewById(R.id.address);
        cityField = (EditText) findViewById(R.id.city);
        boroughField = (EditText) findViewById(R.id.borough);
        latitudeField = (EditText) findViewById(R.id.latitude);
        longitudeField = (EditText) findViewById(R.id.longitude);

        //editable fields to String
        Editable lc = locTypeField.getText();
        locTypeString = lc.toString();
        Editable zC = zipCodeField.getText();
        zipCodeString = zC.toString();
        Editable aD = addressField.getText();
        addressString = aD.toString();
        Editable ct = cityField.getText();
        cityString = ct.toString();
        Editable br = boroughField.getText();
        boroughString = br.toString();
        Editable lat = latitudeField.getText();
        latitudeString = lat.toString();
        Editable lon = longitudeField.getText();
        longitudeString = lon.toString();
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
     * @param locType location type string to be validated
     * @param zipCode zipCode string to be validated
     * @param address address string to be validated
     * @param city city string to be validated
     * @param borough borough string to be validated
     * @param latitude latitude string to be validated
     * @param longitude longitude string to be validated
     * @return a boolean stating if all the fields have been filled
     */
    public boolean validateData(String locType, String zipCode, String address, String city,
                                 String borough, String latitude, String longitude) {

        boolean valid = true;

        View focusView = null; //highlighted view in case of error

        if (locType.isEmpty()) {
            locTypeField.setError("This field is required");
            focusView = locTypeField;
            valid = false;
        }

        if (zipCode.isEmpty()) {
            zipCodeField.setError("this field is required");
            focusView = zipCodeField;
            valid = false;
        }

        if (address.isEmpty()) {
            addressField.setError("this field is required");
            focusView = addressField;
            valid = false;
        }

        if (city.isEmpty()) {
            cityField.setError("this field is required");
            focusView = cityField;
            valid = false;
        }

        if (borough.isEmpty()) {
            boroughField.setError("this field is required");
            focusView = boroughField;
            valid = false;
        }

        if (latitude.isEmpty()) {
            latitudeField.setError("this field is required");
            focusView = latitudeField;
            valid = false;
        }

        if (longitude.isEmpty()) {
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
     * method that creates a new rat object if all the data is properly validated
     * @throws Exception if there is invalid data entered in the fields
     */
    private void createRat() {

        if (validateData(locTypeString, zipCodeString, addressString, cityString,
                boroughString, latitudeString, longitudeString)) {
            try {
                CharSequence k1 = key.getText();
                int key1 = Integer.parseInt(k1.toString());
                Editable zC = zipCodeField.getText();
                int zip = Integer.parseInt(zC.toString());
                Editable lc = locTypeField.getText();
                Editable aD = addressField.getText();
                Editable ct = cityField.getText();
                Editable br = boroughField.getText();
                Editable lat = latitudeField.getText();
                Editable lon = longitudeField.getText();
                CharSequence date1 = date.getText();
                RatSighting newSighting = new RatSighting(
                        key1,
                        date1.toString(),
                        lc.toString(),
                        aD.toString(),
                        br.toString(),
                        zip,
                        ct.toString(),
                        lat.toString(),
                        lon.toString());
                RatSightingRaw raw = new RatSightingRaw(newSighting);
                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                DatabaseReference mDatabaseRef = mDatabase.getReference();
                DatabaseReference newRequestReference = mDatabaseRef.child("rat_sightings");
                newRequestReference.push();
                newRequestReference.setValue(raw);
                Toast toast = Toast.makeText(AddSightingActivity.this, "Rat Report Added",
                        Toast.LENGTH_SHORT);
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
