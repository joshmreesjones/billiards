package edu.ncsu;

import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Rectangle;

public class Bumper extends GameObject {
	public Bumper(float x, float y, float width, float height) {
		Rectangle rect = new Rectangle(width, height);

		this.addFixture(rect);
		this.translate(x, y);

		this.setMass(Mass.Type.INFINITE);
	}

	public Rectangle getRectangle() {
		return (Rectangle) this.getFixture(0).getShape();
	}

	public float getX() {
		return (float) getRectangle().getCenter().x - (getWidth() / 2);
	}

	public float getY() {
		return (float) getRectangle().getCenter().y - (getHeight() / 2);
	}

	public float getWidth() {
		return (float) getRectangle().getWidth();
	}

	public float getHeight() {
		return (float) getRectangle().getHeight();
	}
}
