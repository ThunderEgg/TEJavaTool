package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class TimerPaneTest {

    private TimerPane pane;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        pane = new TimerPane();
        stage.setScene(new Scene(pane));
        stage.show();
    }

    @Test
    void defaultConstructor() {
        Assertions.assertThat(pane).hasExactlyNumChildren(1);
    }
}
