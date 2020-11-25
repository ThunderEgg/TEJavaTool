package dev.thunderegg.colormaps;

import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RGBPointTest {

    @Test
    public void constructor() {
        RGBPoint point = new RGBPoint(0.2, Color.GREEN);
        assertThat(point.x, is(0.2));
        assertThat(point.color, is(Color.GREEN));
    }

    @Test
    public void comapreXLessThan() {
        RGBPoint point1 = new RGBPoint(0.2, Color.GREEN);
        RGBPoint point2 = new RGBPoint(0.3, Color.GREEN);
        assertThat(RGBPoint.compareX(point1, point2), is(lessThan(0)));
    }

    @Test
    public void comapreXEquals() {
        RGBPoint point1 = new RGBPoint(0.2, Color.GREEN);
        RGBPoint point2 = new RGBPoint(0.2, Color.BLUE);
        assertThat(RGBPoint.compareX(point1, point2), is(equalTo(0)));
    }

    @Test
    public void comapreXGreaterThan() {
        RGBPoint point1 = new RGBPoint(0.3, Color.GREEN);
        RGBPoint point2 = new RGBPoint(0.2, Color.GREEN);
        assertThat(RGBPoint.compareX(point1, point2), is(greaterThan(0)));
    }

}
