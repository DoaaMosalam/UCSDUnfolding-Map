package Demos;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.awt.*;
import java.security.Key;

public class MyPApplet extends PApplet {
    // You can ignore this.  It's to get rid of eclipse warnings
    private static final long serialVersionUID = 1L;

    // IF YOU ARE WORKING OFFILINE, change the value of this variable to true
    private static final boolean offline = false;

    /** This is where to find the local tiles, for working without an Internet connection */
    public static String mbTilesString = "blankLight-1-3.mbtiles";

    private  int value=0;

    //feed with magnitude 2.5+ Earthquakes
    private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
    UnfoldingMap map;
    public void setup(){
        size(800,600,JAVA2D);
        map = new UnfoldingMap(this,50,50,700,500,new Microsoft.HybridProvider());
 //      map.zoomToLevel(2);
        MapUtils.createDefaultEventDispatcher(this,map);
       //keyPressed();
        mouseReleased();

    }

    public void draw(){
        map.draw();
     //   addKey();
      drawButton();

    }

    public void drawButton(){
       if (key == 'b' ||key == 'B'){
           fill(255,255,255);
           rect(100,100,25,25);
           fill(100,100,100);
           rect(100,150,25,25);
       }
    }

    public void mousePressed(){

        if (value==0){
            value = 255;
        }else {
            value=0;
        }

    }

    public void mouseClicked(){
        if (value ==0){
            value =255;
        }else {
            value=0;
        }

    }

    public void mouseReleased(){
        if (mouseX>100 && mouseX < 125 && mouseY >100 && mouseY < 125){
            background(255,255,255);
        }else if (mouseX >100 && mouseX <125 && mouseY >150 && mouseY <175)
            background(100,100,100);

    }


    public void addKey(){
        fill(255,255,255);
        rect(100,100,25,25);
        fill(100,100,100);
        rect(100,150,25,25);
    }
    public static void main(String[]args){
        MyPApplet my = new MyPApplet();
        PApplet.runSketch(new String[]{"My PApplet----"},my);
       // PApplet.main(new String(MyPApplet.class.getName()));
    }
}
