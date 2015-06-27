package edu.ncsu.billiards.world;

import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

import org.dyn4j.dynamics.Body;

import org.dyn4j.dynamics.contact.ContactAdapter;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;

public class GameWorld extends BilliardsWorld {
	private ArrayList<PoolBall> futureBalls;

	public GameWorld() {
		futureBalls = new ArrayList<PoolBall>();

		this.addListener(new GameContactHandler());
	}

	public void addFutureBall(PoolBall ball) {
		futureBalls.add(ball);
	}

	public ArrayList<PoolBall> getFutureBalls() {
		return futureBalls;
	}



	private class GameContactHandler extends ContactAdapter
								 implements ContactListener {
		public void sensed(ContactPoint point) {
			Body body1 = point.getBody1();
			Body body2 = point.getBody2();

			if (!body1.getFixture(0).isSensor()) {
				// body1 is a pool ball
				GameWorld.super.removeCurrentBall((PoolBall) body1);
			} else if (!body2.getFixture(0).isSensor()) {
				// body2 is a pool ball
				GameWorld.super.removeCurrentBall((PoolBall) body2);
			}
		}
	}
}
