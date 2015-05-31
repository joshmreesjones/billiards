package edu.ncsu;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Vector2;

import org.newdawn.slick.Color;

public class PoolBall extends Body {
	private Color color;

	// 2.25 inch diameter = .028575 meter radius
	private static float RADIUS = .028575f; // meters
	private static float MASS = .17f; // kilograms
	private static float DENSITY = 217.97925f; // kg/m^3
	private static float FRICTION = .08f;
	private static float RESTITUTION = .95f;

	// rad/s/s to m/s/s
	// [5, 10, 15] * 2*pi*r
	private static float ACCELERATION1 = -0.8977101007632834f;
	private static float ACCELERATION2 = -1.7954202015265668f;
	private static float ACCELERATION3 = -2.6931303022898500f;

	public PoolBall(float x, float y, Color color) {
		this.color = color;

		Circle ballShape = new Circle(RADIUS);
		BodyFixture ballFixture = new BodyFixture(ballShape);

		ballFixture.setDensity(DENSITY);
		ballFixture.setFriction(FRICTION);
		ballFixture.setRestitution(RESTITUTION);
		ballFixture.createMass();

		this.addFixture(ballFixture);
		this.translate(x, y);
		this.setLinearDamping(.999999);

		// configure mass once everything mass depends on
		// has been configured
		this.setMass(Mass.Type.NORMAL);
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
