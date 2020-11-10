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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import dev.thunderegg.GsonAdapters;
import dev.thunderegg.Info;
import dev.thunderegg.Patch;
import dev.thunderegg.Timer;
import dev.thunderegg.Timing;

public class Timer2DController {

	@FXML
	private BorderPane borderPane;
	@FXML
	private TimerPane timerPane;
	@FXML
	TreeView<PaneSupplier> treeView;

	@FXML
	private void initialize() {
		treeView.getSelectionModel().selectedItemProperty().addListener(
				(ChangeListener<TreeItem<PaneSupplier>>) (ObservableValue<? extends TreeItem<PaneSupplier>> observable,
						TreeItem<PaneSupplier> oldValue, TreeItem<PaneSupplier> newValue) -> {
					borderPane.setCenter(newValue.getValue().get());
				});
	}

	@FXML
	private void handleOpenFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File in_file = fileChooser.showOpenDialog(timerPane.getScene().getWindow());
		if (in_file != null) {
			try {
				readTimer(in_file);
			} catch (RuntimeException | IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Unable to open file.");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
	}

	private static String mmaString(double min, double max, double avg) {
		String s = "min: " + Double.toString(min) + "\n";
		s += "max: " + Double.toString(max) + "\n";
		s += "avg: " + Double.toString(avg);
		return s;
	}

	private void setTimer(Timer timer) {
		TreeItem<PaneSupplier> root = new TreeItem<>();
		AddTimingsToItem(timer.domains, timer.timings, root);
		treeView.setRoot(root);
	};

	private static void AddStats(TreeItem<PaneSupplier> item, Map<Integer, ArrayList<Patch>> domains,
			Map<Pair<Integer, String>, Map<String, TreeItem<PaneSupplier>>> domain_item_children, Timing timing,
			String name, double min, double max, double avg) {
		if (domain_item_children.get(new Pair<>(timing.domain_id, timing.name)) == null) {
			domain_item_children.put(new Pair<>(timing.domain_id, timing.name), new HashMap<>());
		}
		TreeItem<PaneSupplier> tps_item = domain_item_children.get(new Pair<>(timing.domain_id, timing.name)).get(name);
		if (tps_item == null) {
			tps_item = new TreeItem<>(new TimerPaneSupplier(name, domains.get(timing.domain_id)));
			domain_item_children.get(new Pair<>(timing.domain_id, timing.name)).put(name, tps_item);
			item.getChildren().add(tps_item);
		}
		TimerPaneSupplier tps = (TimerPaneSupplier) tps_item.getValue();
		tps.addText(timing.patch_id, mmaString(min, max, avg));

	}

	private static void AddTimingsToItem(Map<Integer, ArrayList<Patch>> domains, ArrayList<Timing> timings,
			TreeItem<PaneSupplier> parent) {
		if (timings != null) {
			Map<Pair<Integer, String>, TreeItem<PaneSupplier>> domain_item_map = new HashMap<>();
			Map<Pair<Integer, String>, Map<String, TreeItem<PaneSupplier>>> domain_item_children = new HashMap<>();
			for (Timing timing : timings) {
				TreeItem<PaneSupplier> item = domain_item_map.get(new Pair<>(timing.domain_id, timing.name));
				if (item == null) {
					item = new TreeItem<>(new EmptyPaneSupplier(timing.name));
					domain_item_map.put(new Pair<>(timing.domain_id, timing.name), item);
					parent.getChildren().add(item);
				}
				if (timing.patch_id != null) {
					AddStats(item, domains, domain_item_children, timing, "Time", timing.min, timing.max,
							timing.sum / timing.num_calls);
					if (timing.infos != null) {
						for (Info info : timing.infos) {
							AddStats(item, domains, domain_item_children, timing, info.name, info.min, info.max,
									info.sum / info.num_calls);
						}
					}

				}
				AddTimingsToItem(domains, timing.timings, item);
			}
		}
	}

	private void readTimer(File in_file) throws IOException {
		Gson gson = GsonAdapters.getNewGson();
		FileReader reader = new FileReader(in_file);
		Timer timer = gson.fromJson(reader, Timer.class);
		reader.close();
		setTimer(timer);
	}
}
