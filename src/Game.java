import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import java.awt.*;
import java.awt.event.MouseEvent;

import java.applet.Applet;
import java.applet.AudioClip;

public class Game extends GraphicsProgram {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int FPS = 40;

    private static final int BRiCK_WIDTH = 50;
    private static final int BRiCK_HEIGHT = 20;
    private double speedX = 0;
    private double speedY = 170;
    private boolean speedsConverted = false;

    private static RandomGenerator generator = RandomGenerator.getInstance();

    private double w, h;
    private GRect racket;
    private GOval ball;

    public void run(){
        setSize(WIDTH, HEIGHT);
        setDefault();
        addMouseListeners();

        final int pauseMs = (int) Math.max(1, Math.round(1000.0 / FPS));

        while (ball.getY()<h) {
            moveBall();
            pause(pauseMs);
        }
        println("Game over!");
    }

    /**
     * Mouse movement handler - moves the racket horizontally to follow the mouse.
     *
     * @param e mouse event containing the current pointer coordinates
     */
    public void mouseMoved(MouseEvent e) {
        racket.setLocation(e.getX()-BRiCK_WIDTH*0.75, racket.getY());
    }

    /**
     * Creates and returns a filled circle-shaped GOval centered at (x,y).
     *
     * @param x center x-coordinate in pixels
     * @param y center y-coordinate in pixels
     * @param r radius in pixels
     * @return a configured filled GOval representing the ball
     */
    private GOval GCircle(double x, double y, double r) {
        GOval oval = new GOval(x-r, y-r, 2*r, 2*r);
        oval.setFilled(true);
        oval.setFillColor(Color.BLACK);
        return oval;
    }

    /**
     * Advances the ball position by current per-frame speed components
     * and performs collision/bounce handling.
     * Speeds are in pixels per frame.
     */
    private void moveBall(){
        ball.setLocation(ball.getX()+speedX, ball.getY()+speedY);
        bounceBall();
    }

    /**
     * Handles collisions of the ball with the window edges and the racket.
     * Reverses horizontal speed when hitting left/right walls and reverses
     * vertical speed when hitting the top. If the ball collides with the racket
     * the vertical direction is inverted and a (possibly random) horizontal
     * speed is applied when current horizontal speed is zero.
     */
    private void bounceBall() {
        if (ball.getX() < 0 || ball.getX()+ball.getWidth() > w) {
            if (speedX * FPS < 200) speedX *= -1.1;
            else speedX *= 1;
        }
        if (ball.getY() < 0) {
            if (speedY * FPS < 200) speedY *= -1.1;
            else speedY *= 1;;
        }
        if (checkColision(ball, racket)) {
            speedY *= -1;
            if (speedX == 0) speedX = generator.nextInt(-170, 170) / (FPS*1.);
        }
    }

    /**
     * Tests axis-aligned bounding-box intersection between two GObjects.
     *
     * @param a first object
     * @param b second object
     * @return {@code true} if the two objects overlap, {@code false} otherwise
     */
    private boolean checkColision(GObject a, GObject b) {
        double ax = a.getX(), ay = a.getY(), aw = a.getWidth(), ah = a.getHeight();
        double bx = b.getX(), by = b.getY(), bw = b.getWidth(), bh = b.getHeight();
        return ax < bx + bw && ax + aw > bx && ay < by + bh && ay + ah > by;
    }

    /**
     * Initializes default game objects and converts speeds set in pixels-per-second
     * to pixels-per-frame by dividing by {@code FPS}. This method must be called
     * before the main loop runs.
     */
    private void setDefault() {
        w = getWidth(); h = getHeight();
        if (!speedsConverted) {
            speedX = speedX / FPS;
            speedY = speedY / FPS;
            speedsConverted = true;
        }

        racket = new GRect(w-BRiCK_WIDTH*0.75, h-70, BRiCK_WIDTH*1.5, BRiCK_HEIGHT);
        racket.setFilled(true);
        racket.setFillColor(Color.BLACK);
        add(racket);

        ball = GCircle(w/2, h-250, BRiCK_HEIGHT);
        add(ball);
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
