package dev.thunderegg.colormaps;

import javafx.scene.paint.Color;

/**
 * Abstract class for colormaps
 */
public abstract class ColorMap {

    /**
     * The name of the colormap
     */
    private String name;

    /**
     * Construct a new colormap
     * 
     * @param name the name
     */
    public ColorMap(String name) {
        this.name = name;
    }

    /**
     * Get the color for an x value between 0 and 1
     * 
     * @param x a value between 0 and 1
     * @return the color
     */
    public abstract Color getColor(double x);

    /**
     * Get the name of the colormap
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
