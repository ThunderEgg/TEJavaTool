package dev.thunderegg.meshcreator.twodimensiontimer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
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
	@FXML
	private ChoiceBox<String> statChoice;
	@FXML
	private Canvas patchCanvas;

	private PatchPainter canvasPainter;
	Timer timer;
	private HashMap<Integer, ArrayList<Patch>> domains;

	Integer selectedDomain;
	private PatchStatistics currentStats;
	private String selectedStat;

	@FXML
	private void initialize() {
		treeView.getSelectionModel().selectedItemProperty().addListener(
				(ChangeListener<TreeItem<Statistics>>) (ObservableValue<? extends TreeItem<Statistics>> observable,
						TreeItem<Statistics> oldValue, TreeItem<Statistics> newValue) -> {
					setStatistics(newValue.getValue());
				});
		statChoice.getSelectionModel().selectedIndexProperty()
				.addListener((ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldValue,
						Number newValue) -> {
					if (newValue.intValue() >= 0) {
						setSelectedStat(statChoice.getItems().get((Integer) newValue));
					}
				});
		domainChoice.getSelectionModel().selectedIndexProperty()
				.addListener((ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldValue,
						Number newValue) -> {
					if (newValue.intValue() >= 0) {
						setSelectedDomain(domainChoice.getItems().get((Integer) newValue));
					}
				});

		canvasPainter = new PatchPainter(patchCanvas);
	}

	private void setSelectedDomain(Integer domainId) {
		selectedDomain = domainId;
		canvasPainter.setPatches(SquareWithText.getRectanglesForDomain(domains.get(domainId)));
	}

	private void setSelectedStat(String name) {
		selectedStat = name;
		Collection<Integer> domainsForStats = currentStats.getDomainsForName(name);
		domainChoice.getItems().clear();
		domainChoice.getItems().addAll(domainsForStats);
		if (domainsForStats.contains(selectedDomain)) {
			domainChoice.getSelectionModel().select(selectedDomain);
		} else {
			domainChoice.getSelectionModel().clearAndSelect(0);
			selectedDomain = domainChoice.getValue();
		}
	}

	private void setStatistics(PatchStatistics stats) {
		currentStats = stats;
		domainChoice.getItems().clear();
		statChoice.getItems().clear();
		if (stats != null) {
			statChoice.getItems().addAll(stats.getStatisticNames());
			statChoice.getSelectionModel().clearAndSelect(0);
		}
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
