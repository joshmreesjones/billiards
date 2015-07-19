package edu.ncsu.billiards.ui;

import org.newdawn.slick.geom.Rectangle;

public class Button {
	private static final float WIDTH = 100;
	private static final float HEIGHT = 32;

	private String label;

	private Rectangle rectangle;



	public Button(String label, float x, float y) {
		this.label = label;

		this.rectangle = new Rectangle(x, y, WIDTH, HEIGHT);
	}



	public String getLabel() {
		return label;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}
}
