package edu.ncsu;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Vector2;

import org.newdawn.slick.Color;

public class PoolBall extends GameObject {
	private Color color;

	public PoolBall(float x, float y, Color color) {
		this.color = color;

		// 2.25 in diameter = .028575 m radius
		Circle ballShape = new Circle(0.028575);
		BodyFixture ball = new BodyFixture(ballShape);

		ball.setDensity(1);
		ball.createMass();

		this.addFixture(ball);
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

	public Color getColor() {
		return color;
	}
}
