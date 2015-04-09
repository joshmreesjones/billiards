package edu.ncsu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class PoolBall extends Circle {
	private Vector2f velocity;

	public PoolBall(float centerPointX, float centerPointY, float radius) {
		super(centerPointX, centerPointY, radius);

		this.velocity = new Vector2f(0, 0);
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(float x, float y) {
		velocity.set(x, y);
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public void draw(Graphics g) {
		// store color and antialiasing to restore once
		// we are done drawing
		Color color = g.getColor();
		boolean isAntiAlias = g.isAntiAlias();

		// set colors and antialiasing
		g.setColor(Color.white);
		g.setAntiAlias(true);

		// draw ball outline and fill
		g.draw(this);
		g.fill(this);

		// reset color and antialias to previous values
		g.setColor(color);
		g.setAntiAlias(isAntiAlias);
	}

	public void updateVelocity(int delta) {
		// TODO decelerate faster at slower velocities,
		// especially around v = .02

		// calculate changes in x and y directions
		float deltaX = velocity.getX() * delta;
		float deltaY = velocity.getY() * delta;
		
		// set new position
		setX(getX() + deltaX);
		setY(getY() + deltaY);

		// decrease velocity
		if (velocity.length() < .002f) {
			velocity.set(0, 0);
		} else {
			velocity.scale(.99f);
		}
	}
}
