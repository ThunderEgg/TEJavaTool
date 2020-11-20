package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.Test;

import javafx.scene.canvas.Canvas;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PatchPainterTest {

    @Test
    public void defaultXTranslateValue() {
        PatchPainter painter = new PatchPainter(new Canvas());
        assertThat(painter.getXTranslate(), is(0.0));
    }

    @Test
    public void defaultYTranslateValue() {
        PatchPainter painter = new PatchPainter(new Canvas());
        assertThat(painter.getYTranslate(), is(0.0));
    }

    @Test
    public void defaultSizeValue() {
        PatchPainter painter = new PatchPainter(new Canvas());
        assertThat(painter.getScale(), is(300.0));
    }

    @Test
    public void setSize() {
        PatchPainter painter = new PatchPainter(new Canvas());
        painter.setScale(90.0);
        assertThat(painter.getScale(), is(90.0));
    }

    @Test
    public void translate() {
        PatchPainter painter = new PatchPainter(new Canvas());
        painter.translate(1, 2);
        assertThat(painter.getXTranslate(), is(1.0));
        assertThat(painter.getYTranslate(), is(2.0));
        painter.translate(2, 3);
        assertThat(painter.getXTranslate(), is(3.0));
        assertThat(painter.getYTranslate(), is(5.0));
    }
}
