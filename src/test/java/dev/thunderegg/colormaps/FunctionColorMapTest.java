package dev.thunderegg.colormaps;

import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FunctionColorMapTest {

        @Test
        public void getName() {
                FunctionColorMap map = new FunctionColorMap("blah", null, null, null);

                assertThat(map.getName(), is("blah"));
        }

        @Test
        public void getColor() {
                FunctionColorMap map = new FunctionColorMap("name", (double x) -> x * x, (double x) -> -2 + 4 * x,
                                (double x) -> Math.sin(x));

                for (double x = -0.5; x < 1.5; x += 0.125) {
                        Color color = map.getColor(x);
                        assertThat(color.getRed(), closeTo(clamp(x * x), 0.001));
                        assertThat(color.getGreen(), closeTo(clamp(-2 + 4 * x), 0.001));
                        assertThat(color.getBlue(), closeTo(clamp(Math.sin(x)), 0.001));
                }
        }

        private double clamp(double x) {
                if (x <= 0) {
                        return 0;
                } else if (x >= 1) {
                        return 1;
                }
                return x;
        }

}
