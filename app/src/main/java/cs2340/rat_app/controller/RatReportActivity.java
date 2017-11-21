package cs2340.rat_app.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.location.Location;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatSighting;
import cs2340.rat_app.model.Address;

public class RatReportActivity extends AppCompatActivity{

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.rat_report);
            Intent in = getIntent();
            Bundle b = in.getExtras();
            RatSighting curr = (RatSighting) ((b != null) ? b.get("RatSighting") : null);
            Address ad = (curr != null) ? curr.getAddress() : null;

            TextView key = findViewById(R.id.key);
            key.setText("Key : " + curr.getKey());

            TextView date = findViewById(R.id.date);
            date.setText("Date : " + curr.getDateStr());

            TextView locType = findViewById(R.id.loc_type);
            locType.setText("Location : " + curr.getLocation_type());

            TextView zipCode = findViewById(R.id.zip_code);
            zipCode.setText("Zip Code : " + ad.getZip());

            TextView address = findViewById(R.id.address);
            address.setText("Address : " + ad.getStreet());

            TextView city = findViewById(R.id.city);
            city.setText("City : " + ad.getCity());

            TextView borough = findViewById(R.id.borough);
            borough.setText("Borough : " + ad.getBorough());

            DecimalFormat df = new DecimalFormat("#.###");
            TextView latitude = findViewById(R.id.latitude);
            Location loc = curr.getLocation();
            latitude.setText("Latitude : " + df.format(loc.getLatitude()));
            TextView longitude = findViewById(R.id.longitude);
            longitude.setText("Longitude : " + df.format(loc.getLongitude()));
        } catch (Exception e) {
            Log.d("null pointer exception" , "Caught a null pointer");
        }
    }
}