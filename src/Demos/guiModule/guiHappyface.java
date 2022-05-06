package Demos.guiModule;

import javax.swing.*;
import java.awt.*;

public class guiHappyface extends JPanel {
    //constructor.
//   public guiHappyface(){
//        dispalyHappyFace();
//    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(0xFFFB00));
        g.fillOval(150,200,200,200);
        //draw  the eyes.
        g.setColor(Color.BLACK);
        g.fillOval( 200, 250, 30, 30 );
        g.fillOval( 270, 250, 30, 30 );

        // draw the mouth
        g.fillOval( 200, 300, 100, 60 );
// "touch up" the mouth into a smi

        g.setColor( Color.YELLOW );
        g.fillRect( 200, 300, 100, 30 );
        g.fillOval( 200, 300, 100, 40 );
    }
    public void dispalyHappyFace(){
        //setSize canves.
        guiHappyface happy = new guiHappyface();
        JFrame app = new JFrame();
        app.setSize(500,600);
        app.setBackground(new Color(0x66FFFF));
        app.setVisible(true);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setLocation(400,150);
        app.add(happy);

    }

    public static void main(String[]args){
        guiHappyface happyface = new guiHappyface();
        happyface.dispalyHappyFace();
    }
}
