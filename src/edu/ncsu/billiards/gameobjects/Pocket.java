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
	private Pocket link;

	// rendered velocity line for exit direction
	private VelocityLine velocityLine;

	// the exit direction of pool balls
	private Vector2 exitDirection;



	public Pocket(float x, float y) {
		Circle circleShape = new Circle(RADIUS);

		BodyFixture circleFixture = new BodyFixture(circleShape);
		circleFixture.setSensor(true);

		this.addFixture(circleFixture);
		this.translate(x, y);

		this.velocityLine = new VelocityLine();

		this.exitDirection = new Vector2(0, 0);
	}



	public Pocket getLink() {
		return link;
	}

	public void setLink(Pocket link) {
		this.link = link;
	}



	private Circle getCircle() {
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



	public void setExitDirection(Vector2 direction) {
		this.exitDirection = direction;
	}

	public Vector2 getExitDirection() {
		return this.exitDirection;
	}
}
