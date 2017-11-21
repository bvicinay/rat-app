package cs2340.rat_app.model;

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

    /**
     * Constructor called from a ratRighting
     * @param sighting the ratSighting being constructed from
     */
    public RatSightingRaw(RatSighting sighting) {
        RatSightingRaw rat = RatSighting.makeRawSighting(sighting);
        key = rat.getKey();
        borough = sighting.getAddress().getBorough();
        city = sighting.getAddress().getCity();
        created_date = sighting.getDateStr();
        incident_address = sighting.getAddress().getStreet();
        incident_zip = Integer.toString(sighting.getAddress().getZip());
        location_type = sighting.getLocation_type();
        latitude = Double.toString(sighting.getLocation().getLatitude());
        longitude = Double.toString(sighting.getLocation().getLongitude());

    }

    /**
     * No-arg constructor for firebase
     */
    public RatSightingRaw(){

    }

    /**
     * Returns String of location_type
     * @return location_type
     */
    public String getLocation_type() {
        return location_type;
    }

    /**
     * returns key of string
     * @return key
     */
    public String getKey() { return key; }

    /**
     * return string of borough
     * @return borough
     */
    public String getBorough() {
        return borough;
    }

    /**
     * return string of city
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * returns string of creation_date
     * @return created_date
     */
    public String getCreated_date() {
        return created_date;
    }

    /**
     * returns string of address
     * @return address
     */
    public String getIncident_address() {
        return incident_address;
    }

    /**
     * returns string of zip_code
     * @return zip_code
     */
    public String getIncident_zip() {
        return incident_zip;
    }

    /**
     * returns string of latitude
     * @return latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * returns string of longitude
     * @return longitude
     */
    public String getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Rat Sighting on: " + incident_address;
    }

}
