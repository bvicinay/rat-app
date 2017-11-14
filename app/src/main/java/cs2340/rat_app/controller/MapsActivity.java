package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.List;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatList;
import cs2340.rat_app.model.RatSighting;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Calendar startDate;
    private Calendar finishDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        startDate = (Calendar) getIntent().getSerializableExtra("startDate");
        finishDate = (Calendar) getIntent().getSerializableExtra("finishDate");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // When register button is pressed
        Button backButton = findViewById(R.id.map_back_button);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(getOuter(), RatSightingListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getOuter().finish();
        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     * @param googleMap the map to be set
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        try {
            List<RatSighting> sightings = RatList.getInstance();
            List<RatSighting> dateRangeRats;

            dateRangeRats = RatSighting.validateDateForGraph(sightings, startDate,
                    finishDate, 0);

            mMap = RatSighting.filterMap(mMap, dateRangeRats);

            updateMapView(mMap, dateRangeRats);

        } catch(Exception e) {
            Log.d("Exception", "no data in the range", e);
        }
    }

    private void updateMapView(GoogleMap mMap, List<RatSighting> dateRangeRats) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(dateRangeRats.get(0).
                getLocation().getLatitude(), dateRangeRats.get(0).getLocation().getLongitude())));
    }

    private MapsActivity getOuter() { return this; }
}
