package Demos.SunInNature;

import processing.core.PApplet;
import processing.core.PImage;


// Creating a class which extends
// the PApplet class
public class SunInNature extends PApplet {
    // Defining the image
    private PImage ima;
    private String url = "https://upload.wikimedia.org/wikipedia/commons/8/8f/Bachalpsee_reflection.jpg";
    private String path = "D:\\Coders Programming\\Java\\Coursera\\Object Oriented Java Programming Data Structures and Beyond\\Course One\\My UCSDUnfoldingMaps_Master\\data\\";
    // The setup function will run once
    // and the draw function will run
    // repeatedly drawing the image
    // on the canvas.
    public void setup(){
        //// Set the canvas width and height
        size(500,700);
        // Set the canvas color
        background(255);
        // Set the pen color
        stroke(0);
          // Use any image from the internet
        // or PC
        // Here, the URL of the image is given
        ima = loadImage("D:\\Coders Programming\\Java\\Coursera\\Object Oriented Java Programming Data Structures and Beyond\\Course One\\My UCSDUnfoldingMaps_Master\\data\\palmTrees.jpg");
     //   set resize image int size canvese.
        ima.resize(0,height);
        //display the image.
        image(ima,0,0);
    }
    // This function is executed repeatedly
    public void draw(){
        int[] color = differentSuncolor(second());
        // Set the colour of the sun
        fill(color[0], color[1], color[2]);
        // Draw the sun
        ellipse(width / 4, height / 5,
                width / 4, height / 5);
    }
    // Function to return the RGB
    // color of the sun at the
    // number of seconds in the minute
    public int[]differentSuncolor(float second){
        int [] rgb = new int[3];

        // Scale the brightness of the
        // yellow based on the seconds.
        // 0 seconds is black and
        // 30 seconds is bright yellow.
        float different = Math.abs(30 -second);
        float ratio = different /30;
        // Assigning the ratio of
        // RGB in an array
        rgb[0] = (int) (ratio *255);
        rgb[1] = (int) (ratio*209);
        rgb[2] =0;
        return  rgb;
    }
    public static void main(String[]args){
        PApplet.main(new String(SunInNature.class.getName()));
    }


}
