package cs2340.rat_app.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Borja Vicinay on 10/9/2017.
 */

public class RatSighting {

    private int key;
    private Date creation_date;
    private String location_type;
    private Address address;
    private Location location;

    private static final String TAG = "RatSighting";


    public RatSighting(int key, Date creation_date, String location_type,
                       Address address, Location location) {
        this.key = key;
        this.creation_date = creation_date;
        this.location_type = location_type;
        this.address = address;
        this.location = location;

    }
    public RatSighting(int key, String creation_date, String location_type,
                       String street, String borough, int zip, String city,
                       String latitude, String longitude) {
        this.key = key;
        this.location_type = location_type;
        this.address = new Address(street, borough, zip, city);

        // Parse string to Date (if possible)
        DateFormat df = new SimpleDateFormat("M/d/yyyy K:mm", Locale.ENGLISH);
        try {
            this.creation_date =  df.parse(creation_date);
        } catch (ParseException e) {
            this.creation_date =  new Date(0); // prevents future exceptions
            Log.d(TAG, e.getMessage(), e);
        } catch (Exception e) {
            this.creation_date =  new Date(0); // prevents future exceptions
            Log.d(TAG, e.getMessage(), e);
        }

        // Parse string to Location object
        Location l = new Location("User");
        l.setLatitude(Double.parseDouble(latitude));
        l.setLongitude(Double.parseDouble(longitude));
        this.location = l;

    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static String getTAG() {
        return TAG;
    }

    @Override
    public String toString() {
        return Integer.toString(key);
    }

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString("Key : " + Integer.toString(key));
        dest.writeString("Date : " + creation_date.toString());
        dest.writeString("Loc Type : " + location_type);
        dest.writeString("Zip code : " + address.getZip());
        dest.writeString("Address : " + address.getStreet());
        dest.writeString("City : " + address.getCity());
        dest.writeString("Borough : " + address.getBorough());
        dest.writeString("Longitude : " + location.getLongitude());
        dest.writeString("Latitude : " + location.getLatitude());
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public RatSighting createFromParcel(Parcel in) {
            return new RatSighting(in);
        }

        public RatSighting[] newArray(int size) {
            return new RatSighting[size];
        }
    };*/
}
