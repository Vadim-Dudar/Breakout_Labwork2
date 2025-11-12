import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

import java.applet.Applet;
import java.applet.AudioClip;

public class Game extends GraphicsProgram {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private static final int BRiCK_WIDTH = 50;
    private static final int BRiCK_HEIGHT = 20;

    private double w, h;
    private GRect racket;

    public void run(){
        setSize(WIDTH, HEIGHT);

        w = getWidth(); h = getHeight();
        racket = new GRect(w-BRiCK_WIDTH*0.75, h-70, BRiCK_WIDTH*1.5, BRiCK_HEIGHT);
        racket.setFilled(true);
        racket.setFillColor(Color.BLACK);
        add(racket);

        playSound("victory");

        addMouseListeners();
    }

    public void mouseMoved(MouseEvent e) {
        racket.setLocation(e.getX()-BRiCK_WIDTH*0.75, racket.getY());
    }

    /**
     * Play a WAV sound file located in the user's downloads folder via
     * the Applet AudioClip API. The filename base (without .wav)
     * is provided as `soundName`.
     *
     * Existing sound in project:
     *  - "victory" - Victory sound
     *  - "bounce" - ball bounce sound
     *  - "brick" - break brick sound
     *  - "build" - sound of brick building
     *
     * @param soundName base filename of the WAV
     */
    private void playSound(String soundName) {
        try {
            java.net.URL url = new java.io.File("media\\"+soundName+".wav").toURI().toURL();
            AudioClip sound = Applet.newAudioClip(url);
            sound.play();
        } catch (java.net.MalformedURLException e) {
            System.err.println("Failed to load bomb sound: " + e.getMessage());
        }
    }
}
