package edu.ncsu.billiards.world;

import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

import org.dyn4j.dynamics.Body;

import org.dyn4j.dynamics.contact.ContactAdapter;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;

import org.newdawn.slick.Color;

public class PredictionWorld extends BilliardsWorld {
	public PredictionWorld(ContactListener predictionContactHandler) {
		this.addListener(predictionContactHandler);
	}

	public void runSimulation() {
		// update until all bodies in this prediction world are asleep
		while (super.hasMovingBalls()) {
			this.update(1.0 / 60.0);
		}
	}

	/**
	 * Syncs the prediction world with the specified gameWorld.
	 *
	 * This method only syncs PoolBalls because cushions, pockets,
	 * and world settings should already by the same.
	 * 
	 * @param gameWorld the world to sync this prediction world to
	 */
	public void sync(GameWorld gameWorld) {
		// clear the current balls
		super.clearCurrentBalls();

		// copy balls from gameWorld to this world
		ArrayList<PoolBall> balls = gameWorld.getCurrentBalls();

		for (PoolBall ball : balls) {
			PoolBall newBall = new PoolBall((float) ball.getWorldCenter().x,
			                                (float) ball.getWorldCenter().y,
			                                        ball.getColor());

			newBall.setAngularVelocity(ball.getAngularVelocity());
			newBall.setLinearVelocity(ball.getLinearVelocity());

			newBall.applyForce(ball.getAccumulatedForce());
			newBall.applyTorque(ball.getAccumulatedTorque());

			super.addCurrentBall(newBall);
		}
	}

}
