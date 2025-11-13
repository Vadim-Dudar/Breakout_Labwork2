import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.*;

/**
 * A simple centered message box implemented as an ACM GCompound.
 * <p>
 * Usage:
 * - Before creating any Message instances, initialize the drawing area size by
 *   calling {@link Message#setWidth(double)} and {@link Message#setHeight(double)}
 *   with the application's canvas width and height (in pixels). The message
 *   box sizes and positions are computed from those values.
 * <p>
 * Create an instance with the main text and an additional (smaller) line:
 * <pre>
 * Message msg = new Message("Level Complete", "Press Enter to continue");
 * </pre>
 * Optionally pass a type to change the background color:
 * <pre>
 * // type: 0 = green (default), 1 = yellow, 2 = red
 * Message msg = new Message("Error", "Try again", 2);
 * </pre>
 * Then add the message to your ACM canvas (for example, an {@code GCanvas})
 * using {@code add(msg)}. The message is centered on the canvas and consists
 * of a colored rounded background, a white inner rectangle, a large main label
 * and a smaller additional label beneath it.
 *
 * Notes:
 * - The message width is computed as one third of the canvas width.
 * - The message height is computed as one fifth of the canvas height.
 * - Fonts are fixed in the implementation (approximate sizes "-30" and "-20").
 */
public class Message extends GCompound {
    private String main;
    private String additional;

    private GRect background;
    private GRect rect;

    private GLabel mainL;
    private GLabel additionalL;

    private int type = 0;

    private static double width;
    private static double height;
    private static double messageW;
    private static double messageH;

    /**
     * Creates a Message with the given main and additional text.
     * The message type defaults to 0 (green background).
     *
     * Preconditions:
     * - {@link Message#setWidth(double)} and {@link Message#setHeight(double)}
     *   should have been called with the canvas size before creating the
     *   instance; otherwise the message size/position may be incorrect.
     *
     * @param main       the primary (larger) text to display
     * @param additional the secondary (smaller) text to display below the main
     */
    public Message (String main, String additional) {
        this.main = main;
        this.additional = additional;
        this.type = 0;

        create();
    }

    /**
     * Creates a Message with the given texts and a type that controls the
     * background color.
     *
     * Types:
     * <ul>
     *   <li>0 — green (default)</li>
     *   <li>1 — yellow</li>
     *   <li>2 — red</li>
     * </ul>
     *
     * @param main       the primary (larger) text to display
     * @param additional the secondary (smaller) text to display below the main
     * @param type       the message type (0=green, 1=yellow, 2=red)
     */
    public Message (String main, String additional, int type) {
        this.main = main;
        this.additional = additional;
        this.type = type;

        create();
    }

    private void create() {
        messageW = width / 3;
        messageH = height / 5;

        double pad = 10;
        double bgX = (width - messageW) / 2 - pad;
        double bgY = (height - messageH) / 2 - pad;
        double bgW = messageW + pad * 2;
        double bgH = messageH + pad * 2;

        background = new GRect(bgX, bgY, bgW, bgH);
        background.setFilled(true);
        if (type == 0) background.setFillColor(Color.GREEN);
        else if (type == 1) background.setFillColor(Color.YELLOW);
        else if (type == 2) background.setFillColor(Color.RED);
        add(background);

        double rectX = (width - messageW) / 2;
        double rectY = (height - messageH) / 2;
        rect = new GRect(rectX, rectY, messageW, messageH);
        rect.setFilled(true);
        rect.setFillColor(Color.WHITE);
        add(rect);

        mainL = new GLabel(main);
        mainL.setFont("-30");
        double mainX = rectX + (messageW - mainL.getWidth()) / 2;
        double mainY = rectY + messageH / 2;
        add(mainL, mainX, mainY);

        additionalL = new GLabel(additional);
        additionalL.setFont("-20");
        double addX = rectX + (messageW - additionalL.getWidth()) / 2;
        double addY = mainY + mainL.getHeight() + 8;
        add(additionalL, addX, addY);
    }

    /**
     * Returns the message type (0=green, 1=yellow, 2=red).
     *
     * @return the message type
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the message type which affects the background color.
     * Valid values: 0 (green), 1 (yellow), 2 (red).
     *
     * Note: calling this after creation will change the stored type but will
     * not automatically update the background color visible on screen. To
     * reflect changes immediately you would need to modify this method to
     * update the {@link #background} fill color or recreate the component.
     *
     * @param type the message type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Sets the canvas width (in pixels) used to compute message sizing and
     * positioning. Must be called before creating Message instances.
     *
     * @param width canvas width in pixels
     */
    public static void setWidth(double width) {
        Message.width = width;
    }

    /**
     * Sets the canvas height (in pixels) used to compute message sizing and
     * positioning. Must be called before creating Message instances.
     *
     * @param height canvas height in pixels
     */
    public static void setHeight(double height) {
        Message.height = height;
    }
}
