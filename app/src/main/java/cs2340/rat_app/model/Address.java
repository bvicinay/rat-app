package cs2340.rat_app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Borja Vicinay on 10/9/2017.
 */

public class Address implements Parcelable {

    //instance variables
    private String street;
    private String borough;
    private int zip;
    private String city;

    /**
     * default construstor
     * @param street the street- String
     * @param borough the borough- String
     * @param zip the zip- int
     * @param city the city- string
     */
    public Address(String street, String borough, int zip, String city) {
        this.street = street;
        this.borough = borough;
        this.zip = zip;
        this.city = city;
    }

    /**
     * Constructor that is called when address object is passed via a parcel
     * @param p the parcel the object is passed through
     */
    public Address(Parcel p) {
        street = p.readString();
        borough = p.readString();
        zip = p.readInt();
        city = p.readString();
    }

    //Getters and setters
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(street);
        parcel.writeString(borough);
        parcel.writeInt(zip);
        parcel.writeString(city);
    }

    //For passing through an intent
    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

}
