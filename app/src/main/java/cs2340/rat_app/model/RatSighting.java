package cs2340.rat_app.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Borja Vicinay on 10/9/2017.
 */

public class RatSighting implements Parcelable {

    //Instance Variables
    private int key;
    private Calendar creation_date;
    private String location_type;
    private Address address;
    private Location location;

    //Constructor for when being passed through a parcel
    public RatSighting(Parcel p) {
        key = p.readInt();
        creation_date = (Calendar) p.readSerializable();
        location_type = p.readString();
        address = p.readParcelable(Address.class.getClassLoader());
        location = p.readParcelable(Location.class.getClassLoader());
    }

    public RatSighting(int key, Calendar creation_date, String location_type,
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
        try {
            String[] dateData = creation_date.split("/");
            dateData[2] = dateData[2].split(" ")[0];
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(dateData[2]), Integer.parseInt(dateData[0]), Integer.parseInt(dateData[1]));
            this.creation_date = calendar;
        } catch (Exception e) { // Add any date to keep data valid
            Calendar calendar = Calendar.getInstance();
            calendar.set(2000,0,0);
            this.creation_date =  calendar;
            Log.d(TAG, e.getMessage(), e);
        }

        // Parse string to Location object
        Location l = new Location("User");
        l.setLatitude(Double.parseDouble(latitude));
        l.setLongitude(Double.parseDouble(longitude));
        this.location = l;

    }

    @Override
    public String toString() {
        return "Key: " + key + " - " + address.getBorough() + ", " + address.getCity();
    }

    //Getters
    public int getKey() {
        return key;
    }

    public Calendar getCreation_date() {
        return creation_date;
    }

    public String getLocation_type() {
        return location_type;
    }

    public Address getAddress() {
        return address;
    }

    public Location getLocation() {
        return location;
    }

    private static final String TAG = "RatSighting";

    public String getTitle() {
        return address.getCity();
    }

    public String getStreet() {
        return address.getStreet();
    }

    /**
     * String representation of date
     * @return date
     */
    public String getDateStr() {
        String month = Integer.toString(creation_date.get(Calendar.MONTH));
        String day = Integer.toString(creation_date.get(Calendar.DATE));
        String year = Integer.toString(creation_date.get(Calendar.YEAR));
        return month + "/" + day + "/" + year;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeSerializable(creation_date);
        dest.writeString(location_type);
        dest.writeParcelable(address, flags);
        dest.writeParcelable(location, flags);
    }

    //For passing through an intent
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public RatSighting createFromParcel(Parcel in) {
            return new RatSighting(in);
        }

        public RatSighting[] newArray(int size) {
            return new RatSighting[size];
        }
    };
}
