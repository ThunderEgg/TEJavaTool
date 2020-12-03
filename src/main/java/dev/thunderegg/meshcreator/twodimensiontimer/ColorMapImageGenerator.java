package dev.thunderegg.meshcreator.twodimensiontimer;

import dev.thunderegg.colormaps.ColorMap;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ColorMapImageGenerator {

    private ColorMap cmap;

    public ColorMapImageGenerator(ColorMap cmap) {
        this.cmap = cmap;
    }

    public ColorMap getColorMap() {
        return cmap;
    }

    public Image getImage(int width) {
        WritableImage image = new WritableImage(width, 2);
        PixelWriter writer = image.getPixelWriter();
        for (int i = 0; i < width; i++) {
            Color color = cmap.getColor((i + 0.5) / width);
            writer.setColor(i, 0, color);
            writer.setColor(i, 1, color);
        }
        return image;
    }

    @Override
    public String toString() {
        return cmap.getName();
    }

}
