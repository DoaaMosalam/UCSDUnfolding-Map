package module5;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

public class LandQuakeMarker extends EarthquakeMarker {
    /**
     * Constructor
     *
     * @param feature this marker's point feature
     */
    public LandQuakeMarker(PointFeature feature) {
        // calling EarthquakeMarker constructor
        super(feature);
        // setting field in earthquake marker
        isOnLand = true;
    }

    @Override
    public void drawEarthquake(PGraphics pg, float x, float y) {
        pg.ellipse(x,y,2*radius,2*radius);
    }
}