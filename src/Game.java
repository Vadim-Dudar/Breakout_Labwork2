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

    // Add FPS constant and derived pause (milliseconds per frame).
    // pauseMs is computed using the formula: pauseMs = round(1000.0 / FPS)
    private static final int FPS = 20;
    private static final int PAUSE_MS = (int) Math.round(1000.0 / FPS);

    private double w, h;
    private GRect racket;

    public void run(){
        setSize(WIDTH, HEIGHT);
        w = getWidth(); h = getHeight();
        Message.setWidth(w);
        Message.setHeight(h);

        racket = new GRect(w-BRiCK_WIDTH*0.75, h-70, BRiCK_WIDTH*1.5, BRiCK_HEIGHT);
        racket.setFilled(true);
        racket.setFillColor(Color.BLACK);
        add(racket);

        playSound("victory");
        Message message = new Message("hi", "How are you");
        add(message);

        addMouseListeners();
    }

    public void mouseMoved(MouseEvent e) {
        racket.setLocation(e.getX()-BRiCK_WIDTH*0.75, racket.getY());
    }

    /**
     * Play a WAV sound file located in the user's downloads folder via
     * the Applet AudioClip API. The filename base (without .wav)
     * is provided as `soundName`.
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

    /**
     * Returns the pause (milliseconds) you should sleep between frames to
     * achieve the requested FPS. Formula: pauseMs = round(1000.0 / fps).
     *
     * Example: for FPS = 20 -> pauseMs = 1000 / 20 = 50 ms.
     *
     * @param fps desired frames per second (must be > 0)
     * @return milliseconds to sleep between frames (rounded)
     * @throws IllegalArgumentException if fps &lt;= 0
     */
    public static int pauseForFps(int fps) {
        if (fps <= 0) throw new IllegalArgumentException("fps must be > 0");
        return (int) Math.round(1000.0 / fps);
    }

    /**
     * Converts a speed expressed in pixels-per-second into pixels-per-frame
     * for the given FPS. Formula: pxPerFrame = speedPxPerSec / fps.
     *
     * This is useful when you update object positions once per frame and want
     * consistent motion regardless of FPS.
     *
     * @param speedPxPerSec speed in pixels per second
     * @param fps frames per second used by the game loop (must be > 0)
     * @return pixels to move each frame (can be fractional)
     * @throws IllegalArgumentException if fps &lt;= 0
     */
    public static double pixelsPerFrame(double speedPxPerSec, int fps) {
        if (fps <= 0) throw new IllegalArgumentException("fps must be > 0");
        return speedPxPerSec / fps;
    }

    /**
     * Computes the X and Y components of a velocity given a speed in pixels/sec
     * and an angle in degrees. Coordinates follow typical screen coordinates
     * where X increases to the right and Y increases downward. The angle is
     * measured from the positive X axis and positive angles rotate clockwise
     * (so 0 = right, 90 = down, 180 = left, 270 = up).
     *
     * Example: speed=100 px/s, angle=90 -> {0, 100} (moving down at 100 px/s).
     *
     * @param speedPxPerSec magnitude of velocity in pixels per second
     * @param angleDegrees angle in degrees measured from +X axis, clockwise
     * @return double array {speedX, speedY} in pixels per second
     */
    public static double[] speedComponents(double speedPxPerSec, double angleDegrees) {
        double rad = Math.toRadians(angleDegrees);
        double sx = speedPxPerSec * Math.cos(rad);
        double sy = speedPxPerSec * Math.sin(rad);
        return new double[]{sx, sy};
    }

    /**
     * Computes the per-frame delta X and delta Y (in pixels) for an object
     * moving at the given speed (pixels/sec) and angle, using the provided FPS.
     * Internally this computes speed components (px/sec) and divides them by
     * fps to produce per-frame movement.
     *
     * Example: speed=200 px/s, angle=0, fps=20 -> per-frame dx=10, dy=0.
     *
     * @param speedPxPerSec magnitude of velocity in pixels per second
     * @param angleDegrees angle in degrees measured from +X axis, clockwise
     * @param fps frames per second used to update positions (must be > 0)
     * @return double array {dxPerFrame, dyPerFrame} in pixels per frame
     * @throws IllegalArgumentException if fps &lt;= 0
     */
    public static double[] perFrameDeltas(double speedPxPerSec, double angleDegrees, int fps) {
        if (fps <= 0) throw new IllegalArgumentException("fps must be > 0");
        double[] comps = speedComponents(speedPxPerSec, angleDegrees);
        return new double[]{comps[0] / fps, comps[1] / fps};
    }
}
