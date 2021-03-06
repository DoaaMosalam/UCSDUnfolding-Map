package module5;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeCityMap extends PApplet {

    // We will use member variables, instead of local variables, to store the data
    // that the setup and draw methods will need to access (as well as other methods)
    // You will use many of these variables, but the only one you should need to add
    // code to modify is countryQuakes, where you will store the number of earthquakes
    // per country.

    // You can ignore this.  It's to get rid of eclipse warnings
    private static final long serialVersionUID = 1L;

    // IF YOU ARE WORKING OFFILINE, change the value of this variable to true
    private static final boolean offline = false;

    //feed with magnitude 2.5+ Earthquakes
    private String earthquakesURL =
            "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

    // The map
    private UnfoldingMap map;

    // Markers for each city
    private List<Marker> cityMarkers;
    // Markers for each earthquake
    private List<Marker> quakeMarkers;

    // A List of country markers
    private List<Marker> countryMarkers;
    private List<PointFeature> earthquakes;

    // NEW IN MODULE 5
    private CommonMarker lastSelected;
    private CommonMarker lastClicked;

    public void setup() {
        // (1) Initializing canvas and map tiles
        size(900, 700, JAVA2D);
        if (offline) {
            /* This is where to find the local tiles, for working without an Internet connection */
            String mbTilesString = "blankLight-1-3.mbtiles";
            map = new UnfoldingMap(this, 200, 50, 650, 600, new MBTilesMapProvider(mbTilesString));
            earthquakesURL = "2.5_week.atom";  // The same feed, but saved August 7, 2015
        } else {
            //AbstractMapProvider provider = new Google.GoogleMapProvider();
            AbstractMapProvider provider = new Microsoft.RoadProvider();
            map = new UnfoldingMap(this, 200, 50, 650, 600, provider);
            // IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
            earthquakesURL = "2.5_week.atom";
        }
        MapUtils.createDefaultEventDispatcher(this, map);


        // (2) Reading in earthquake data and geometric properties
        //     STEP 1: load country features and markers
        String countryFile = "countries.geo.json";
        List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
        countryMarkers = MapUtils.createSimpleMarkers(countries);

        //     STEP 2: read in city data
        String cityFile = "city-data.json";
        List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
        cityMarkers = new ArrayList<>();
        for(Feature city : cities) {
            cityMarkers.add(new CityMarker(city));
        }

        //     STEP 3: read in earthquake RSS feed
         earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
        quakeMarkers = new ArrayList<>();

        for(PointFeature feature : earthquakes) {
            //check if LandQuake
            if(isLand(feature)) {
                quakeMarkers.add(new LandQuakeMarker(feature));
            }
            // OceanQuakes
            else {
                quakeMarkers.add(new OceanQuakeMarker(feature));
            }
        }

        // could be used for debugging
        printQuakes();

        // (3) Add markers to map
        //     NOTE: Country markers are not added to the map.  They are used
        //           for their geometric properties
        map.addMarkers(quakeMarkers);
        map.addMarkers(cityMarkers);

    }


    public void draw() {
        background(0);
        map.draw();
        addKey();

    }

    /**
     * Event handler that gets called automatically when the mouse moves.
     */
    @Override
    public void mouseMoved()
    {
        // clear the last selection
        if (lastSelected != null) {
            lastSelected.setSelected(false);
            lastSelected = null;

        }
        selectMarkerIfHover(quakeMarkers);
        selectMarkerIfHover(cityMarkers);
    }

    /**
     * Select a marker if it's under the cursor.
     * @param markers the list of markers
     */
    private void selectMarkerIfHover(List<Marker> markers) {
        // If there is a marker under the cursor, and lastSelected is null,
        // set the lastSelected to be the first marker found under the cursor.
        // Make sure you do not select two markers.

        // DONE: Implement this method
        if (lastSelected == null) {
            for (Marker marker: markers) {
                if (! marker.isSelected() && marker.isInside(map, mouseX, mouseY)) {
                    lastSelected = (CommonMarker) marker;
                    marker.setSelected(true);
                    break;
                }
            }
        }
    }

    /** The event handler for mouse clicks.
     * It will display an earthquake and its threat circle of cities.
     * Or if a city is clicked, it will display all the earthquakes
     * where the city is in the threat circle.
     */
    @Override
    public void mouseClicked() {
        // DONE: Implement this method
        // Hint: You probably want a helper method or two to keep this code
        // from getting too long/disorganized.

        if (lastClicked != null) {
            if (lastSelected != null) {
                lastSelected.setSelected(false);
                lastSelected = null;
            }
            unhideMarkers();
            lastClicked = null;
        } else {
            selectMarkerIfHover(cityMarkers);
            selectMarkerIfHover(quakeMarkers);
            if (lastSelected != null) {
                lastClicked = lastSelected;
                hideMarkers(lastClicked);
            }
        }
    }

    /**
     * Unhide all markers
     */
    private void unhideMarkers() {
        for(Marker marker : quakeMarkers) {
            marker.setHidden(false);
        }

        for(Marker marker : cityMarkers) {
            marker.setHidden(false);
        }
    }

    /**
     * Hide a given marker
     * @param refMarker the marker to hide
     */
    private void hideMarkers(Marker refMarker) {
        for (Marker marker: quakeMarkers) {
            if (! marker.equals(refMarker)) {
                if (refMarker instanceof CityMarker) {
                    if (! isThreat((EarthquakeMarker) marker,
                            (CityMarker) refMarker)) {
                        marker.setHidden(true);
                    } else {
                        marker.setHidden(false);
                    }
                } else {
                    marker.setHidden(true);
                }
            }
        }
        for (Marker marker: cityMarkers) {
            if (! marker.equals(refMarker)) {
                if (refMarker instanceof EarthquakeMarker) {
                    if (! isThreatened((CityMarker)marker,
                            (EarthquakeMarker) refMarker)) {
                        marker.setHidden(true);
                    } else {
                        marker.setHidden(false);
                    }
                } else {
                    marker.setHidden(true);
                }
            }
        }
    }

    /**
     * Determine if a city is threatened by an earthquoke
     * @param marker the city marker
     * @param threat the earthquake marker
     * @return true if the city is threatened by the earthquake
     * 		   fale otherwise
     */
    private boolean isThreatened(CityMarker marker, EarthquakeMarker threat) {
        double distance = threat.threatCircle();
        return marker.getDistanceTo(threat.getLocation()) < distance;
    }

    /**
     * Determine whether the earthquake is a threat for a city
     * @param threat the earthquake marker
     * @param marker the city marker
     * @return true if the earthquake is a threat to the city
     * 		   false otherwise
     */
    private boolean isThreat(EarthquakeMarker threat, CityMarker marker) {
        double distance = threat.threatCircle();
        return threat.getDistanceTo(marker.getLocation()) < distance;
    }

    /**
     * Helper method to draw key in GUI
     */
    private void addKey() {
        // Remember you can use Processing's graphics methods here
        fill(255, 250, 240);

        int xbase = 25;
        int ybase = 50;

        rect(xbase, ybase, 150, 250);

        fill(0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("Earthquake Key", xbase+25, ybase+25);

        fill(150, 30, 30);
        int tri_xbase = xbase + 35;
        int tri_ybase = ybase + 50;
        triangle(tri_xbase, tri_ybase-CityMarker.TRI_SIZE, tri_xbase-CityMarker.TRI_SIZE,
                tri_ybase+CityMarker.TRI_SIZE, tri_xbase+CityMarker.TRI_SIZE,
                tri_ybase+CityMarker.TRI_SIZE);

        fill(0, 0, 0);
        textAlign(LEFT, CENTER);
        text("City Marker", tri_xbase + 15, tri_ybase);

        text("Land Quake", xbase+50, ybase+70);
        text("Ocean Quake", xbase+50, ybase+90);
        text("Size ~ Magnitude", xbase+25, ybase+110);

        fill(255, 255, 255);
        ellipse(xbase+35,
                ybase+70,
                10,
                10);
        rect(xbase+35-5, ybase+90-5, 10, 10);

        fill(color(255, 255, 0));
        ellipse(xbase+35, ybase+140, 12, 12);
        fill(color(0, 0, 255));
        ellipse(xbase+35, ybase+160, 12, 12);
        fill(color(255, 0, 0));
        ellipse(xbase+35, ybase+180, 12, 12);

        textAlign(LEFT, CENTER);
        fill(0, 0, 0);
        text("Shallow", xbase+50, ybase+140);
        text("Intermediate", xbase+50, ybase+160);
        text("Deep", xbase+50, ybase+180);

        text("Past hour", xbase+50, ybase+200);

        fill(255, 255, 255);
        int centerx = xbase+35;
        int centery = ybase+200;
        ellipse(centerx, centery, 12, 12);

        strokeWeight(2);
        line(centerx-8, centery-8, centerx+8, centery+8);
        line(centerx-8, centery+8, centerx+8, centery-8);

    }

    /**
     * Determine whether the earthquake occurred on land.
     * @param earthquake the feature to check
     * @return true if it is on lonad
     * 		   false otherwise
     */
    private boolean isLand(PointFeature earthquake) {
        // If this quake occurred on land, set the "country" property of its
        // PointFeature to the country where it occurred and return true.
        // Notice that the helper method isInCountry will set this "country" property already.
        // Otherwise, return false.

        // DONE: loop over all countries to check if location is in any of them.
        // If it is, add 1 to the entry in countryQuakes corresponding to this country.
        for (Marker country : countryMarkers) {
            if (isInCountry(earthquake, country)) {
                return true;
            }
        }

        // not inside any country
        return false;
    }

    /**
     * Print countries with number of earthquakes
     */
    private void printQuakes() {
        int countEarthquake = 0;
        for (Marker cm: countryMarkers) {
            String name = (String) cm.getProperty("name");
            for (PointFeature qm : earthquakes){
                String country = (String) qm.getProperty("country");
                if (name.equals(country)){
                    countEarthquake++;
                }
            }
            if (countEarthquake > 0){
                System.out.println(name+ ":" + countEarthquake);
            }
            countEarthquake =0;
        }
        int earthquakeOcean = 0;
        for (PointFeature qo:earthquakes) {
            if (! isLand(qo)){
                earthquakeOcean++;
            }
        }
        System.out.println("Earthquake Ocean : " + earthquakeOcean);

    }

    /**
     * Determine whether a given earthquake is in a given country
     * @param earthquake the earthquake feature to check
     * @param country the coutnry marker
     * @return true if the feature is in the country
     * 		   false otherwise
     */
    private boolean isInCountry(PointFeature earthquake, Marker country) {
        // This will add the country property to the properties of the earthquake
        // feature if it's in one of the countries.
        // You should not have to modify this code.

        // getting location of feature
        Location checkLoc = earthquake.getLocation();

        // some countries represented it as MultiMarker
        // looping over SimplePolygonMarkers which make them up to use isInsideByLoc
        if(country.getClass() == MultiMarker.class) {

            // looping over markers making up MultiMarker
            for(Marker marker : ((MultiMarker)country).getMarkers()) {

                // checking if inside
                if(((AbstractShapeMarker)marker).isInsideByLocation(checkLoc)) {
                    earthquake.addProperty("country", country.getProperty("name"));

                    // return if is inside one
                    return true;
                }
            }
        }

        // check if inside country represented by SimplePolygonMarker
        else if(((AbstractShapeMarker)country).isInsideByLocation(checkLoc)) {
            earthquake.addProperty("country", country.getProperty("name"));

            return true;
        }
        return false;
    }
    public static void  main(String[]args){
        PApplet.main(new String(EarthquakeCityMap.class.getName()));
    }

}
