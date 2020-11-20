package dev.thunderegg.meshcreator.twodimensiontimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Translate;
import dev.thunderegg.Patch;

public class PatchPainter {

	private double size = 300;
	private Position position = new Position();
	private Canvas canvas;
	private Map<Integer, Color> rank_color_map;
	private Collection<SquareWithText> patches;

	public PatchPainter(Canvas canvas) {
		this.canvas = canvas;
		paint();
	}

	public void paint() {
		GraphicsContext g = canvas.getGraphicsContext2D();
		Translate r = new Translate(position.x_trans, position.y_trans);
		g.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		g.clearRect(-position.x_trans, -position.y_trans, canvas.getWidth(), canvas.getHeight());
		if (patches != null) {
			drawPatches(g);
		}
	}

	private boolean isInBounds(Font font, String text, int x_ln, int y_ln) {
		Text helper = new Text();
		helper.setFont(font);
		helper.setText(text);
		double textWidth = Math.ceil(helper.getLayoutBounds().getWidth());
		double textHeight = Math.ceil(helper.getLayoutBounds().getHeight());
		return x_ln > textWidth && y_ln > textHeight;
	}

	private void drawPatches(GraphicsContext g) {
		g.setTextAlign(TextAlignment.CENTER);
		g.setTextBaseline(VPos.CENTER);
		for (SquareWithText rectangle : patches) {
			int x_px = 0 + (int) (rectangle.x * size);
			int y_px = (int) size - (int) ((rectangle.y + rectangle.y_length) * size);
			int x_ln = (int) Math.ceil(size * rectangle.x_length);
			int y_ln = (int) Math.ceil(size * rectangle.y_length);
			// g.setFill(rank_color_map.get(p.rank));
			g.setFill(Color.WHITE);
			g.setStroke(Color.RED);
			g.fillRect(x_px, y_px, x_ln, y_ln);
			g.strokeRect(x_px, y_px, x_ln, y_ln);
			g.setFill(Color.BLACK);
			if (isInBounds(g.getFont(), rectangle.text, x_ln, y_ln)) {
				g.fillText(rectangle.text, x_px + x_ln / 2, y_px + y_ln / 2);
			}
		}
	}

	private Patch getPatchAt(double[] coord) {
		/*
		 * for (Patch patch : patches) { if (patch.coordIsInside(coord)) { return patch;
		 * } }
		 */
		return null;
	}

	public void setPatches(Collection<SquareWithText> patches) {
		this.patches = patches;
		paint();
	}

}
