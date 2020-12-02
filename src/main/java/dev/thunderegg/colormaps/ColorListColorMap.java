package dev.thunderegg.colormaps;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class ColorListColorMap implements ColorMap {

    private String name;
    private ArrayList<Color> colors;

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

    private Color mix(Color lower, Color upper, double x) {
        double r = lower.getRed() * (1.0 - x) + upper.getRed() * x;
        double g = lower.getGreen() * (1.0 - x) + upper.getGreen() * x;
        double b = lower.getBlue() * (1.0 - x) + upper.getBlue() * x;
        double a = lower.getOpacity() * (1.0 - x) + upper.getOpacity() * x;
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
