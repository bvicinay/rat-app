package cs2340.rat_app.controller;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatSighting;

/**
 * Created by colton on 10/12/17.
 */

public class RatReportActivity extends AppCompatActivity{

    //instance variables
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
        RatSighting curr = (RatSighting) b.get("RatSighting");

        key = (TextView) findViewById(R.id.key);
        key.setText("Key : " + curr.getKey());

        date = (TextView) findViewById(R.id.date);
        date.setText("Date : " + curr.getDateStr());

        locType = (TextView) findViewById(R.id.loc_type);
        locType.setText("Location : " + curr.getLocation_type());

        zipCode = (TextView) findViewById(R.id.zip_code);
        zipCode.setText("Zip Code : " + curr.getAddress().getZip());

        address = (TextView) findViewById(R.id.address);
        address.setText("Address : " + curr.getAddress().getStreet());

        city = (TextView) findViewById(R.id.city);
        city.setText("City : " + curr.getAddress().getCity());

        borough = (TextView) findViewById(R.id.borough);
        borough.setText("Borough : " + curr.getAddress().getBorough());

        DecimalFormat df = new DecimalFormat("#.###");
        latitude = (TextView) findViewById(R.id.latitude);
        latitude.setText("Latitude : " + df.format(curr.getLocation().getLatitude()));
        longitude = (TextView) findViewById(R.id.longitude);
        longitude.setText("Longitude : " + df.format(curr.getLocation().getLongitude()));

    }

    /**
     * return this activity
     * @return RatReportActivity
     */
    public RatReportActivity getOuter() {
        return this;
    }

}