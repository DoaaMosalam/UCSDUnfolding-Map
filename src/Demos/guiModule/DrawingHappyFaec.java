package Demos.guiModule;

import processing.core.PApplet;

public class DrawingHappyFaec extends PApplet {

    public void setup(){
//set a canves size
        size(400,400);
        //set loaction canvas in center window.
        setLocation(450,150);
        //set Color
        background(200,200,200);
    }

    public void draw(){
        text("Smail Face....",5,3);
        textSize(30);
        fill(247,255,0);
        ellipse(200,200,390,390);
        fill(0,0,0);
        ellipse(120,150,50,70);
        ellipse(280,150,50,70);
        // if i want mouse empty.
        noFill();
        arc(200, 280, 100, 75, 0, PI);
    }

    public static void main(String[]args){
        DrawingHappyFaec display = new DrawingHappyFaec();
        PApplet.runSketch(new String[]{"Happy Face..."},display);
    }
}
