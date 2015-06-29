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

	public void removeFutureBall(PoolBall ball) {
		futureBalls.remove(ball);
	}

	public ArrayList<PoolBall> getFutureBalls() {
		return futureBalls;
	}



	@Override
	public boolean update(double elapsedTime) {
		for (int i = 0; i < futureBalls.size(); i++) {
			if (futureBalls.get(i).getEntryTime() <= super.getTime()) {
				super.addCurrentBall(futureBalls.remove(i));

				i--;
			}
		}

		return super.update(elapsedTime);
	}
}
