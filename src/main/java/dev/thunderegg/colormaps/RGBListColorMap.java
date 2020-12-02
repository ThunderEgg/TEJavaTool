package dev.thunderegg.colormaps;

import java.util.Collection;
import java.util.TreeSet;

import com.google.common.collect.Comparators;

import javafx.scene.paint.Color;

/**
 * A ColorMap implemented using seperate lists RGBPoint along the along the
 * color axis
 */
public class RGBListColorMap implements ColorMap {

    /**
     * The name of this colormap
     */
    private String name;
    /**
     * The color points in this map
     */
    private TreeSet<RGBPoint> points = new TreeSet<>(RGBPoint::compareX);

    /**
     * Construct a new RGBListColorMap
     * 
     * Will throw IllegalArgumentException if any of the set is not in strictly
     * ascending order
     * 
     * @param name   the name of the colormap
     * @param points the set of values
     */

    public RGBListColorMap(String name, Collection<RGBPoint> points) {
        if (!Comparators.isInStrictOrder(points, RGBPoint::compareX)) {
            throw new IllegalArgumentException("point list is not in strictly ascending order");
        }
        this.name = name;
        this.points.addAll(points);
    }

    @Override
    public Color getColor(double x) {
        RGBPoint lower = points.floor(new RGBPoint(x, Color.GREEN));
        RGBPoint upper = points.higher(new RGBPoint(x, Color.GREEN));
        return getValue(lower, upper, x);
    }

    /**
     * Get an interpolated color value
     * 
     * @param lower the value lower on the color axis
     * @param upper the value higher on the oclor axis
     * @param x     the point int the axis to interpolate at
     * @return the interpolated color
     */
    private Color getValue(RGBPoint lower, RGBPoint upper, double x) {
        if (lower == null) {
            return upper.color;
        }
        if (upper == null) {
            return lower.color;
        }
        double r = (lower.color.getRed() * (upper.x - x) + upper.color.getRed() * (x - lower.x)) / (upper.x - lower.x);
        double g = (lower.color.getGreen() * (upper.x - x) + upper.color.getGreen() * (x - lower.x))
                / (upper.x - lower.x);
        double b = (lower.color.getBlue() * (upper.x - x) + upper.color.getBlue() * (x - lower.x))
                / (upper.x - lower.x);
        double a = (lower.color.getOpacity() * (upper.x - x) + upper.color.getOpacity() * (x - lower.x))
                / (upper.x - lower.x);
        return new Color(clamp(r), clamp(g), clamp(b), clamp(a));
    }

    /**
     * Clamp the value between 0 and 1
     * 
     * @param r the value to clamp
     * @return the clamped value
     */
    private double clamp(double d) {
        if (d < 0) {
            return 0;
        } else if (d > 1) {
            return 1;
        } else {
            return d;
        }
    }

    @Override
    public String getName() {
        return name;
    }

}
