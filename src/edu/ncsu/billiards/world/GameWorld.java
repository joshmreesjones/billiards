package edu.ncsu.billiards.world;

import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

import org.dyn4j.dynamics.Body;

import org.dyn4j.dynamics.contact.ContactAdapter;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;

public class GameWorld extends BilliardsWorld {
	private ArrayList<PoolBall> futureBalls;

	public GameWorld(ContactListener gameContactHandler) {
		futureBalls = new ArrayList<PoolBall>();

		this.addListener(gameContactHandler);
	}

	public void addFutureBall(PoolBall ball) {
		futureBalls.add(ball);
	}

	public ArrayList<PoolBall> getFutureBalls() {
		return futureBalls;
	}
}
