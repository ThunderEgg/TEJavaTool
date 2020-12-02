package dev.thunderegg.colormaps;

import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

public class SeperateRGBListColorMapTest {

        @Test
        public void constructorInvalidRedList() {
                List<SinglePoint> red = Arrays.asList(new SinglePoint(0, 0.5, 0.5), new SinglePoint(0, 0.5, 0.5),
                                new SinglePoint(1, 0.5, 0.5));
                List<SinglePoint> green = Arrays.asList(new SinglePoint(0, 0.5, 0.5), new SinglePoint(0.5, 0.5, 0.5),
                                new SinglePoint(1, 0.5, 0.5));
                List<SinglePoint> blue = Arrays.asList(new SinglePoint(0, 0.5, 0.5), new SinglePoint(0.5, 0.5, 0.5),
                                new SinglePoint(1, 0.5, 0.5));

                assertThrows(IllegalArgumentException.class,
                                () -> new SeperateRGBListColorMap("name", red, green, blue));

        }

        @Test
        public void constructorInvalidGreenList() {
                List<SinglePoint> red = Arrays.asList(new SinglePoint(0, 0.5, 0.5), new SinglePoint(0.5, 0.5, 0.5),
                                new SinglePoint(1, 0.5, 0.5));
                List<SinglePoint> green = Arrays.asList(new SinglePoint(0, 0.5, 0.5), new SinglePoint(0.0, 0.5, 0.5),
                                new SinglePoint(1, 0.5, 0.5));
                List<SinglePoint> blue = Arrays.asList(new SinglePoint(0, 0.5, 0.5), new SinglePoint(0.5, 0.5, 0.5),
                                new SinglePoint(1, 0.5, 0.5));

                assertThrows(IllegalArgumentException.class,
                                () -> new SeperateRGBListColorMap("name", red, green, blue));

        }

        @Test
        public void constructorInvalidBlueList() {
                List<SinglePoint> red = Arrays.asList(new SinglePoint(0, 0.5, 0.5), new SinglePoint(0.5, 0.5, 0.5),
                                new SinglePoint(1, 0.5, 0.5));
                List<SinglePoint> green = Arrays.asList(new SinglePoint(0, 0.5, 0.5), new SinglePoint(0.5, 0.5, 0.5),
                                new SinglePoint(1, 0.5, 0.5));
                List<SinglePoint> blue = Arrays.asList(new SinglePoint(0, 0.5, 0.5), new SinglePoint(0.0, 0.5, 0.5),
                                new SinglePoint(1, 0.5, 0.5));

                assertThrows(IllegalArgumentException.class,
                                () -> new SeperateRGBListColorMap("name", red, green, blue));

        }

        @Test
        public void getColorNeg1() {
                List<SinglePoint> red = Arrays.asList(new SinglePoint(0, 1.0, 0.1), new SinglePoint(0.5, 0.2, 0.3),
                                new SinglePoint(1, 0.4, 0.5));
                List<SinglePoint> green = Arrays.asList(new SinglePoint(0, .9, 0.0), new SinglePoint(0.5, .1, 0.2),
                                new SinglePoint(1, 0.3, 0.4));
                List<SinglePoint> blue = Arrays.asList(new SinglePoint(0, 0.8, 0.7), new SinglePoint(0.5, 0.5, 0.1),
                                new SinglePoint(1, 0.2, 0.3));

                SeperateRGBListColorMap map = new SeperateRGBListColorMap("name", red, green, blue);

                Color color = map.getColor(-1);
                assertThat(color.getRed(), closeTo(1.0, 0.001));
                assertThat(color.getGreen(), closeTo(0.9, 0.001));
                assertThat(color.getBlue(), closeTo(0.8, 0.001));
        }

        @Test
        public void getName() {
                List<SinglePoint> red = Arrays.asList(new SinglePoint(0, 1.0, 0.1), new SinglePoint(0.5, 0.2, 0.3),
                                new SinglePoint(1, 0.4, 0.5));
                List<SinglePoint> green = Arrays.asList(new SinglePoint(0, .9, 0.0), new SinglePoint(0.5, .1, 0.2),
                                new SinglePoint(1, 0.3, 0.4));
                List<SinglePoint> blue = Arrays.asList(new SinglePoint(0, 0.8, 0.7), new SinglePoint(0.5, 0.5, 0.1),
                                new SinglePoint(1, 0.2, 0.3));

                SeperateRGBListColorMap map = new SeperateRGBListColorMap("blah", red, green, blue);

                assertThat(map.getName(), is("blah"));
        }

        @Test
        public void getColor0() {
                List<SinglePoint> red = Arrays.asList(new SinglePoint(0, 1.0, 0.1), new SinglePoint(0.5, 0.2, 0.3),
                                new SinglePoint(1, 0.4, 0.5));
                List<SinglePoint> green = Arrays.asList(new SinglePoint(0, .9, 0.0), new SinglePoint(0.5, .1, 0.2),
                                new SinglePoint(1, 0.3, 0.4));
                List<SinglePoint> blue = Arrays.asList(new SinglePoint(0, 0.8, 0.7), new SinglePoint(0.5, 0.5, 0.1),
                                new SinglePoint(1, 0.2, 0.3));

                SeperateRGBListColorMap map = new SeperateRGBListColorMap("name", red, green, blue);

                Color color = map.getColor(0);
                assertThat(color.getRed(), closeTo(0.1, 0.001));
                assertThat(color.getGreen(), closeTo(0.0, 0.001));
                assertThat(color.getBlue(), closeTo(0.7, 0.001));
        }

        @Test
        public void getColor0p25() {
                List<SinglePoint> red = Arrays.asList(new SinglePoint(0, 1.0, 0.1), new SinglePoint(0.5, 0.2, 0.3),
                                new SinglePoint(1, 0.4, 0.5));
                List<SinglePoint> green = Arrays.asList(new SinglePoint(0, .9, 0.0), new SinglePoint(0.5, .1, 0.2),
                                new SinglePoint(1, 0.3, 0.4));
                List<SinglePoint> blue = Arrays.asList(new SinglePoint(0, 0.8, 0.7), new SinglePoint(0.5, 0.5, 0.1),
                                new SinglePoint(1, 0.2, 0.3));

                SeperateRGBListColorMap map = new SeperateRGBListColorMap("name", red, green, blue);

                Color color = map.getColor(0.25);
                assertThat(color.getRed(), closeTo(0.15, 0.001));
                assertThat(color.getGreen(), closeTo(0.05, 0.001));
                assertThat(color.getBlue(), closeTo(0.6, 0.001));
        }

        @Test
        public void getColor0p5() {
                List<SinglePoint> red = Arrays.asList(new SinglePoint(0, 1.0, 0.1), new SinglePoint(0.5, 0.2, 0.3),
                                new SinglePoint(1, 0.4, 0.5));
                List<SinglePoint> green = Arrays.asList(new SinglePoint(0, .9, 0.0), new SinglePoint(0.5, .1, 0.2),
                                new SinglePoint(1, 0.3, 0.4));
                List<SinglePoint> blue = Arrays.asList(new SinglePoint(0, 0.8, 0.7), new SinglePoint(0.5, 0.5, 0.1),
                                new SinglePoint(1, 0.2, 0.3));

                SeperateRGBListColorMap map = new SeperateRGBListColorMap("name", red, green, blue);

                Color color = map.getColor(0.5);
                assertThat(color.getRed(), closeTo(0.3, 0.001));
                assertThat(color.getGreen(), closeTo(0.2, 0.001));
                assertThat(color.getBlue(), closeTo(0.1, 0.001));
        }

        @Test
        public void getColor0p75() {
                List<SinglePoint> red = Arrays.asList(new SinglePoint(0, 1.0, 0.1), new SinglePoint(0.5, 0.2, 0.3),
                                new SinglePoint(1, 0.4, 0.5));
                List<SinglePoint> green = Arrays.asList(new SinglePoint(0, .9, 0.0), new SinglePoint(0.5, .1, 0.2),
                                new SinglePoint(1, 0.3, 0.4));
                List<SinglePoint> blue = Arrays.asList(new SinglePoint(0, 0.8, 0.7), new SinglePoint(0.5, 0.5, 0.1),
                                new SinglePoint(1, 0.2, 0.3));

                SeperateRGBListColorMap map = new SeperateRGBListColorMap("name", red, green, blue);

                Color color = map.getColor(0.75);
                assertThat(color.getRed(), closeTo(0.35, 0.001));
                assertThat(color.getGreen(), closeTo(0.25, 0.001));
                assertThat(color.getBlue(), closeTo(0.15, 0.001));
        }

        @Test
        public void getColor1p0() {
                List<SinglePoint> red = Arrays.asList(new SinglePoint(0, 1.0, 0.1), new SinglePoint(0.5, 0.2, 0.3),
                                new SinglePoint(1, 0.4, 0.5));
                List<SinglePoint> green = Arrays.asList(new SinglePoint(0, .9, 0.0), new SinglePoint(0.5, .1, 0.2),
                                new SinglePoint(1, 0.3, 0.4));
                List<SinglePoint> blue = Arrays.asList(new SinglePoint(0, 0.8, 0.7), new SinglePoint(0.5, 0.5, 0.1),
                                new SinglePoint(1, 0.2, 0.3));

                SeperateRGBListColorMap map = new SeperateRGBListColorMap("name", red, green, blue);

                Color color = map.getColor(1.0);
                assertThat(color.getRed(), closeTo(0.5, 0.001));
                assertThat(color.getGreen(), closeTo(0.4, 0.001));
                assertThat(color.getBlue(), closeTo(0.3, 0.001));
        }

        @Test
        public void getColor2p0() {
                List<SinglePoint> red = Arrays.asList(new SinglePoint(0, 1.0, 0.1), new SinglePoint(0.5, 0.2, 0.3),
                                new SinglePoint(1, 0.4, 0.5));
                List<SinglePoint> green = Arrays.asList(new SinglePoint(0, .9, 0.0), new SinglePoint(0.5, .1, 0.2),
                                new SinglePoint(1, 0.3, 0.4));
                List<SinglePoint> blue = Arrays.asList(new SinglePoint(0, 0.8, 0.7), new SinglePoint(0.5, 0.5, 0.1),
                                new SinglePoint(1, 0.2, 0.3));

                SeperateRGBListColorMap map = new SeperateRGBListColorMap("name", red, green, blue);

                Color color = map.getColor(2.0);
                assertThat(color.getRed(), closeTo(0.5, 0.001));
                assertThat(color.getGreen(), closeTo(0.4, 0.001));
                assertThat(color.getBlue(), closeTo(0.3, 0.001));
        }

}
