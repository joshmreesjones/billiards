package edu.ncsu.billiards.world;

import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.DetectResult;

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

	public PoolBall removeFutureBall(int index) {
		return futureBalls.remove(index);
	}

	public ArrayList<PoolBall> getFutureBalls() {
		return futureBalls;
	}



	@Override
	public boolean update(double elapsedTime) {
		// Update isExiting flag in all current balls
		for (PoolBall ball : getCurrentBalls()) {
			if (ball.isExiting()) {
				boolean inContact = false;

				for (Pocket pocket : getPockets()) {
					if (ball.isInContact(pocket)) {
						inContact = true;
					}
				}

				if (!inContact) {
					ball.setExiting(false);
				}
			}
		}

		// check for future balls entering the world
		// we can't use an enhanced for loop because that messes
		// up the iterator and throws a ConcurrentModificationException
		for (int i = 0; i < futureBalls.size(); i++) {
			if (futureBalls.get(i).getEntryTime() <= getTime()) {
				PoolBall ball = removeFutureBall(i);
				ball.setExiting(true);
				addCurrentBall(ball);

				i--;
			}
		}

		return super.update(elapsedTime);
	}
}
