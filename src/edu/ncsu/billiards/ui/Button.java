package edu.ncsu.billiards.ui;

import org.newdawn.slick.geom.Rectangle;

public class Button {
	private static final int WIDTH = 200;
	private static final int HEIGHT = 32;

	private int x;
	private int y;

	private String label;



	public Button(String label) {
		this.label = label;
		x = 0;
		y = 0;
	}



	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public String getLabel() {
		return label;
	}

	public Rectangle getHitBox() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}



	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
