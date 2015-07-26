package edu.ncsu.billiards.setups;

import edu.ncsu.billiards.gameobjects.Cushion;
import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

import org.dyn4j.geometry.Vector2;

import org.newdawn.slick.Color;

public class ParadoxSetup extends Setup {
	public ArrayList<Pocket> generatePockets() {
		ArrayList<Pocket> pockets = new ArrayList<Pocket>();

		Pocket pocket1 = new Pocket(1, 1, new Vector2(0, 1));
		Pocket pocket2 = new Pocket(1.3f, .7f, new Vector2(1, 0));

		pocket1.setDestination(pocket2);
		pocket1.setTimeDifference(-0.2);

		pocket2.setDestination(pocket1);
		pocket2.setTimeDifference(-0.2);

		pockets.add(pocket1);
		pockets.add(pocket2);

		return pockets;
	}

	public ArrayList<PoolBall> generateBalls() {
		ArrayList<PoolBall> balls = new ArrayList<PoolBall>();

		PoolBall ball1 = new PoolBall( .9f, .6f, Color.red);
		PoolBall ball2 = new PoolBall(1.5f,  1f, Color.blue);
		PoolBall ball3 = new PoolBall(1.3f, .8f, Color.green);

		balls.add(ball1);
		balls.add(ball2);
		balls.add(ball3);

		return balls;
	}
}
