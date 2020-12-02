package dev.thunderegg.colormaps;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

/**
 * Colormap that interpolates from an equally space list of colors
 */
public class ColorListColorMap implements ColorMap {

    /**
     * The name of the colormap
     */
    private String name;
    /**
     * The list of colors
     */
    private ArrayList<Color> colors;

    /**
     * Construct a new ColorListColorMap
     * 
     * @param name   the name of the map
     * @param colors the list of colors
     */
    public ColorListColorMap(String name, List<Color> colors) {
        if (colors.isEmpty()) {
            throw new IllegalArgumentException("Empty list passed to colormap");
        }
        this.name = name;
        this.colors = new ArrayList<>(colors);
    }

    @Override
    public Color getColor(double x) {
        if (x <= 0) {
            return colors.get(0);
        } else if (x >= 1) {
            return colors.get(colors.size() - 1);
        }
        int lowerIndex = (int) Math.floor(x * (colors.size() - 1));
        int upperIndex = (int) Math.ceil(x * (colors.size() - 1));
        double r = x * (colors.size() - 1) - lowerIndex;
        return mix(colors.get(lowerIndex), colors.get(upperIndex), r);
    }

    /**
     * Mix the colors
     * 
     * @param lower the lower color
     * @param upper the upper color
     * @param x     the ratio of upper color to lower color
     * @return the color
     */
    private Color mix(Color lower, Color upper, double x) {
        double r = lower.getRed() * (1.0 - x) + upper.getRed() * x;
        double g = lower.getGreen() * (1.0 - x) + upper.getGreen() * x;
        double b = lower.getBlue() * (1.0 - x) + upper.getBlue() * x;
        double a = lower.getOpacity() * (1.0 - x) + upper.getOpacity() * x;
        return new Color(ColorMath.clamp(r), ColorMath.clamp(g), ColorMath.clamp(b), ColorMath.clamp(a));
    }

    @Override
    public String getName() {
        return name;
    }

}
