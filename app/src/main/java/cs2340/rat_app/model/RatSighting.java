package cs2340.rat_app.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Calendar;

/**
 * Represents a Rat Sighting
 */

public class RatSighting implements Parcelable {

    //Instance Variables
    private final int key;
    private Calendar creation_date;
    private final String location_type;
    private final Address address;
    private final Location location;

    /**
     * RatSighting constructor called by parcel
     * @param p the parcel being passed through
     */
    private RatSighting(Parcel p) {
        key = p.readInt();
        creation_date = (Calendar) p.readSerializable();
        location_type = p.readString();
        address = p.readParcelable(Address.class.getClassLoader());
        location = p.readParcelable(Location.class.getClassLoader());
    }

    /**
     * Constructor called from RatSightingListActivity
     * @param key the key of the sighting
     * @param creation_date the date of creation
     * @param location_type the location type
     * @param street the street of the sighting
     * @param borough the borough of the sighting
     * @param zip the zip of the sighting
     * @param city the city
     * @param latitude the latitude
     * @param longitude the longitude
     */
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
            calendar.set(Integer.parseInt(dateData[2]), Integer.parseInt(dateData[0]),
                    Integer.parseInt(dateData[1]));
            this.creation_date = calendar;
        } catch (Exception e) { // Add any date to keep data valid
            Calendar calendar = Calendar.getInstance();
            calendar.set(0,0,0);
            this.creation_date =  calendar;
            Log.d(TAG, e.getMessage(), e);
        }

        // Parse string to Location object
        Location l = new Location("User");
        l.setLatitude(Double.parseDouble(latitude));
        l.setLongitude(Double.parseDouble(longitude));
        this.location = l;

    }

    /**
     * Rat Sighting object constructor that utilizes a raw sighting
     * @param rs raw rat sighting from firebase
     */
    public RatSighting(RatSightingRaw rs) {
        this(Integer.parseInt(rs.getKey()), rs.getCreated_date(), rs.getLocation_type(),
                rs.getIncident_address(), rs.getBorough(), Integer.parseInt(rs.getIncident_zip()),
                rs.getCity(), rs.getLatitude(), rs.getLongitude());
    }

    @Override
    public String toString() {
        return "Key: " + key + " - " + address.getBorough() + ", " + address.getCity();
    }

    /**
     * Key getter
     * @return key
     */
    public int getKey() {
        return key;
    }

    /**
     * Calendar object getter
     * @return creation_date
     */
    private Calendar getCreation_date() {
        return creation_date;
    }

    /**
     * location type getter
     * @return location_type
     */
    public String getLocation_type() {
        return location_type;
    }

    /**
     * location getter
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    private static final String TAG = "RatSighting";

    /**
     * city getter
     * @return city
     */
    public CharSequence getTitle() {
        return address.getCity();
    }

    /**
     * Street getter
     * @return Street
     */
    public CharSequence getStreet() {
        return address.getStreet();
    }

    /**
     * Zip getter
     * @return Zip
     */
    public int getZip() {
        return address.getZip();
    }

    /**
     * Borough getter
     * @return Borough
     */
    public String getBorough() {
        return address.getBorough();
    }

    /**
     * City getter
     * @return City
     */
    public String getCity() {
        return address.getCity();
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

    private int getMonth() {
        return creation_date.get(Calendar.MONTH);
    }

    private int getYear() {
        return creation_date.get(Calendar.YEAR);
    }

    /**
     * Checks whether the data to be used in the map/graph is valid
     * @param rats RatSightings
     * @param startDate filter
     * @param finishDate filter
     * @return list of rat sightings
     */
    public static List<RatSighting> validateDataForMapAndGraph(
            Iterable<RatSighting> rats, Calendar startDate, Calendar finishDate) {
        List<RatSighting> filteredList = new ArrayList<>();
        for (RatSighting rat : rats) {
            if (rat.getCreation_date() == null) {
                continue;
            }
            try {
                Calendar c1 = rat.getCreation_date();
                if ((c1.compareTo(startDate) >= 0) &&
                        (c1.compareTo(finishDate) <= 0)) {
                    filteredList.add(rat);
                }
            } catch (Exception e) {
                Log.d("Missing data", "Rat is missing data");
            }
        }
        return filteredList;
    }

    /**
     * Creates a HashMap mapping dates to number of sightings on that date
     * @param filteredList list of filtered RatSightings
     * @return finished HashMap to be passed in to graph
     */
    public static HashMap<Calendar, Integer> setRatHashMap(Iterable<RatSighting> filteredList) {
        HashMap<Calendar, Integer> ratSightingHashMap = new HashMap<>();
        for (RatSighting rat: filteredList) {
            int month = rat.getMonth();
            int year = rat.getYear();
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, year);
            if (ratSightingHashMap.containsKey(cal)) {
                ratSightingHashMap.put(cal, ratSightingHashMap.get(cal) + 1);
            } else {
                ratSightingHashMap.put(cal, 1);
            }
        }
        return ratSightingHashMap;
    }

    /**
     * Filtered map RatSightings
     * @param mMap google map
     * @param dateRangeRats rats in the date range
     * @return the google map to be used
     */
    public static GoogleMap filterMap(GoogleMap mMap, List<RatSighting> dateRangeRats) {
        for (int i = 0; i < dateRangeRats.size(); i++) {
            try {
                RatSighting rat = dateRangeRats.get(i);
                Location loc = rat.getLocation();
                mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(),
                        loc.getLongitude())).title("Rat " + rat.getKey()));
            } catch(Exception e){
                Log.d("null pointer", "marker was missing longitude or latitude");
            }
        }
        return mMap;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeSerializable(creation_date);
        dest.writeString(location_type);
        dest.writeParcelable(address, flags);
        dest.writeParcelable(location, flags);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public RatSighting createFromParcel(Parcel in) {
            return new RatSighting(in);
        }

        @Override
        public RatSighting[] newArray(int size) {
            return new RatSighting[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
