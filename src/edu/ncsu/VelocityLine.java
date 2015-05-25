package edu.ncsu;

public class VelocityLine {
	private int startX;
	private int startY;
	private int endX;
	private int endY;

	public void setStart(int startX, int startY) {
		this.startX = startX;
		this.startY = startY;
	}

	public void setEnd(int endX, int endY) {
		this.endX = endX;
		this.endY = endY;
	}

	public int[] getStart() {
		return new int[]{startX, startY};
	}

	public int[] getEnd() {
		return new int[]{endX, endY};
	}
}
