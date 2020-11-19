package dev.thunderegg.meshcreator.twodimensiontimer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import dev.thunderegg.GsonAdapters;
import dev.thunderegg.Patch;
import dev.thunderegg.Timer;

public class Timer2DController {

	@FXML
	private BorderPane borderPane;
	@FXML
	private TimerPane timerPane;
	@FXML
	private TreeView<Statistics> treeView;
	@FXML
	private ChoiceBox<Integer> domainChoice;

	private PatchCanvasPainter canvasPainter;
	Timer timer;
	private HashMap<Integer, ArrayList<Patch>> domains;

	@FXML
	private void initialize() {
		treeView.getSelectionModel().selectedItemProperty().addListener(
				(ChangeListener<TreeItem<Statistics>>) (ObservableValue<? extends TreeItem<Statistics>> observable,
						TreeItem<Statistics> oldValue, TreeItem<Statistics> newValue) -> {
					setStatistics(newValue.getValue());
				});
	}

	private void setStatistics(PatchStatistics value) {
	}

	@FXML
	private void handleOpenFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File in_file = fileChooser.showOpenDialog(treeView.getScene().getWindow());
		Timer timer = null;
		if (in_file != null) {
			try {
				Gson gson = GsonAdapters.getNewGson();
				FileReader reader = new FileReader(in_file);
				timer = gson.fromJson(reader, Timer.class);
				reader.close();
			} catch (RuntimeException | IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Unable to open file.");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
		if (timer != null) {
			setTimer(timer);
		}
	}

	public void setTimer(Timer timer) {
		this.domains = timer.domains;
		Statistics stats = new Statistics(timer);
		treeView.setRoot(stats.getTree());
		setStatistics(null);
	}

	@FXML
	public void handleMouseDraggedOnCanvas(MouseEvent me) {
	}

	@FXML
	public void handleScrollOnCanvas(ScrollEvent se) {
	}

}
