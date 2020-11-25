package dev.thunderegg.colormaps;

/**
 * Represents a point in a colormap
 */
public class SinglePoint {

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

    /**
     * Construct a new SinglePoint
     * 
     * @param x     the x value on the color axis
     * @param lower the color value for value <x
     * @param upper the color values for values >=x
     */
    public SinglePoint(double x, double lower, double upper) {
        this.x = x;
        this.lower = lower;
        this.upper = upper;
    }

    /**
     * Compare the x values of two SinglePoints
     * 
     * @param a the first value
     * @param b the second values
     * @return negative, zero, or positive is less than, equal to, or greater than
     */
    public static int compareX(SinglePoint a, SinglePoint b) {
        return Double.compare(a.x, b.x);
    }

}
