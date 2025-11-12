import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

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

        addMouseListeners();
    }

    public void mouseMoved(MouseEvent e) {
        racket.setLocation(e.getX()-BRiCK_WIDTH*0.75, racket.getY());
    }
}
