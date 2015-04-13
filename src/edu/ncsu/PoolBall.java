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

	// acceleration of the PoolBall
	private static final float ACCELERATION = -0.01f;

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
			velocity.scale(1 + ACCELERATION);
		}
	}

	public void handleCushionCollision(Rectangle cushion, float nextX, float nextY, int delta) {
		float radius = getRadius();

		float startCenterX = getCenterX();
		float startCenterY = getCenterY();
		float nextCenterX = nextX + radius;
		float nextCenterY = nextY + radius;

		float startMinX = startCenterX - radius;
		float startMinY = startCenterY - radius;
		float startMaxX = startCenterX + radius;
		float startMaxY = startCenterY + radius;

		float nextMinX = nextCenterX - radius;
		float nextMinY = nextCenterY - radius;
		float nextMaxX = nextCenterX + radius;
		float nextMaxY = nextCenterY + radius;

		float cushionMinX = cushion.getMinX();
		float cushionMinY = cushion.getMinY();
		float cushionMaxX = cushion.getMaxX();
		float cushionMaxY = cushion.getMaxY();

		float deltaX = Math.abs(nextCenterX - startCenterX);
		float deltaY = Math.abs(nextCenterY - startCenterY);

		float velocityX = velocity.getX();
		float velocityY = velocity.getY();

		if (startMaxY < cushionMinY && nextMaxY >= cushionMinY) {
			// find out when it hits the top
			float topTime = delta * (cushionMinY - startMaxY) / deltaY;

			// y coordinate of PoolBall when it hits top wall
			float intersectionY = startCenterY + (velocityY * topTime);

			velocityY = -velocityY;

			float finalY = intersectionY + (velocityY * (delta - topTime));

			// set position
			setCenterX(startCenterX + (velocityX * delta));
			setCenterY(finalY);

			// set velocity
			velocity.set(velocityX, velocityY);

			return;
		}

		// does the circle pass through the bottom of the rectangle?
		if (startMinY >  cushionMaxY && nextMinY <= cushionMaxY) {
			// find out when it hits the bottom
			float bottomTime = delta * (startMinY - cushionMaxY) / deltaY;

			// y coordinate of PoolBall when it hits bottom wall
			float intersectionY = startCenterY - (velocityY * bottomTime);

			velocityY = -velocityY;

			float finalY = intersectionY + (velocityY * (delta - bottomTime));

			// set position
			setCenterX(startCenterX + (velocityX * delta));
			setCenterY(finalY);

			// set velocity
			velocity.set(velocityX, velocityY);

			return;
		}

		// does the circle pass through the left side of the rectangle?
		if (startMaxX < cushionMinX && nextMaxX >= cushionMinX) {
			// find out when it hits the left
			float leftTime = delta * (cushionMinX - startMaxX) / deltaX;

			// x coordinate of PoolBall when it hits left wall
			float intersectionX = startCenterX + (velocityX * leftTime);

			velocityX = -velocityX;

			float finalX = intersectionX + (velocityX * (delta - leftTime));

			// set position
			setCenterX(finalX);
			setCenterY(startCenterY + (velocityY * delta));

			// set velocity
			velocity.set(velocityX, velocityY);

			return;
		}

		// does the circle pass through the right side of the rectangle?
		if (startMinX > cushionMaxX && nextMinX <= cushionMaxX) {
			// find out when it hits the right
			float rightTime = delta * (startMinX - cushionMaxX) / deltaX;

			// y coordinate of PoolBall when it hits bottom wall
			float intersectionX = startCenterX - (velocityX * rightTime);

			velocityX = -velocityX;

			float finalX = intersectionX + (velocityX * (delta - rightTime));

			// set position
			setCenterX(finalX);
			setCenterY(startCenterY + (velocityY * delta));

			// set velocity
			velocity.set(velocityX, velocityY);

			return;
		}

		// Otherwise, we are about to hit a corner
	}
}
