package edu.ncsu.billiards.setups;

import edu.ncsu.billiards.gameobjects.Cushion;
import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

import org.dyn4j.geometry.Vector2;

import org.newdawn.slick.Color;

/**
 * A setup which should not result in a collision.
 *
 * Assuming window dimensions:
 *     - Width:  872 pixels
 *     - Height: 472 pixels
 *
 * and a pixels/meter scale of:
 *     - 328 pixels/meter
 *
 * We obtain these values:
 *     - distance between window edge and pool table: .111 meters
 *     - width of cushions: .106 meters
 *     - total window width: 2.659 meters
 *     - total window height: 1.439 meters
 *     - coordinates of normal cue ball start: (.738, .72)
 */
public class NoCollisionSetup extends Setup {
	public ArrayList<Pocket> generatePockets() {
		ArrayList<Pocket> pockets = new ArrayList<Pocket>();

		Pocket pocket1 = new Pocket(2, .62f, new Vector2(-1, 0));
		Pocket pocket2 = new Pocket(2, .82f, new Vector2(-1, 0));

		pocket1.setDestination(pocket2);
		pocket1.setTimeDifference(-0.35);

		pocket2.setDestination(pocket1);
		pocket2.setTimeDifference(-0.35);

		pockets.add(pocket1);
		pockets.add(pocket2);

		return pockets;
	}

	public ArrayList<PoolBall> generateBalls() {
		ArrayList<PoolBall> balls = new ArrayList<PoolBall>();

		PoolBall ball = new PoolBall(.738f, .72f, Color.white);

		balls.add(ball);

		return balls;
	}
}
