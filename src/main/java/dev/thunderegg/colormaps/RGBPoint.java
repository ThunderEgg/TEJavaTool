package dev.thunderegg.colormaps;

import javafx.scene.paint.Color;

/**
 * Represents a point in a colormap with an associated color
 */
public class RGBPoint {
    /**
     * A value in [0,1] on the color axis
     */
    public final double x;
    /**
     * The color associated with this point
     */
    public final Color color;

    /**
     * Construct a new RGBPoint
     * 
     * @param x     the x value on the axis
     * @param color the color associated with the point
     */
    public RGBPoint(double x, Color color) {
        this.x = x;
        this.color = color;
    }

    /**
     * Compare the x values of two RGBPoints
     * 
     * @param a the first value
     * @param b the second values
     * @return negative, zero, or positive is less than, equal to, or greater than
     */
    public static int compareX(RGBPoint a, RGBPoint b) {
        return Double.compare(a.x, b.x);
    }

}
