package dev.thunderegg.colormaps;

import javafx.scene.paint.Color;

public interface ColorMap {

    /**
     * Get the color for an x value between 0 and 1
     * 
     * @param x a value between 0 and 1
     * @return the color
     */
    public Color getColor(double x);
}
