package edu.ncsu;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Vector2;

public class PoolBall extends GameObject {
	public PoolBall(float x, float y, float radius) {
		BodyFixture circle = new BodyFixture(new Circle(radius));

		circle.setDensity(1);
		circle.createMass();

		this.addFixture(new Circle(radius));
		this.translate(x, y);
		this.setLinearDamping(0.0003);

		// configure mass once everything mass depends on
		// has been configured
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
