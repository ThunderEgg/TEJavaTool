package dev.thunderegg.meshcreator.twodimensiontimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;

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
        return getRectanglesForDomain(domain, (Patch p) -> "");
    }

    public static Collection<SquareWithText> getRectanglesForDomain(Collection<Patch> domain,
            Function<Patch, String> textCallback) {
        LinkedList<SquareWithText> ret = new LinkedList<>();
        for (Patch patch : domain) {
            SquareWithText rectangle = new SquareWithText(patch.starts[0], patch.starts[1], patch.lengths[0],
                    patch.lengths[1]);
            rectangle.text = textCallback.apply(patch);
            ret.add(rectangle);
        }
        return ret;
    }

}
