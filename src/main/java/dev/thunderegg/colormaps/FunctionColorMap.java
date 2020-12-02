package dev.thunderegg.colormaps;

import java.util.function.DoubleUnaryOperator;

import javafx.scene.paint.Color;

/**
 * A colormap that uses functions to determine red, green, and blue values
 */
public class FunctionColorMap implements ColorMap {

    /**
     * The name of the colormap
     */
    private String name;
    /**
     * The function for red values
     */
    private DoubleUnaryOperator red;
    /**
     * The function for green values
     */
    private DoubleUnaryOperator green;
    /**
     * The function for blue values
     */
    private DoubleUnaryOperator blue;

    /**
     * Construct a new FunctionColorMap
     * 
     * @param name  the name of the colormap
     * @param red   the function for red values
     * @param green the funciton for green values
     * @param blue  the function for blue values
     */
    public FunctionColorMap(String name, DoubleUnaryOperator red, DoubleUnaryOperator green, DoubleUnaryOperator blue) {
        this.name = name;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public Color getColor(double x) {
        return new Color(clamp(red.applyAsDouble(x)), clamp(green.applyAsDouble(x)), clamp(blue.applyAsDouble(x)), 1.0);
    }

    /**
     * Clamp the value between 0 and 1
     * 
     * @param x the value
     * @return the clamped value
     */
    private double clamp(double x) {
        if (x <= 0) {
            return 0;
        } else if (x >= 1) {
            return 1;
        }
        return x;
    }

    @Override
    public String getName() {
        return name;
    }

}
