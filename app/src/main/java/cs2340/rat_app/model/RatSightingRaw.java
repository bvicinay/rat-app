package cs2340.rat_app.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class RatSightingRaw {

    //Instance Variables
    private String key;
    private String borough;
    private String city;
    private String created_date;
    private String incident_address;
    private String incident_zip;
    private String location_type;
    private String latitude;
    private String longitude;

    public String getLocation_type() {
        return location_type;
    }

    public RatSightingRaw() {}

    public static ArrayList<RatSightingRaw> ratSightings = new ArrayList<>();

    public String getKey() { return key; }

    public String getBorough() {
        return borough;
    }

    public String getCity() {
        return city;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getIncident_address() {
        return incident_address;
    }

    public String getIncident_zip() {
        return incident_zip;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Rat Sighting on: " + incident_address;
    }

}
