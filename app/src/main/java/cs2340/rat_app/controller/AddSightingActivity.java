package cs2340.rat_app.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

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
            Toast.makeText(AddSightingActivity.this, "Rat Report Cancelled",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getOuter(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getOuter().finish();
        });

        //non-editable fields
        key = (TextView) findViewById(R.id.key);
        date = (TextView) findViewById(R.id.date);
        errorText = (TextView) findViewById(R.id.error_text);

        creation_date = Calendar.getInstance();
        date.setText(getDateStr());
        if (!RatList.ratSightings.isEmpty()) {
            int key1 = RatList.ratSightings.get(0).getKey() + 1;
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

    }

    /**
     * returns a String representation of calender object- mm/dd/yyyy
     * @return a String representation of a Calendar object- date
     */
    public CharSequence getDateStr() {
        String month = Integer.toString(creation_date.get(Calendar.MONTH));
        String day = Integer.toString(creation_date.get(Calendar.DATE));
        String year = Integer.toString(creation_date.get(Calendar.YEAR));
        return (Integer.parseInt(month) + 1) + "/" + day + "/" + year;
    }

    /**
     * boolean that returns whether all the editable fields have data in them
     * @return a boolean stating if all the fields have been filled
     */
    public boolean validateData() {

        String locType = locTypeField.getText().toString();
        String zipCode = zipCodeField.getText().toString();
        String address = addressField.getText().toString();
        String city = cityField.getText().toString();
        String borough = boroughField.getText().toString();
        String latitude = latitudeField.getText().toString();
        String longitude = longitudeField.getText().toString();

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

        if (validateData()) {
            try {
                int key1 = Integer.parseInt(key.getText().toString());
                int zip = Integer.parseInt(zipCodeField.getText().toString());
                RatSighting newSighting = new RatSighting(
                        key1,
                        date.getText().toString(),
                        locTypeField.getText().toString(),
                        addressField.getText().toString(),
                        boroughField.getText().toString(),
                        zip,
                        cityField.getText().toString(),
                        latitudeField.getText().toString(),
                        longitudeField.getText().toString());
                RatSightingRaw raw = new RatSightingRaw(newSighting);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                DatabaseReference newRequestReference = mDatabase.child("rat_sightings").push();
                newRequestReference.setValue(raw);
                Toast.makeText(AddSightingActivity.this, "Rat Report Added",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getOuter(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getOuter().finish();
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
    public AddSightingActivity getOuter() {
        return this;
    }
}
