package cs2340.rat_app.model;

/**
 * Created by Borja Vicinay on 10/9/2017.
 */

public class Address {

    private String street;
    private String borough;
    private int zip;
    private String city;

    public Address(String street, String borough, int zip, String city) {
        this.street = street;
        this.borough = borough;
        this.zip = zip;
        this.city = city;
    }

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
}
