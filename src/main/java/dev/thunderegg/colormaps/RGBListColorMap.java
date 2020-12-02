package dev.thunderegg.colormaps;

import java.util.Collection;
import java.util.TreeSet;

import com.google.common.collect.Comparators;

import javafx.scene.paint.Color;

public class RGBListColorMap implements ColorMap {

    /**
     * The name of this colormap
     */
    private String name;
    /**
     * The color points in this map
     */
    private TreeSet<RGBPoint> points = new TreeSet<>(RGBPoint::compareX);

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

    private double clamp(double r) {
        if (r < 0) {
            return 0;
        } else if (r > 1) {
            return 1;
        } else {
            return r;
        }
    }

    @Override
    public String getName() {
        return name;
    }

}
