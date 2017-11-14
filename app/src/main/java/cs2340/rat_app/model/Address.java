package cs2340.rat_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {

    //instance variables
    private String street;
    private String borough;
    private int zip;
    private String city;

    /**
     * default constructor
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

    /**
     * Street getter
     * @return street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Street setter
     * @param street being set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Borough getter
     * @return borough
     */
    public String getBorough() {
        return borough;
    }

    /**
     * Borough setter
     * @param borough being set
     */
    public void setBorough(String borough) {
        this.borough = borough;
    }

    /**
     * ZipCode getter
     * @return ZipCode
     */
    public int getZip() {
        return zip;
    }

    /**
     * ZipCode setter
     * @param zip ZipCode being set
     */
    public void setZip(int zip) {
        this.zip = zip;
    }

    /**
     * City getter
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * City setter
     * @param city city being set
     */
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
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

}
