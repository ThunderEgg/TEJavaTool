package dev.thunderegg.colormaps;

/**
 * Functions related to color math
 */
public class ColorMath {

    /**
     * Clamp the value between 0 and 1
     * 
     * @param x the value to clamp
     * @return the clamped value
     */
    public static double clamp(double x) {
        if (x <= 0) {
            return 0;
        } else if (x >= 1) {
            return 1;
        }
        return x;
    }

}
