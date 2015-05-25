package edu.ncsu;

public class VelocityLine {
	// using physics units
	private double startX;
	private double startY;
	private double endX;
	private double endY;

	public void setStart(double startX, double startY) {
		this.startX = startX;
		this.startY = startY;
	}

	public void setEnd(double endX, double endY) {
		this.endX = endX;
		this.endY = endY;
	}

	public double[] getStart() {
		return new double[]{startX, startY};
	}

	public double[] getEnd() {
		return new double[]{endX, endY};
	}
}
