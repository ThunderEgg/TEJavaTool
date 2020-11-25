package dev.thunderegg.colormaps;

import java.util.Collection;
import java.util.TreeSet;

import com.google.common.collect.Comparators;

import javafx.scene.paint.Color;

/**
 * A ColorMap implemented using seperate lists for red, green, and blue values.
 */
public class SeperateRGBListColorMap implements ColorMap {

    /**
     * The red values
     */
    private TreeSet<SinglePoint> red = new TreeSet<>(SinglePoint::compareX);
    /**
     * The green values
     */
    private TreeSet<SinglePoint> green = new TreeSet<>(SinglePoint::compareX);
    /**
     * The blue values
     */
    private TreeSet<SinglePoint> blue = new TreeSet<>(SinglePoint::compareX);

    /**
     * Construct a new SeperateRGBListColorMap
     * 
     * Will throw IllegalArgumentException if any of the sets are not in strictly
     * ascending order
     * 
     * @param name  the name of the colormap
     * @param red   the set of red values
     * @param green the set of green values
     * @param blue  the set of blue values
     */
    public SeperateRGBListColorMap(String name, Collection<SinglePoint> red, Collection<SinglePoint> green,
            Collection<SinglePoint> blue) {
        if (!Comparators.isInStrictOrder(red, SinglePoint::compareX)) {
            throw new IllegalArgumentException("red list is not in strictly ascending order");
        }
        if (!Comparators.isInStrictOrder(blue, SinglePoint::compareX)) {
            throw new IllegalArgumentException("blue list is not in strictly ascending order");
        }
        if (!Comparators.isInStrictOrder(green, SinglePoint::compareX)) {
            throw new IllegalArgumentException("green list is not in strictly ascending order");
        }
        this.red.addAll(red);
        this.green.addAll(green);
        this.blue.addAll(blue);
    }

    @Override
    public Color getColor(double x) {
        double r = getValue(red, x);
        double g = getValue(green, x);
        double b = getValue(blue, x);
        return new Color(r, g, b, 1.0);
    }

    /**
     * 
     * Get the value from a given set of SinglePoints
     * 
     * @param values the values
     * @param x      the x value on the color axis
     * @return the value
     */
    private double getValue(TreeSet<SinglePoint> values, double x) {
        SinglePoint lower = values.floor(new SinglePoint(x, 0, 0));
        SinglePoint upper = values.higher(new SinglePoint(x, 0, 0));
        return getValue(lower, upper, x);
    }

    /**
     * Interpolate the value between the points
     * 
     * @param lower the lower point
     * @param upper the higher point
     * @param x     the x value on the axis
     * @return the interpolated value. If lower is null it returns upper.lower. If
     *         higher is null it returns lower.upper.
     */
    private double getValue(SinglePoint lower, SinglePoint upper, double x) {
        if (lower == null) {
            return upper.lower;
        }
        if (upper == null) {
            return lower.upper;
        }
        return (lower.upper * (upper.x - x) + upper.lower * (x - lower.x)) / (upper.x - lower.x);
    }

}
