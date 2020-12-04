package dev.thunderegg.meshcreator.twodimensiontimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;

import dev.thunderegg.Patch;
import javafx.scene.paint.Color;

public class SquareWithText {
    public final double x;
    public final double y;
    public final double x_length;
    public final double y_length;
    public String text = "";
    public Color color = Color.WHITE;

    public SquareWithText(double x, double y, double x_length, double y_length) {
        this.x = x;
        this.y = y;
        this.x_length = x_length;
        this.y_length = y_length;
    }

    public static Collection<SquareWithText> getRectanglesForDomain(Collection<Patch> domain) {
        return getRectanglesForDomain(domain, (Patch p) -> "", (Patch p) -> Color.WHITE);
    }

    public static Collection<SquareWithText> getRectanglesForDomain(Collection<Patch> domain,
            Function<Patch, String> textCallback, Function<Patch, Color> colorCallback) {
        LinkedList<SquareWithText> ret = new LinkedList<>();
        for (Patch patch : domain) {
            SquareWithText rectangle = new SquareWithText(patch.starts[0], patch.starts[1], patch.lengths[0],
                    patch.lengths[1]);
            rectangle.text = textCallback.apply(patch);
            rectangle.color = colorCallback.apply(patch);
            ret.add(rectangle);
        }
        return ret;
    }

}
