package dev.thunderegg.meshcreator.twodimensiontimer;

import java.util.Collection;
import java.util.LinkedList;

import dev.thunderegg.Patch;

public class SquareWithText {
    public final double x;
    public final double y;
    public final double x_length;
    public final double y_length;
    public String text = "";

    public SquareWithText(double x, double y, double x_length, double y_length) {
        this.x = x;
        this.y = y;
        this.x_length = x_length;
        this.y_length = y_length;
    }

    public static Collection<SquareWithText> getRectanglesForDomain(Collection<Patch> domain) {
        LinkedList<SquareWithText> ret = new LinkedList<>();
        for (Patch patch : domain) {
            ret.add(new SquareWithText(patch.starts[0], patch.starts[1], patch.lengths[0], patch.lengths[1]));
        }
        return ret;
    }

}