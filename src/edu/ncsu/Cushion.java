package edu.ncsu;

import org.dyn4j.dynamics.Body;

import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Rectangle;

public class Cushion extends Body {
	public Cushion(float x, float y, float width, float height) {
		Rectangle rect = new Rectangle(width, height);

		this.addFixture(rect);
		this.translate(
			x + (width  / 2),
			y + (height / 2)
		);

		this.setMass(Mass.Type.INFINITE);
	}

	public Rectangle getRectangle() {
		return (Rectangle) this.getFixture(0).getShape();
	}

	public float getX() {
		return (float) this.getWorldCenter().x - (getWidth() / 2);
	}

	public float getY() {
		return (float) this.getWorldCenter().y - (getHeight() / 2);
	}

	public float getWidth() {
		return (float) getRectangle().getWidth();
	}

	public float getHeight() {
		return (float) getRectangle().getHeight();
	}
}
