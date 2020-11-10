package dev.thunderegg.meshcreator.twodimensiontimer;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Timer2D extends Application {

	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/twodimensiontimer.fxml"));
		Scene scene = new Scene(root, 700, 600);

		stage.setTitle("2D Timing Visualizer");
		stage.setScene(scene);
		stage.show();
	}
}
