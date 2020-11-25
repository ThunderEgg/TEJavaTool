package dev.thunderegg.colormaps;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SinglePointTest {

    @Test
    public void constructor() {
        SinglePoint point = new SinglePoint(0.2, 0.3, 0.4);
        assertThat(point.x, is(0.2));
        assertThat(point.lower, is(0.3));
        assertThat(point.upper, is(0.4));
    }

    @Test
    public void comapreXLessThan() {
        SinglePoint point1 = new SinglePoint(0.2, 0.3, 0.4);
        SinglePoint point2 = new SinglePoint(0.3, 0.3, 0.4);
        assertThat(SinglePoint.compareX(point1, point2), is(lessThan(0)));
    }

    @Test
    public void comapreXEquals() {
        SinglePoint point1 = new SinglePoint(0.2, 0.3, 0.4);
        SinglePoint point2 = new SinglePoint(0.2, 0.9, 0.7);
        assertThat(SinglePoint.compareX(point1, point2), is(equalTo(0)));
    }

    @Test
    public void comapreXGreaterThan() {
        SinglePoint point1 = new SinglePoint(0.3, 0.3, 0.4);
        SinglePoint point2 = new SinglePoint(0.2, 0.3, 0.4);
        assertThat(SinglePoint.compareX(point1, point2), is(greaterThan(0)));
    }

}
