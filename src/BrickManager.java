import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.*;

public class BrickManager {

    // Brick constants
    private static final int BRICK_WIDTH = 60;   // width of a single brick in pixels
    private static final int BRICK_HEIGHT = 20;  // height of a single brick in pixels
    private static final int ROWS = 5;           // number of brick rows
    private static final int COLS = 10;          // number of brick columns

    /**
     * Creates and adds bricks to the given GraphicsProgram canvas.
     *
     * @param gp the GraphicsProgram to which the bricks are added
     */
    public void initBricks(GraphicsProgram gp) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                GRect brick = new GRect(
                        col * (BRICK_WIDTH + 5) + 50,
                        row * (BRICK_HEIGHT + 5) + 100,
                        BRICK_WIDTH,
                        BRICK_HEIGHT
                );
                brick.setFilled(true);
                brick.setFillColor(Color.BLUE);
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
