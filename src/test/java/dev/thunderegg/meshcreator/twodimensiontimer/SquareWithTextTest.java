package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.Test;

import dev.thunderegg.Patch;
import javafx.scene.paint.Color;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Collection;

public class SquareWithTextTest {

    @Test
    public void constructor() {
        SquareWithText rectangle = new SquareWithText(1, 2, 3, 4);
        assertThat(rectangle.x, is(1.0));
        assertThat(rectangle.y, is(2.0));
        assertThat(rectangle.x_length, is(3.0));
        assertThat(rectangle.y_length, is(4.0));
    }

    @Test
    public void getRectanglesForDomain() {
        ArrayList<Patch> domain = new ArrayList<>();
        Patch patch1 = new Patch(2);
        patch1.lengths = new double[] { 1, 2 };
        patch1.starts = new double[] { 3, 4 };
        Patch patch2 = new Patch(2);
        patch2.lengths = new double[] { 5, 6 };
        patch2.starts = new double[] { 7, 8 };
        domain.add(patch1);
        domain.add(patch2);

        Collection<SquareWithText> rectangles = SquareWithText.getRectanglesForDomain(domain);
        SquareWithText rectangle1 = null;
        SquareWithText rectangle2 = null;
        for (SquareWithText rectangle : rectangles) {
            if (rectangle.x == 3.0) {
                rectangle1 = rectangle;
            }
            if (rectangle.x == 7.0) {
                rectangle2 = rectangle;
            }
        }
        assertThat(rectangle1.x, is(patch1.starts[0]));
        assertThat(rectangle1.y, is(patch1.starts[1]));
        assertThat(rectangle1.x_length, is(patch1.lengths[0]));
        assertThat(rectangle1.y_length, is(patch1.lengths[1]));
        assertThat(rectangle2.x, is(patch2.starts[0]));
        assertThat(rectangle2.y, is(patch2.starts[1]));
        assertThat(rectangle2.x_length, is(patch2.lengths[0]));
        assertThat(rectangle2.y_length, is(patch2.lengths[1]));
    }

    @Test
    public void getRectanglesForDomainWithText() {
        ArrayList<Patch> domain = new ArrayList<>();
        Patch patch1 = new Patch(2);
        patch1.lengths = new double[] { 1, 2 };
        patch1.starts = new double[] { 3, 4 };
        Patch patch2 = new Patch(2);
        patch2.lengths = new double[] { 5, 6 };
        patch2.starts = new double[] { 7, 8 };
        domain.add(patch1);
        domain.add(patch2);

        Collection<SquareWithText> rectangles = SquareWithText.getRectanglesForDomain(domain,
                (Patch p) -> Double.toString(p.starts[0]), (Patch p) -> new Color(p.starts[0] / 8, 1.0, 1.0, 1.0));
        SquareWithText rectangle1 = null;
        SquareWithText rectangle2 = null;
        for (SquareWithText rectangle : rectangles) {
            if (rectangle.x == 3.0) {
                rectangle1 = rectangle;
            }
            if (rectangle.x == 7.0) {
                rectangle2 = rectangle;
            }
        }
        assertThat(rectangle1.x, is(patch1.starts[0]));
        assertThat(rectangle1.y, is(patch1.starts[1]));
        assertThat(rectangle1.x_length, is(patch1.lengths[0]));
        assertThat(rectangle1.y_length, is(patch1.lengths[1]));
        assertThat(rectangle1.text, is(Double.toString(patch1.starts[0])));
        assertThat(rectangle1.color, is(new Color(patch1.starts[0] / 8, 1.0, 1.0, 1.0)));
        assertThat(rectangle2.x, is(patch2.starts[0]));
        assertThat(rectangle2.y, is(patch2.starts[1]));
        assertThat(rectangle2.x_length, is(patch2.lengths[0]));
        assertThat(rectangle2.y_length, is(patch2.lengths[1]));
        assertThat(rectangle2.text, is(Double.toString(patch2.starts[0])));
        assertThat(rectangle2.color, is(new Color(patch2.starts[0] / 8, 1.0, 1.0, 1.0)));
    }

}
