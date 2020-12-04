package dev.thunderegg.meshcreator.twodimensiontimer;

import java.util.Collection;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Translate;
import dev.thunderegg.Patch;

public class PatchPainter {

	private Canvas canvas;
	private double scale = 300;
	private double x_trans = 0;
	private double y_trans = 0;
	private Collection<SquareWithText> patches;

	public PatchPainter(Canvas canvas) {
		this.canvas = canvas;
		paint();
	}

	public void paint() {
		GraphicsContext g = canvas.getGraphicsContext2D();
		Translate r = new Translate(x_trans, y_trans);
		g.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		g.clearRect(-x_trans, -y_trans, canvas.getWidth(), canvas.getHeight());
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
			int x_px = 0 + (int) (rectangle.x * scale);
			int y_px = (int) scale - (int) ((rectangle.y + rectangle.y_length) * scale);
			int x_ln = (int) Math.ceil(scale * rectangle.x_length);
			int y_ln = (int) Math.ceil(scale * rectangle.y_length);
			// g.setFill(rank_color_map.get(p.rank));
			g.setFill(rectangle.color);
			g.setStroke(Color.BLACK);
			g.fillRect(x_px, y_px, x_ln, y_ln);
			g.strokeRect(x_px, y_px, x_ln, y_ln);
			if (rectangle.color.grayscale().getRed() > 0.5) {
				g.setFill(Color.BLACK);
			} else {
				g.setFill(Color.WHITE);
			}
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

	/**
	 * Set the patches to draw
	 * 
	 * @param patches the patches to draw
	 */
	public void setPatches(Collection<SquareWithText> patches) {
		this.patches = patches;
		paint();
	}

	/**
	 * Translate the patches on the canvas
	 * 
	 * @param x the x translation
	 * @param y the y translation
	 */
	public void translate(double x, double y) {
		x_trans += x;
		y_trans += y;
	}

	/**
	 * Get the current x tranlate value
	 * 
	 * @return the value
	 */
	public double getXTranslate() {
		return x_trans;
	}

	/**
	 * Get the current y tranlate value
	 * 
	 * @return the value
	 */
	public double getYTranslate() {
		return y_trans;
	}

	/**
	 * Get the current size value
	 * 
	 * @return the size
	 */
	public double getScale() {
		return scale;
	}

	/**
	 * Set the scale
	 * 
	 * @param scale the scale
	 */
	public void setScale(double scale) {
		this.scale = scale;
	}

}
