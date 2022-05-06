package module3;

//Java utilities libraries
import java.awt.*;
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.providers.Microsoft;
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Doaa Mosalam.
 * Date: June 3, 2021
 * */
public class EarthquakeCityMap extends PApplet  {
    // You can ignore this.  It's to keep eclipse from generating a warning.
    private static final long serialVersionUID = 1L;

    // IF YOU ARE WORKING OFFLINE, change the value of this variable to true
    private static final boolean offline = false;

    // Less than this threshold is a light earthquake
    public static final float THRESHOLD_MODERATE = 5;
    // Less than this threshold is a minor earthquake
    public static final float THRESHOLD_LIGHT = 4;

    /** This is where to find the local tiles, for working without an Internet connection */
    public static String mbTilesString = "blankLight-1-3.mbtiles";

    // The map
    private UnfoldingMap map;

    //feed with magnitude 2.5+ Earthquakes
   private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
 //   private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.atom";



    public void setup() {
        size(950, 600, JAVA2D);

        if (offline) {
            map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
         //   earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
            earthquakesURL ="all_month.atom";

        }
        else {
            map = new UnfoldingMap(this, 200, 50, 700, 500, new Microsoft.HybridProvider());
            // IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
            //earthquakesURL = "2.5_week.atom";
        }

        map.zoomToLevel(2);
        MapUtils.createDefaultEventDispatcher(this, map);

        // The List you will populate with new SimplePointMarkers
        List<Marker> markers = new ArrayList<Marker>();

        //Use provided parser to collect properties for each earthquake
        //PointFeatures have a getLocation method
        // These print statements show you (1) all of the relevant properties
        // in the features, and (2) how to get one property and use it
        List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
        if (earthquakes.size()>0){
            PointFeature f = earthquakes.get(0);
            System.out.println(f.getProperties());
            Object magObj = f.getProperty("magnitude");
            float mag = Float.parseFloat(magObj.toString());
            // PointFeatures also have a getLocation method
            System.out.println(f.getLocation() + " | " + mag);
        }
//this code marker earthquake magnitude red,yellow,blue.
        for (PointFeature eq: earthquakes) {
         //   markers.add(new SimplePointMarker(eq.getLocation(),eq.getProperties()));
            SimplePointMarker mark = createMarker(eq);
            markers.add(mark);
        }
        // Add the markers to the map so that they are displayed
        map.addMarkers(markers);
    }

    private SimplePointMarker createMarker(PointFeature feature){

        // Create a new SimplePointMarker at the location given by the PointFeature
        SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
        //This code get magnitude earthequake and location earthquake.
        Object magObj = feature.getProperty("magnitude");
        Object magLocation = feature.getLocation();
        float mag = Float.parseFloat(magObj.toString());
           System.out.println( magLocation + " magnitude earthquake is: " + mag );
        //==========================================================================================================
        // Here is an example of how to use Processing's color method to generate
        // an int that represents the Color res,Color  color yellow.
        int red = color(255,0,0);
        int yellow = color(255, 255, 0);
        int blue = color(0,0,255);
        if (mag>THRESHOLD_MODERATE){
            marker.setColor(red);
            marker.setRadius(15);
        }else if (mag > THRESHOLD_LIGHT){
            marker.setColor(yellow);
            marker.setRadius(10);
        }else if (mag < THRESHOLD_LIGHT){
            marker.setColor(blue);
            marker.setRadius(5);
        }
        // Finally return the marker
        return marker;
    }
    public void draw() {
        background(10);
        map.draw();
        addKey();
    }



    // helper method to draw key in GUI
    // TODO: Implement this method to draw the key
    private void addKey() {
        // Remember you can use Processing's graphics methods here
        // draw canvas
        //variable helper to draw key rectangle
        int  xRec = 10 ;int yRect = 10;
        int widthRect =150;
        int heightRect = 260;
        fill(255,255,255);
      rect(xRec,yRect,widthRect,heightRect,7);
        stroke(51,51,255);
        fill(0,0,204);
        textSize(14);
        text("Earthquake Key",40,50,7);
        line(40,55,150,55);
        //draw  for red markers
        fill(255,0,0,204);
        ellipse(32,85,15,15);
        fill(0,0,0);
        textSize(12);
        text("5.0+Magnitude",45,90,7);
        //draw  for yellow marker
        fill(255,255,0);
        ellipse(32,150,10,10);
        fill(0,0,0);
        textSize(12);
        text("4.5+Magnitude",45,155,7);
        //draw for blue marker
        fill(0,0,204);
        ellipse(32,220,5,5);
        fill(0,0,0);
        textSize(12);
        text("Below 4.0",45,225,7);
    }
    //Method run this class
    public static void main(String[] args) {
        PApplet.main(new String(EarthquakeCityMap.class.getName()));
    }
}