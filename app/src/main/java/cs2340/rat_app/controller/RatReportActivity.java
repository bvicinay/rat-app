package cs2340.rat_app.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.location.Location;
import android.widget.Button;
import android.os.Parcel;

import com.facebook.share.widget.ShareButton;
import com.facebook.share.model.ShareContent;

import cs2340.rat_app.R;
import cs2340.rat_app.model.FBUser;
import cs2340.rat_app.model.RatSighting;

/**
 * Screen that shows details about a rat sighting
 */
public class RatReportActivity extends AppCompatActivity {

    private RatSighting curr;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.rat_report);
            Intent in = getIntent();
            Bundle b = in.getExtras();
            curr = (RatSighting) b.get("RatSighting");
            setFields();
        } catch (Exception e) {
            Log.d("null pointer exception" , "Caught a null pointer");
        }


        Button shareButton = (Button) findViewById(R.id.sharebutton);
        shareButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                DecimalFormat df = new DecimalFormat("#.###");
                Location loc = (curr != null) ? curr.getLocation() : new Location("No data");

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String subject = "Rat Sighting: " + (curr.getKey());
                String body = "Key:" + (curr.getKey())
                        + "\nDate : " + (curr.getDateStr() != null ? curr.getDateStr() : "No Date")
                        + "\nLocation : " + (curr.getLocation_type() != null ?
                        curr.getLocation_type() : "No Type")
                        + "\nZip Code : " + (curr.getZip())
                        + "\nAddress : " + (curr.getStreet() != null ? curr.getStreet()
                        : "No street")
                        + "\nCity : " + (curr.getCity() != null ? curr.getCity() : "No city")
                        + "\nBorough : " + (curr.getBorough() != null ? curr.getBorough()
                        : "No Borough")
                        + "\nLatitude : " + df.format(loc.getLatitude())
                        + "\nLongitude : " + df.format(loc.getLongitude());

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                shareIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(shareIntent, "Share Rat Sighting: "));
            }
        });
    }

    /*
     * sets the values for the view fields
     */
    private void setFields() {
            TextView key = findViewById(R.id.key);
            key.setText("Key : " + (curr.getKey()));

            TextView date = findViewById(R.id.date);
            date.setText("Date : " + (curr.getDateStr() != null ? curr.getDateStr() : "No Date"));

            TextView locType = findViewById(R.id.loc_type);
            locType.setText("Location : " + (curr.getLocation_type() != null ?
                    curr.getLocation_type() : "No Type"));

            DecimalFormat df = new DecimalFormat("#.###");
            TextView latitude = findViewById(R.id.latitude);
            Location loc = (curr != null) ? curr.getLocation() : new Location("No data");
            latitude.setText("Latitude : " + df.format(loc.getLatitude()));
            TextView longitude = findViewById(R.id.longitude);
            longitude.setText("Longitude : " + df.format(loc.getLongitude()));

            TextView zipCode = findViewById(R.id.zip_code);
            zipCode.setText("Zip Code : " + (curr.getZip()));

            TextView address = findViewById(R.id.address);
            address.setText("Address : " + (curr.getStreet() != null ? curr.getStreet()
                    : "No street"));

            TextView city = findViewById(R.id.city);
            city.setText("City : " + (curr.getCity() != null ? curr.getCity() : "No city"));

            TextView borough = findViewById(R.id.borough);
            borough.setText("Borough : " + (curr.getBorough() != null ? curr.getBorough()
                    : "No Borough"));
    }
}