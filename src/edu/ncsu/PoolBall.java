package edu.ncsu;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Vector2;

import org.newdawn.slick.Color;

public class PoolBall extends Body {
	private Color color;
	// 2.25 in diameter = .028575 m radius
	private static float RADIUS = .028575f;

	// 1700 kg
	private static float MASS = 1700;

	// rad/s/s to m/s/s
	// [5, 10, 15] * 2*pi*r
	private static float ACCELERATION1 = -0.8977101007632834f;
	private static float ACCELERATION2 = -1.7954202015265668f;
	private static float ACCELERATION3 = -2.6931303022898500f;


	public PoolBall(float x, float y, Color color) {
		RADIUS = .03f;
		this.color = color;

		Circle ballShape = new Circle(RADIUS);
		BodyFixture ball = new BodyFixture(ballShape);

		double area = Math.PI * RADIUS * RADIUS;
		double density = (double) MASS / area;

		ball.createMass();
		ball.setDensity(density);
		ball.setRestitution(0.9);

		this.addFixture(ball);
		this.translate(x, y);
		this.setLinearDamping(.7);

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
