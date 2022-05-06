package Demos;

public class Airport {
   private  int id;
   private String airportName;
   private String city;
   private String country;
   private String code3;
   private String code4;
   private double latitude;
   private  double longitude;
   private int altitude;
   private float timeZone;
   private  char dst;
   private String dbTimeZone;

   //constructor.
    public Airport(int id, String airportName, String city, String country,
                   String code3, String code4, double latitude, double longitude,
                   int altitude, float timeZone, char dst, String dbTimeZone) {
        this.id = id;
        this.airportName = airportName;
        this.city = city;
        this.country = country;
        this.code3 = code3;
        this.code4 = code4;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.timeZone = timeZone;
        this.dst = dst;
        this.dbTimeZone = dbTimeZone;
    }
    //Setter and Getter.

    public int getId() {
        return id;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getCode3() {
        return code3;
    }

    public String getCode4() {
        return code4;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public float getTimeZone() {
        return timeZone;
    }

    public char getDst() {
        return dst;
    }

    public String getDbTimeZone() {
        return dbTimeZone;
    }

    public int compareTo(Airport other)
    {
        return this.city.compareTo(other.city);
    }

    //ToString
    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", airportName='" + airportName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", code3='" + code3 + '\'' +
                ", code4='" + code4 + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", timeZone=" + timeZone +
                ", dst=" + dst +
                ", dbTimeZone='" + dbTimeZone + '\'' +
                '}';
    }
}
