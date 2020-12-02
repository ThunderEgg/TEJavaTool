package dev.thunderegg.colormaps;

import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

public class ColorListColorMapTest {

        @Test
        public void emptyListThrows() {
                List<Color> list = Arrays.asList();

                assertThrows(IllegalArgumentException.class, () -> new ColorListColorMap("name", list));
        }

        @Test
        public void getColorNeg1() {
                List<Color> list = Arrays.asList(new Color(0.1, 0.2, 0.9, 1.0), new Color(0.3, 0.4, 0.5, 1.0),
                                new Color(0.4, 0.2, 0.3, 1.0));

                ColorListColorMap map = new ColorListColorMap("name", list);

                Color color = map.getColor(-1);
                assertThat(color.getRed(), closeTo(0.1, 0.001));
                assertThat(color.getGreen(), closeTo(0.2, 0.001));
                assertThat(color.getBlue(), closeTo(0.9, 0.001));
        }

        @Test
        public void getName() {
                List<Color> list = Arrays.asList(new Color(0.1, 0.2, 0.9, 1.0), new Color(0.3, 0.4, 0.5, 1.0),
                                new Color(0.4, 0.2, 0.3, 1.0));

                ColorListColorMap map = new ColorListColorMap("blah", list);

                assertThat(map.getName(), is("blah"));
        }

        @Test
        public void getColor0() {
                List<Color> list = Arrays.asList(new Color(0.1, 0.2, 0.9, 1.0), new Color(0.3, 0.4, 0.5, 1.0),
                                new Color(0.4, 0.2, 0.3, 1.0));

                ColorListColorMap map = new ColorListColorMap("blah", list);

                Color color = map.getColor(0);
                assertThat(color.getRed(), closeTo(0.1, 0.001));
                assertThat(color.getGreen(), closeTo(0.2, 0.001));
                assertThat(color.getBlue(), closeTo(0.9, 0.001));
        }

        @Test
        public void getColor0p25() {
                List<Color> list = Arrays.asList(new Color(0.1, 0.2, 0.9, 1.0), new Color(0.3, 0.4, 0.5, 1.0),
                                new Color(0.4, 0.2, 0.3, 1.0));

                ColorListColorMap map = new ColorListColorMap("blah", list);

                Color color = map.getColor(0.25);
                assertThat(color.getRed(), closeTo(0.2, 0.001));
                assertThat(color.getGreen(), closeTo(0.3, 0.001));
                assertThat(color.getBlue(), closeTo(0.7, 0.001));
        }

        @Test
        public void getColor0p5() {
                List<Color> list = Arrays.asList(new Color(0.1, 0.2, 0.9, 1.0), new Color(0.3, 0.4, 0.5, 1.0),
                                new Color(0.4, 0.2, 0.3, 1.0));

                ColorListColorMap map = new ColorListColorMap("blah", list);

                Color color = map.getColor(0.5);
                assertThat(color.getRed(), closeTo(0.3, 0.001));
                assertThat(color.getGreen(), closeTo(0.4, 0.001));
                assertThat(color.getBlue(), closeTo(0.5, 0.001));
        }

        @Test
        public void getColor0p75() {
                List<Color> list = Arrays.asList(new Color(0.1, 0.2, 0.9, 1.0), new Color(0.3, 0.4, 0.5, 1.0),
                                new Color(0.4, 0.2, 0.3, 1.0));

                ColorListColorMap map = new ColorListColorMap("blah", list);

                Color color = map.getColor(0.75);
                assertThat(color.getRed(), closeTo(0.35, 0.001));
                assertThat(color.getGreen(), closeTo(0.3, 0.001));
                assertThat(color.getBlue(), closeTo(0.4, 0.001));
        }

        @Test
        public void getColor1p0() {
                List<Color> list = Arrays.asList(new Color(0.1, 0.2, 0.9, 1.0), new Color(0.3, 0.4, 0.5, 1.0),
                                new Color(0.4, 0.2, 0.3, 1.0));

                ColorListColorMap map = new ColorListColorMap("blah", list);

                Color color = map.getColor(1.0);
                assertThat(color.getRed(), closeTo(0.4, 0.001));
                assertThat(color.getGreen(), closeTo(0.2, 0.001));
                assertThat(color.getBlue(), closeTo(0.3, 0.001));
        }

        @Test
        public void getColor2p0() {
                List<Color> list = Arrays.asList(new Color(0.1, 0.2, 0.9, 1.0), new Color(0.3, 0.4, 0.5, 1.0),
                                new Color(0.4, 0.2, 0.3, 1.0));

                ColorListColorMap map = new ColorListColorMap("blah", list);

                Color color = map.getColor(2.0);
                assertThat(color.getRed(), closeTo(0.4, 0.001));
                assertThat(color.getGreen(), closeTo(0.2, 0.001));
                assertThat(color.getBlue(), closeTo(0.3, 0.001));
        }

}
