package module4;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

import java.awt.*;

public class CityMarker extends SimplePointMarker {
    // The size of the triangle marker
    // It's a good idea to use this variable in your draw method
    public static final int TRI_SIZE = 8;

    public CityMarker(Location location){
        super(location);
    }

    public CityMarker(Feature city){
            super(((PointFeature)city).getLocation(), city.getProperties());
        }

    /**
     * Implementation of method to draw marker on the map.
     */
    public void draw(PGraphics pg, float x, float y) {
        // Save previous drawing style
        pg.pushStyle();
        int red = pg.color(255,0,0);
        int green = pg.color(0,255,0);
        pg.fill(green);
        pg.triangle(x,y,x-TRI_SIZE,y+TRI_SIZE,x+TRI_SIZE,y+TRI_SIZE);
        pg.popStyle();
    }

    /* Local getters for some city properties.  You might not need these
     * in module 4. 	 */
    public String getCity()
    {
        return getStringProperty("name");
    }

    public String getCountry()
    {
        return getStringProperty("country");
    }

    public float getPopulation()
    {
        return Float.parseFloat(getStringProperty("population"));
    }


}
