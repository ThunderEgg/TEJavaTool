package dev.thunderegg.meshcreator.twodimensions;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import dev.thunderegg.meshcreator.Forest;
import dev.thunderegg.GsonAdapters;
import dev.thunderegg.meshcreator.Levels;
import dev.thunderegg.meshcreator.threedimensions.RMCreator3D;

public class RMCreator2D extends Application {

	private RMCanvasPane panel;
	// private JFileChooser fc;
	private ToolBar toolbar;
	private ToggleGroup mode;
	private ToggleButton refine_button;
	private ToggleButton coarsen_button;
	private ToggleButton add_button;
	private Button balance_button;
	private Forest forest;
	Stage primary_stage;

	public void start(Stage primaryStage) {
		primary_stage = primaryStage;
		forest = new Forest(2);
		refine_button = new ToggleButton("Refine");
		refine_button.setUserData(Mode.refine);
		// refine_button.addActionListener(this);
		coarsen_button = new ToggleButton("Coarsen");
		coarsen_button.setUserData(Mode.coarsen);
		// coarsen_button.addActionListener(this);
		add_button = new ToggleButton("Add");
		add_button.setUserData(Mode.add);
		mode = new ToggleGroup();
		refine_button.setToggleGroup(mode);
		coarsen_button.setToggleGroup(mode);
		add_button.setToggleGroup(mode);
		mode.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {
				if (new_toggle == null)
					panel.setMode(null);
				else
					panel.setMode((Mode) mode.getSelectedToggle().getUserData());
			}
		});
		balance_button = new Button("Balance");
		balance_button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				Stage stage = new Stage();
				RMBalance2D amc = new RMBalance2D(forest);
				amc.start(stage);
				((Node) (t.getSource())).getScene().getWindow().hide();
			}
		});
		// add_button.addActionListener(this);
		toolbar = new ToolBar(refine_button, coarsen_button, add_button, balance_button);
		panel = new RMCanvasPane(forest);
		BorderPane root = new BorderPane();
		root.setCenter(panel);
		VBox vbox = new VBox();
		Menu file_menu = new Menu("File");
		MenuBar menuBar = new MenuBar();
		Menu new_menu = new Menu("New");
		MenuItem new2d_item = new MenuItem("2D Mesh");
		new2d_item.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				Stage stage = new Stage();
				RMCreator2D amc = new RMCreator2D();
				amc.start(stage);
			}
		});
		MenuItem new3d_item = new MenuItem("3D Mesh");
		new3d_item.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				Stage stage = new Stage();
				RMCreator3D amc = new RMCreator3D();
				amc.start(stage);
			}
		});
		new_menu.getItems().addAll(new2d_item, new3d_item);

		MenuItem save_item = new MenuItem("Save");
		save_item.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				save();
			}
		});
		MenuItem open_item = new MenuItem("Open");
		open_item.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				open();
			}
		});
		file_menu.getItems().addAll(new_menu, save_item, open_item);
		menuBar.getMenus().add(file_menu);
		vbox.getChildren().addAll(menuBar, toolbar);
		root.setTop(vbox);

		Scene scene = new Scene(root, 500, 500);
		primaryStage.setTitle("2D Refined Mesh Creator");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void save() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File out_file = fileChooser.showSaveDialog(primary_stage);
		if (out_file != null) {
			outputMeshGraphJson(out_file);
		}
	}

	private void outputMeshGraphJson(File out_file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		gson = GsonAdapters.getNewGson();

		Levels levels = new Levels(forest);

		try {
			FileWriter writer = new FileWriter(out_file);
			gson.toJson(levels, writer);
			writer.flush();
			writer.close();
		} catch (JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void open() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File out_file = fileChooser.showOpenDialog(primary_stage);
		if (out_file != null) {
			readMeshGraphJson(out_file);
		}
	}

	private void readMeshGraphJson(File in_file) {
		Gson gson = GsonAdapters.getNewGson();

		try {
			FileReader reader = new FileReader(in_file);
			Levels levels = gson.fromJson(reader, Levels.class);
			reader.close();

			Stage stage = new Stage();
			RMBalance2D amc = new RMBalance2D(levels);
			amc.start(stage);
			primary_stage.getScene().getWindow().hide();
		} catch (JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
