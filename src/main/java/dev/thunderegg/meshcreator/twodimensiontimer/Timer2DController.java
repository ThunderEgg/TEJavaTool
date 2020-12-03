package dev.thunderegg.meshcreator.twodimensiontimer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.IllegalFormatConversionException;

import com.google.gson.Gson;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import dev.thunderegg.GsonAdapters;
import dev.thunderegg.Patch;
import dev.thunderegg.Timer;
import dev.thunderegg.colormaps.ColorMap;
import dev.thunderegg.colormaps.ColorMaps;

public class Timer2DController {

	@FXML
	private BorderPane borderPane;
	@FXML
	private TreeView<Statistics> treeView;
	@FXML
	private ChoiceBox<Integer> domainChoice;
	@FXML
	private ChoiceBox<String> statChoice;
	@FXML
	private ChoiceBox<String> subStatChoice;
	@FXML
	private ComboBox<ColorMap> cmapChoice;
	@FXML
	private Canvas patchCanvas;
	@FXML
	private AnchorPane patchCanvasPane;
	@FXML
	private TextField formatText;

	private PatchPainter canvasPainter;
	Timer timer;
	private HashMap<Integer, ArrayList<Patch>> domains;

	Integer selectedDomain;
	private PatchStatistics currentStats;
	private String selectedStat;
	private double lastMouseX;
	private double lastMouseY;
	private String selectedSubStat;
	private String formatString = "%.2e";

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
		subStatChoice.getSelectionModel().selectedIndexProperty()
				.addListener((ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldValue,
						Number newValue) -> {
					if (newValue.intValue() >= 0) {
						setSlectedSubStat(subStatChoice.getItems().get((Integer) newValue));
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
		subStatChoice.getItems().addAll("Average", "Min", "Max");
		subStatChoice.getSelectionModel().clearAndSelect(0);
		patchCanvas.widthProperty().bind(patchCanvasPane.widthProperty());
		patchCanvas.heightProperty().bind(patchCanvasPane.heightProperty());
		patchCanvas.heightProperty().addListener(observable -> redrawCanvas());
		patchCanvas.widthProperty().addListener(observable -> redrawCanvas());

		cmapChoice.getItems().addAll(ColorMaps.getColorMaps());
		cmapChoice.setCellFactory(new Callback<ListView<ColorMap>, ListCell<ColorMap>>() {
			@Override
			public ListCell<ColorMap> call(ListView<ColorMap> p) {
				return new ListCell<ColorMap>() {
					@Override
					protected void updateItem(ColorMap cmap, boolean empty) {
						super.updateItem(cmap, empty);

						if (cmap == null || empty) {
							setGraphic(null);
						} else {
							setText(cmap.getName());
							ColorMapImageGenerator gen = new ColorMapImageGenerator(cmap);
							ImageView view = new ImageView(gen.getImage(200));
							view.setFitHeight(30);
							setGraphic(view);
						}
					}
				};
			}
		});
	}

	private void setSlectedSubStat(String selectedSubStat) {
		this.selectedSubStat = selectedSubStat;
		updatePatches();
	}

	private void updatePatches() {
		if (currentStats != null) {
			canvasPainter
					.setPatches(SquareWithText.getRectanglesForDomain(domains.get(selectedDomain), (Patch patch) -> {
						Statistic stat = currentStats
								.getStatisticForPatch(new PatchKey(selectedStat, selectedDomain, patch.id));

						double value = stat.getStatistic(selectedSubStat);
						try {
							return String.format(formatString, value);
						} catch (IllegalFormatConversionException e) {
							return String.format(formatString, (int) value);
						}
					}));
		}
	}

	private void setSelectedDomain(Integer domainId) {
		selectedDomain = domainId;
		updatePatches();
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
	public void handleMousePressedOnCanvas(MouseEvent me) {
		lastMouseX = me.getSceneX();
		lastMouseY = me.getSceneY();
	}

	@FXML
	public void handleMouseDraggedOnCanvas(MouseEvent me) {
		double newMouseX = me.getSceneX();
		double newMouseY = me.getSceneY();
		canvasPainter.translate(newMouseX - lastMouseX, newMouseY - lastMouseY);
		canvasPainter.paint();
		lastMouseX = newMouseX;
		lastMouseY = newMouseY;
	}

	@FXML
	public void redrawCanvas() {
		canvasPainter.paint();
	}

	@FXML
	public void handleScrollOnCanvas(ScrollEvent se) {
		double scale = canvasPainter.getScale();
		double delta = se.getDeltaY() / se.getMultiplierY();
		double deltaScale = scale * (1 - Math.pow(1.1, delta));
		scale += deltaScale;
		canvasPainter.setScale(scale);
		double xOrigin = canvasPainter.getXTranslate();
		double yOrigin = canvasPainter.getYTranslate();
		double xTranslate = xOrigin - ((xOrigin - se.getX()) * Math.pow(1.1, delta) + se.getX());
		double yTranslate = yOrigin - ((yOrigin - se.getY()) * Math.pow(1.1, delta) + se.getY());
		canvasPainter.translate(xTranslate, yTranslate);
		canvasPainter.paint();
	}

	@FXML
	private void setFormatText(ActionEvent event) {
		formatString = formatText.getText();
		updatePatches();
	}

}
