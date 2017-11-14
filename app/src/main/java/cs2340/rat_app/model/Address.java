package cs2340.rat_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {

    //instance variables
    private final String street;
    private final String borough;
    private final int zip;
    private final String city;

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
     * Borough getter
     * @return borough
     */
    public String getBorough() {
        return borough;
    }

    /**
     * ZipCode getter
     * @return ZipCode
     */
    public int getZip() {
        return zip;
    }

    /**
     * City getter
     * @return city
     */
    public String getCity() {
        return city;
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
