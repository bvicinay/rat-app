package cs2340.rat_app.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import org.w3c.dom.Text;

import java.util.Calendar;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatSighting;


/**
 * Created by colton on 10/23/17.
 */

public class AddSightingActivity extends AppCompatActivity {

    private TextView key;
    private TextView date;
    private Calendar creation_date;
    private EditText locTypeField;
    private EditText zipCodeField;
    private EditText addressField;
    private EditText cityField;
    private EditText boroughField;
    private EditText latitudeField;
    private EditText longitudeField;
    private Button backButton;
    private Button addRat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sighting);


        addRat = (Button) findViewById(R.id.create_rat);
        addRat.setOnClickListener((view) -> { createRat(); });

        backButton = (Button) findViewById(R.id.cancel);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getOuter(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getOuter().finish();
            }
        });

        key = (TextView) findViewById(R.id.key);
        date = (TextView) findViewById(R.id.date);

        creation_date = Calendar.getInstance();
        date.setText(getDateStr());
        if (RatSighting.ratSightings.size() > 0) {
            int key1 = RatSighting.ratSightings.get(0).getKey() + 1;
            key.setText(Integer.toString(key1));
        }

        locTypeField = (EditText) findViewById(R.id.locType);
        zipCodeField = (EditText) findViewById(R.id.zipCode);
        addressField = (EditText) findViewById(R.id.address);
        cityField = (EditText) findViewById(R.id.city);
        boroughField = (EditText) findViewById(R.id.borough);
        latitudeField = (EditText) findViewById(R.id.latitude);
        longitudeField = (EditText) findViewById(R.id.longitude);

    }

    public String getDateStr() {
        String month = Integer.toString(creation_date.get(Calendar.MONTH));
        String day = Integer.toString(creation_date.get(Calendar.DATE));
        String year = Integer.toString(creation_date.get(Calendar.YEAR));
        return month + "/" + day + "/" + year;
    }

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

        if (locType.length() == 0) {
            locTypeField.setError("This field is required");
            focusView = locTypeField;
            valid = false;
        }

        if (zipCode.length() == 0) {
            zipCodeField.setError("this field is required");
            focusView = zipCodeField;
            valid = false;
        }

        if (address.length() == 0) {
            addressField.setError("this field is required");
            focusView = addressField;
            valid = false;
        }

        if (city.length() == 0) {
            cityField.setError("this field is required");
            focusView = cityField;
            valid = false;
        }

        if (borough.length() == 0) {
            boroughField.setError("this field is required");
            focusView = boroughField;
            valid = false;
        }

        if (latitude.length() == 0) {
            latitudeField.setError("this field is required");
            focusView = latitudeField;
            valid = false;
        }

        if (longitude.length() == 0) {
            longitudeField.setError("this field is required");
            focusView = longitudeField;
            valid = false;
        }

        if (!valid) {
            focusView.requestFocus();
        }

        return valid;

    }

    private void createRat() {

        if (validateData()) {
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
            RatSighting.ratSightings.add(0, newSighting);
            Intent intent = new Intent(getOuter(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getOuter().finish();
        } else {
            TextView errorText = (TextView) findViewById(R.id.error_text);
            errorText.setText("Please fill in all fields");
        }
    }

    public AddSightingActivity getOuter() {
        return this;
    }
}
