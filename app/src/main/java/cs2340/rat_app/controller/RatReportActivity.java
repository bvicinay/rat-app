package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import cs2340.rat_app.R;

/**
 * Created by colton on 10/12/17.
 */

public class RatReportActivity extends AppCompatActivity{

    private TextView key;
    private TextView date;
    private TextView locType;
    private TextView zipCode;
    private TextView address;
    private TextView city;
    private TextView borough;
    private TextView latitude;
    private TextView longitude;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rat_report);
        Intent in = getIntent();
        Bundle b = in.getExtras();

        backButton = (Button) findViewById(R.id.get_started_button);

        key = (TextView) findViewById(R.id.key);
        key.setText("Key : " + b.getString("Key"));

        date = (TextView) findViewById(R.id.date);
        date.setText("Date : " + b.getString("Date"));

        locType = (TextView) findViewById(R.id.loc_type);
        locType.setText("Location : " + b.getString("LocType"));

        zipCode = (TextView) findViewById(R.id.zip_code);
        zipCode.setText("Zip Code : " + b.getString("ZipCode"));

        address = (TextView) findViewById(R.id.address);
        address.setText("Address : " + b.getString("Address"));

        city = (TextView) findViewById(R.id.city);
        city.setText("City : " + b.getString("City"));

        borough = (TextView) findViewById(R.id.borough);
        borough.setText("Borough : " + b.getString("Borough"));

        latitude = (TextView) findViewById(R.id.latitude);
        latitude.setText("Latitude : " + b.getString("Latitude"));

        longitude = (TextView) findViewById(R.id.longitude);
        longitude.setText("Longitude : " + b.getString("Longitude"));

        backButton = (Button) findViewById(R.id.back);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getOuter(), RatSightingListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getOuter().finish();
            }
        });
    }

    public RatReportActivity getOuter() {
        return this;
    }
}