package edu.ncsu.billiards.gameobjects;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Vector2;

public class Pocket extends Body {
	// 4.25 inches = 0.10795 meters
	//private float RADIUS = 0.10795f;
	private float RADIUS = .05f;
	
	// the Pocket linked to this Pocket
	private Pocket destination;

	// the amount of time to send a ball back from this pocket
	private double timeDifference;

	// rendered velocity line for exit direction
	private VelocityLine velocityLine;

	// the exit direction of pool balls
	private Vector2 unitExitDirection;



	/**
	 * Constructs a new Pocket.
	 *
	 * @param x the x position in meters of the new pocket
	 * @param y the y position in meters of the new pocket
	 */
	public Pocket(float x, float y) {
		Circle circleShape = new Circle(RADIUS);

		BodyFixture circleFixture = new BodyFixture(circleShape);
		circleFixture.setSensor(true);

		this.addFixture(circleFixture);
		this.translate(x, y);

		this.velocityLine = new VelocityLine();

		this.unitExitDirection = new Vector2(0, 0);
	}



	public Pocket getDestination() {
		return this.destination;
	}

	public void setDestination(Pocket destination) {
		this.destination = destination;
	}

	public double getTimeDifference() {
		return this.timeDifference;
	}

	public void setTimeDifference(double time) {
		this.timeDifference = time;
	}

	public void setExitDirection(Vector2 direction) {
		this.unitExitDirection = direction.getNormalized();
	}

	public Vector2 getUnitExitDirection() {
		return this.unitExitDirection;
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



	public VelocityLine getVelocityLine() {
		return this.velocityLine;
	}
}
