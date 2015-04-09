package edu.ncsu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class PoolBall extends Circle {
	// scales velocity appropriately from screen dimensions
	private static final float VELOCITY_SCALE_FACTOR = 0.0025f;

	// minimum moving velocity - below this will be set to 0
	private static final float ZERO_VELOCITY_CUTOFF = .002f;

	// the velocity of this ball
	private Vector2f velocity;

	public PoolBall(float centerPointX, float centerPointY, float radius) {
		super(centerPointX, centerPointY, radius);

		this.velocity = new Vector2f(0, 0);
	}

	public Vector2f getVelocity() {
		return velocity.scale(1 / VELOCITY_SCALE_FACTOR);
	}

	public void setVelocity(float x, float y) {
		velocity.set(VELOCITY_SCALE_FACTOR * x,
					 VELOCITY_SCALE_FACTOR * y);
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

	public float[] nextPosition(int delta) {
		float[] result = new float[2];

		// calculate changes in x and y directions
		float deltaX = velocity.getX() * delta;
		float deltaY = velocity.getY() * delta;

		result[0] = getX() + deltaX;
		result[1] = getY() + deltaY;

		return result;
	}

	public void updatePosition(int delta) {
		float[] position = nextPosition(delta);

		// set new position
		setX(position[0]);
		setY(position[1]);

		// TODO decelerate faster at slower velocities,
		// especially around v = .02

		// decrease velocity
		if (velocity.length() < ZERO_VELOCITY_CUTOFF) {
			velocity.set(0, 0);
		} else {
			velocity.scale(.99f);
		}
	}

	public void handleCushionCollision(Rectangle cushion, float nextX, float nextY, int delta) {
		float radius = getRadius();

		float centerX = getCenterX();
		float centerY = getCenterY();

		float cushionMinX = cushion.getMinX();
		float cushionMinY = cushion.getMinY();
		float cushionMaxX = cushion.getMaxX();
		float cushionMaxY = cushion.getMaxY();

		boolean leftCrossed = false;
		boolean rightCrossed = false;
		boolean topCrossed = false;
		boolean bottomCrossed = false;

		/*
		if (centerX + radius <  cushionMinX &&
			  nextX + radius >= cushionMinX) {
			// left boundary was crossed
			System.out.println("Left crossed.");
			leftCrossed = true;
		}

		if (centerX - radius >  cushionMaxX &&
			  nextX - radius <= cushionMaxX) {
			// right boundary was crossed
			System.out.println("Right crossed.");
			rightCrossed = true;
		}

		if (centerY + radius <  cushionMinY &&
			  nextY + radius >= cushionMinY) {
			// top boundary was crossLefted
			System.out.println("Top crossed.");
			topCrossed = true;
		}

		if (centerY - radius >  cushionMaxY &&
			  nextY - radius <= cushionMaxY) {
			// bottom boundary was crossed
			System.out.println("Bottom crossed.");
			bottomCrossed = true;
		}
		*/
		


		// otherwise it's an intersection with a corner
		updatePosition(delta);
	}
}
