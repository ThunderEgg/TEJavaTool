package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.thunderegg.colormaps.ColorMaps;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ColorMapImageGeneratorTest {

    public ColorMapImageGenerator cmapImageGenerator;

    @BeforeEach
    public void setup() {
        cmapImageGenerator = new ColorMapImageGenerator(ColorMaps.getColorMaps().get(0));
    }

    @Test
    public void getColorMap() {
        assertThat(cmapImageGenerator.getColorMap(), is(ColorMaps.getColorMaps().get(0)));
    }

    @Test
    public void toStringTest() {
        assertThat(cmapImageGenerator.toString(), is(cmapImageGenerator.getColorMap().getName()));
    }

    @Test
    public void getImage() {
        Image image = cmapImageGenerator.getImage(300);
        assertThat(image.getWidth(), is(300.0));
        assertThat(image.getHeight(), is(2.0));
        PixelReader reader = image.getPixelReader();
        for (int i = 0; i < 300; i++) {
            double x = (i + 0.5) / 300;
            Color expectedColor = cmapImageGenerator.getColorMap().getColor(x);
            for (int j = 0; j < 2; j++) {
                Color color = reader.getColor(i, j);
                assertThat(color.getRed(), closeTo(expectedColor.getRed(), 0.002));
                assertThat(color.getGreen(), closeTo(expectedColor.getGreen(), 0.002));
                assertThat(color.getBlue(), closeTo(expectedColor.getBlue(), 0.002));
                assertThat(color.getOpacity(), closeTo(expectedColor.getOpacity(), 0.002));
            }
        }
    }

    @Test
    public void getVerticalImage() {
        Image image = cmapImageGenerator.getVerticalImage(300);
        assertThat(image.getWidth(), is(2.0));
        assertThat(image.getHeight(), is(300.0));
        PixelReader reader = image.getPixelReader();
        for (int j = 0; j < 300; j++) {
            double x = 1.0 - (j + 0.5) / 300;
            Color expectedColor = cmapImageGenerator.getColorMap().getColor(x);
            for (int i = 0; i < 2; i++) {
                Color color = reader.getColor(i, j);
                assertThat(color.getRed(), closeTo(expectedColor.getRed(), 0.002));
                assertThat(color.getGreen(), closeTo(expectedColor.getGreen(), 0.002));
                assertThat(color.getBlue(), closeTo(expectedColor.getBlue(), 0.002));
                assertThat(color.getOpacity(), closeTo(expectedColor.getOpacity(), 0.002));
            }
        }
    }

}
