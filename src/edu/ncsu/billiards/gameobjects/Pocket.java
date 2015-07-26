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

	// the exit direction of pool balls
	private Vector2 exitDirection;



	/**
	 * Constructs a new Pocket.
	 *
	 * @param x the x position in meters of the new pocket
	 * @param y the y position in meters of the new pocket
	 */
	public Pocket(float x, float y, Vector2 exitDirection) {
		Circle circleShape = new Circle(RADIUS);

		BodyFixture circleFixture = new BodyFixture(circleShape);
		circleFixture.setSensor(true);

		this.addFixture(circleFixture);
		this.translate(x, y);

		this.exitDirection = exitDirection;
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
		this.exitDirection = direction;
	}

	public Vector2 getExitDirection() {
		return this.exitDirection;
	}



	public double getRadius() {
		return RADIUS;
	}



	public VelocityLine getVelocityLine() {
		VelocityLine line = new VelocityLine();

		double startX = getWorldCenter().x;
		double startY = getWorldCenter().y;
		double endX = startX + getExitDirection().setMagnitude(.2).x;
		double endY = startY + getExitDirection().setMagnitude(.2).y;

		line.setStart(startX, startY);
		line.setEnd(endX, endY);

		return line;
	}
}
