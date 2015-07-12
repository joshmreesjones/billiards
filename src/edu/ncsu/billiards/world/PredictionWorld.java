package edu.ncsu.billiards.world;

import edu.ncsu.billiards.gameobjects.Pocket;
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

	public void runSimulation(GameWorld gameWorld) {
		// Move all pockets from gameWorld to this prediction world. We cannot
		// just copy the ball over because a Body can be a member of only one
		// World at a time. Dyn4j enforces this by throwing an exception if we
		// try to violate the rule.
		clearPockets();
		ArrayList<Pocket> gameWorldPockets = gameWorld.getPockets();
		while (!gameWorldPockets.isEmpty()) {
			Pocket pocket = gameWorldPockets.remove(0);
			gameWorld.removePocket(pocket);
			addPocket(pocket);
		}

		sync(gameWorld);

		// update until all bodies in this prediction world are asleep
		while (hasMovingBalls()) {
			this.update(1.0 / 60.0);
		}

		// Move all pockets back from this prediction world to the
		// game world.
		gameWorld.clearPockets();
		ArrayList<Pocket> predictionWorldPockets = getPockets();
		while (!predictionWorldPockets.isEmpty()) {
			Pocket pocket = predictionWorldPockets.get(0);
			removePocket(pocket);
			gameWorld.addPocket(pocket);
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
	private void sync(GameWorld gameWorld) {
		clearCurrentBalls();

		// copy balls from gameWorld to this world
		for (PoolBall ball : gameWorld.getCurrentBalls()) {
			PoolBall newBall = new PoolBall((float) ball.getWorldCenter().x,
			                                (float) ball.getWorldCenter().y,
			                                        ball.getColor());

			newBall.setAngularVelocity(ball.getAngularVelocity());
			newBall.setLinearVelocity(ball.getLinearVelocity());

			newBall.applyForce(ball.getAccumulatedForce());
			newBall.applyTorque(ball.getAccumulatedTorque());

			addCurrentBall(newBall);
		}

		setTime(gameWorld.getTime());
	}
}
