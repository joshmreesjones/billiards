package edu.ncsu.billiards.ui;

public class Button {
	private String label;

	private int x;
	private int y;

	private int width;
	private int height;

	public Button(String label, int x, int y) {
		this.label = label;
		
		this.x = x;
		this.y = y;

		width = 100;
		height = 32;
	}

	public String getLabel() {
		return label;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
