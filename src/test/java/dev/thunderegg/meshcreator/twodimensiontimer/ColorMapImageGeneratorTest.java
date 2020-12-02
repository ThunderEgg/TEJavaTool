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
        assertThat(image.getHeight(), is(1.0));
        PixelReader reader = image.getPixelReader();
        for (int i = 0; i < 300; i++) {
            double x = (i + 0.5) / 300;
            Color expectedColor = cmapImageGenerator.getColorMap().getColor(x);
            Color color = reader.getColor(i, 0);
            assertThat(color.getRed(), closeTo(expectedColor.getRed(), 0.002));
            assertThat(color.getGreen(), closeTo(expectedColor.getGreen(), 0.002));
            assertThat(color.getBlue(), closeTo(expectedColor.getBlue(), 0.002));
            assertThat(color.getOpacity(), closeTo(expectedColor.getOpacity(), 0.002));
        }
    }

}
