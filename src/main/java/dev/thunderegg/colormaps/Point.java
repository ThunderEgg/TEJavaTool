package dev.thunderegg.colormaps;

/**
 * Represents a point in a colormap
 */
public class Point {

    /**
     * A value in [0,1] on the color axis
     */
    public final double x;
    /**
     * The color value for values <x
     */
    public final double lower;
    /**
     * The color value for values >=x
     */
    public final double upper;

    public Point(double x, double lower, double upper) {
        this.x = x;
        this.lower = lower;
        this.upper = upper;
    }

}
