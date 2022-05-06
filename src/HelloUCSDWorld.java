import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class HelloUCSDWorld  extends PApplet {
    UnfoldingMap map;

    public void setup(){
        size(800,900,JAVA2D);
        map = new UnfoldingMap(this,new Google.GoogleTerrainProvider());
        map.zoomAndPanTo(14, new Location(32.881, -117.238)); // UCSD

        MapUtils.createDefaultEventDispatcher(this,map) ;
    }
    public void draw(){
        background(0);
        map.draw();
    }

    public static void main(String[]args){
        PApplet.main(new String(HelloUCSDWorld.class.getName()));
    }

}
