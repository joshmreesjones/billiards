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
			// find out  when it hits the top
			float topTime = delta * (cushionMinY - startMaxY) / deltaY;

			// y coordinate of PoolBall when it hits top wall
			float intersectionY = startCenterY + (velocityY * topTime);

			velocityY = -velocityY;

			float finalY = intersectionY + (velocityY * (delta - topTime));

			// set position
			setCenterX(startCenterX + velocityX * delta);
			setCenterY(finalY);

			// set velocity
			velocity.set(velocityX, velocityY);

			return;
		}

		// does the circle pass through the bottom of the rectangle?
		if (startMinY >  cushionMaxY && nextMinY <= cushionMaxY) {
			// find out when it hits the bottom
			float bottomTime = delta * (startMinY - cushionMaxY) / deltaY;

			// x coordinate of PoolBall when it hits bottom wall
		}

		// does the circle pass through the left side of the rectangle?
		if (startMaxX < cushionMinX && nextMaxX >= cushionMinX) {
			System.out.println("Hit left wall.");
			// find out when it hits the left
		}

		// does the circle pass through the right side of the rectangle?
		if (startMinX > cushionMaxX && nextMinX <= cushionMaxX) {
			System.out.println("Hit right wall.");
			// find out when it hits the right
		}

		// Otherwise, we are about to hit a corner




















		updatePosition(delta);
		/*
		float radius = getRadius();

		float startCenterX = getCenterX();
		float startCenterY = getCenterY();
		
		float nextCenterX = nextX + radius;
		float nextCenterY = nextY + radius;

		float L = cushion.getMinX();
		float T = cushion.getMinY();
		float R = cushion.getMaxX();
		float B = cushion.getMaxY();

		float dx = nextX - getX();
		float dy = nextY - getY();
		float invdx = (dx == 0.0f ? 0.0f : 1.0f / dx);
		float invdy = (dy == 0.0f ? 0.0f : 1.0f / dy);
		float cornerX = Float.MAX_VALUE;
		float cornerY = Float.MAX_VALUE;

		float intersectionCenterX = Float.MAX_VALUE;
		float intersectionCenterY = Float.MAX_VALUE;

		if (startCenterX - radius < L && nextCenterX + radius > L) {
			float ltime = ((L - radius) - startCenterX) * invdx;

			if (ltime >= 0.0f && ltime <= 1.0f) {
				float ly = dy * ltime + startCenterY;

				if (ly >= T && ly <= B) {
					intersectionCenterX = dx * ltime + startCenterX;
					intersectionCenterY = ly;
					//return new Intersection( dx * ltime + start.x, ly, ltime, -1, 0, L, ly );
				}
			}

			cornerX = L;
		}
		
		if (startCenterX + radius > R && nextCenterX - radius < R ) {
			float rtime = (startCenterX - (R + radius)) * -invdx;

			if (rtime >= 0.0f && rtime <= 1.0f) {
				float ry = dy * rtime + startCenterY;

				if (ry >= T && ry <= B) {
					intersectionCenterX = dx * rtime + startCenterX;
					intersectionCenterY = ry;
					//return new Intersection( dx * rtime + start.x, ry, rtime, 1, 0, R, ry );
				}
			}

			cornerX = R;
		}
		
		if (startCenterY - radius < T && nextCenterY + radius > T) {
			float ttime = ((T - radius) - startCenterY) * invdy;

			if (ttime >= 0.0f && ttime <= 1.0f) {
				float tx = dx * ttime + startCenterX;

				if (tx >= L && tx <= R) {
					intersectionCenterX = tx;
					intersectionCenterY = dy * ttime + startCenterY;
					//return new Intersection( tx, dy * ttime + start.y, ttime, 0, -1, tx, T );
				}
			}

			cornerY = T;
		}

		if (startCenterY + radius > B && nextCenterY - radius < B) {
			float btime = (startCenterY - (B + radius)) * -invdy;

			if (btime >= 0.0f && btime <= 1.0f) {
				float bx = dx * btime + startCenterX;

				if (bx >= L && bx <= R) {
					intersectionCenterX = bx;
					intersectionCenterY = dy * btime + startCenterY;
					//return new Intersection( bx, dy * btime + start.y, btime, 0, 1, bx, B );
				}
			}

			cornerY = B;
		}

		if (cornerX != Float.MAX_VALUE && cornerY == Float.MAX_VALUE) {
			cornerY = (dy > 0.0f ? B : T);
		}

		if (cornerY != Float.MAX_VALUE && cornerX == Float.MAX_VALUE) {
			cornerX = (dx > 0.0f ? R : L);
		}
		*/
		
		// TODO unfinished

		/*
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
	}
}
