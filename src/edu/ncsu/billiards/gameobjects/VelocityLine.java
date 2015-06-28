package edu.ncsu.billiards.gameobjects;

import org.dyn4j.geometry.Vector2;

public class VelocityLine {
	// using physics units
	private double startX;
	private double startY;
	private double endX;
	private double endY;

	public VelocityLine() {
		this.startX = 0;
		this.startY = 0;
		this.endX = 0;
		this.endY = 0;
	}

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

	public Vector2 getUnitDirectionVector() {
		return (new Vector2(endX - startX, endY - startY)).getNormalized();
	}
}
