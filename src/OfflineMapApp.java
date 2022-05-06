import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class OfflineMapApp extends PApplet {
    public static String mbTilesString = "blankLight-1-3.mbtiles";

    UnfoldingMap map;

    public void setup() {
        size(800, 600, JAVA2D);

        map = new UnfoldingMap(this, new MBTilesMapProvider(mbTilesString));
        MapUtils.createDefaultEventDispatcher(this, map);
        map.setZoomRange(1, 3);
    }

    public void draw() {
        background(0);
        map.draw();
    }
}
