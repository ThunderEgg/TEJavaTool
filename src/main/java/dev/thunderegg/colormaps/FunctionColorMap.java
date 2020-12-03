package dev.thunderegg.colormaps;

import java.util.function.DoubleUnaryOperator;

import javafx.scene.paint.Color;

/**
 * A colormap that uses functions to determine red, green, and blue values
 */
public class FunctionColorMap extends ColorMap {

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
        super(name);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public Color getColor(double x) {
        return new Color(ColorMath.clamp(red.applyAsDouble(x)), ColorMath.clamp(green.applyAsDouble(x)),
                ColorMath.clamp(blue.applyAsDouble(x)), 1.0);
    }
}
