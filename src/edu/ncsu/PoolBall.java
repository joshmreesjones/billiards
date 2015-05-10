package edu.ncsu;

import org.dyn4j.dynamics.Body;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Vector2;

public class PoolBall extends Body {
	public PoolBall(float x, float y, float radius) {
		this.addFixture(new Circle(radius));

		this.setMass();
	}

	public Circle getCircle() {
		return (Circle) this.getFixture(0).getShape();
	}

	public float getX() {
		return (float) getCircle().getCenter().x;
	}

	public float getY() {
		return (float) getCircle().getCenter().y;
	}

	public float getRadius() {
		return (float) getCircle().getRadius();
	}
}
