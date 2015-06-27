package edu.ncsu.billiards.gameobjects;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;

import org.dyn4j.geometry.Circle;

public class Pocket extends Body {
	// 4.25 inches = 0.10795 meters
	//private float RADIUS = 0.10795f;
	private float RADIUS = .05f;

	// the Pocket linked to this Pocket
	private Pocket link;



	public Pocket(float x, float y) {
		Circle circleShape = new Circle(RADIUS);

		BodyFixture circleFixture = new BodyFixture(circleShape);
		circleFixture.setSensor(true);

		this.addFixture(circleFixture);
		this.translate(x, y);
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
}
