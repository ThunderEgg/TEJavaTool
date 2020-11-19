package dev.thunderegg.meshcreator.twodimensiontimer;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public interface PatchCanvasPainter {

    public void handleMouseDragged(MouseEvent me);

    public void handleScroll(ScrollEvent se);

}
