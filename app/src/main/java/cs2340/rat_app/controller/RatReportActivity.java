package cs2340.rat_app.controller;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatSighting;

public class RatReportActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.rat_report);
            Intent in = getIntent();
            Bundle b = in.getExtras();
            RatSighting curr = (RatSighting) b.get("RatSighting");

            TextView key = (TextView) findViewById(R.id.key);
            key.setText("Key : " + curr.getKey());

            TextView date = (TextView) findViewById(R.id.date);
            date.setText("Date : " + curr.getDateStr());

            TextView locType = (TextView) findViewById(R.id.loc_type);
            locType.setText("Location : " + curr.getLocation_type());

            TextView zipCode = (TextView) findViewById(R.id.zip_code);
            zipCode.setText("Zip Code : " + curr.getAddress().getZip());

            TextView address = (TextView) findViewById(R.id.address);
            address.setText("Address : " + curr.getAddress().getStreet());

            TextView city = (TextView) findViewById(R.id.city);
            city.setText("City : " + curr.getAddress().getCity());

            TextView borough = (TextView) findViewById(R.id.borough);
            borough.setText("Borough : " + curr.getAddress().getBorough());

            DecimalFormat df = new DecimalFormat("#.###");
            TextView latitude = (TextView) findViewById(R.id.latitude);
            latitude.setText("Latitude : " + df.format(curr.getLocation().getLatitude()));
            TextView longitude = (TextView) findViewById(R.id.longitude);
            longitude.setText("Longitude : " + df.format(curr.getLocation().getLongitude()));
        } catch (Exception e) {
            Log.d("null pointer exception" , "Caught a null pointer");
        }
    }

    /**
     * return this activity
     * @return RatReportActivity
     */
    public RatReportActivity getOuter() {
        return this;
    }

}