import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.*;

public class BrickManager {

    // Brick constants
    private static final int BRICK_HEIGHT = 20;  // height of a single brick in pixels
    private static final int ROWS = 5;           // number of brick rows
    private static final int COLS = 10;          // number of brick columns
    private static final int GAP_X = 5;
    private static final int GAP_Y = 8;

    private final int APPLICATION_WIDTH;
    private final int APPLICATION_HEIGHT;
    private int BRICK_WIDTH;

    public BrickManager(int APPLICATION_WIDTH, int APPLICATION_HEIGHT) {
        this.APPLICATION_WIDTH = APPLICATION_WIDTH;
        this.APPLICATION_HEIGHT = APPLICATION_HEIGHT;

        // Adaptive brick width
        this.BRICK_WIDTH = (APPLICATION_WIDTH - (COLS - 1) * GAP_X) / COLS;
    }

    /**
     * Creates and adds bricks to the given GraphicsProgram canvas.
     *
     * @param gp the GraphicsProgram to which the bricks are added
     */
    public void initBricks(GraphicsProgram gp) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                GRect brick = new GRect(col * (BRICK_WIDTH + GAP_X), row * (BRICK_HEIGHT + GAP_Y) + 100, BRICK_WIDTH, BRICK_HEIGHT);
                brick.setFilled(true);

                switch (row % 5) {
                    case 0:
                        brick.setFillColor(Color.RED);
                        break;
                    case 1:
                        brick.setFillColor(Color.ORANGE);
                        break;
                    case 2:
                        brick.setFillColor(Color.YELLOW);
                        break;
                    case 3:
                        brick.setFillColor(Color.GREEN);
                        break;
                    case 4:
                        brick.setFillColor(Color.CYAN);
                        break;
                }
                gp.add(brick);
            }
        }
    }

    /**
     * Removes a brick from the given GraphicsProgram canvas.
     *
     * @param brick the brick to remove
     * @param gp the GraphicsProgram from which the brick is removed
     */
    public void removeBrick(GRect brick, GraphicsProgram gp) {
        gp.remove(brick);
    }

    /**
     * Checks collision between the ball and bricks.
     * Ignores any objects below the top of the platform to avoid deleting the platform.
     *
     * @param ball the GOval representing the ball
     * @param gp the GraphicsProgram containing all objects
     * @param platformTop the Y-coordinate of the top of the platform
     * @return the brick that the ball collided with, or null if no collision occurred
     */
    public GRect checkCollision(GOval ball, GraphicsProgram gp, double platformTop) {
        for (int i = 0; i < gp.getElementCount(); i++) {
            GObject obj = gp.getElement(i);

            if (obj instanceof GRect
                    && obj.getY() < platformTop
                    && ball.getBounds().intersects(obj.getBounds())) {
                return (GRect) obj;
            }
        }
        return null;
    }
}
