package dev.thunderegg.colormaps;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

/**
 * Qualitative colormap given a list of colors. The colormap is divided into
 * equally space blocks of colors from the given list.
 */
public class QualitativeColorListColorMap extends ColorMap {

    /**
     * The list of colors
     */
    private ArrayList<Color> colors;

    /**
     * Construct a QualitativeColorListColorMap
     * 
     * @param name   the name of the colormap
     * @param colors the list of colors
     */
    public QualitativeColorListColorMap(String name, List<Color> colors) {
        super(name);
        if (colors.isEmpty()) {
            throw new IllegalArgumentException("Empty list passed to colormap");
        }
        this.colors = new ArrayList<>(colors);
    }

    @Override
    public Color getColor(double x) {
        if (x <= 0) {
            return colors.get(0);
        } else if (x >= 1) {
            return colors.get(colors.size() - 1);
        }
        int index = (int) Math.round(x * colors.size() - 0.5);
        return colors.get(index);
    }

}
