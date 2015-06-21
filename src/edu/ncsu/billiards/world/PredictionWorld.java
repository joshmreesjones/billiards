package edu.ncsu.billiards.world;

import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

import org.dyn4j.dynamics.Body;

import org.dyn4j.dynamics.contact.ContactAdapter;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;

public class PredictionWorld extends BilliardsWorld {
	public PredictionWorld() {
		this.addListener(new PredictionContactHandler());
	}

	public void runSimulation() {
		// update until all bodies in this prediction world are asleep
		int i = 0;
		while (super.hasMovingBalls()) {
			/*
			System.out.println(this.getAccumulatedTime() + "\t" +
								this.getStep().getDeltaTime()
			);
			*/
			Body ball = this.getBodies().get(0);
			//System.out.println("\n");
			//System.out.println(ball.getWorldCenter());
			//System.out.println(ball.getAngularVelocity());
			//System.out.println(ball.getLinearVelocity());
			//System.out.println(ball.getAccumulatedForce());
			//System.out.println(ball.getAccumulatedTorque());
			//System.out.println("\n");

			this.update(1.0 / 60.0);

			i++;
		}

		System.out.println(i);
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
		super.getCurrentBalls().clear();

		// copy balls from gameWorld to this world
		ArrayList<PoolBall> balls = gameWorld.getCurrentBalls();

		for (PoolBall ball : balls) {
			PoolBall newBall = new PoolBall((float) ball.getWorldCenter().x,
			                                (float) ball.getWorldCenter().y,
			                                        ball.getColor());

			System.out.println(ball.getWorldCenter());
			System.out.println(ball.getAngularVelocity());
			System.out.println(ball.getLinearVelocity());
			System.out.println(ball.getAccumulatedForce());
			System.out.println(ball.getAccumulatedTorque());

			newBall.setAngularVelocity(ball.getAngularVelocity());
			newBall.setLinearVelocity(ball.getLinearVelocity());

			newBall.applyForce(ball.getAccumulatedForce());
			newBall.applyTorque(ball.getAccumulatedTorque());

			super.addCurrentBall(newBall);
		}
	}

	private class PredictionContactHandler extends ContactAdapter
										implements ContactListener {
		public void sensed(ContactPoint point) {
			System.out.println("Contact sensed in simulation world.");

			// record the time
			// find out source pocket
			// find out destination pocket
			// set the position and velocity of the ball to what the
			//							destination pocket specifies
			// add ball to gameWorld's futureBalls
			// remove from predictionWorld's currentBalls
		}
	}

/*
	private class PredictionContactHandler extends ContactAdapter
										implements ContactListener {
		public void sensed(ContactPoint point) {
			// TODO
			// get the time of contact
			// add the ball to gameWorld.futureBalls with contact time
			// remove the ball from predictionWorld.currentBalls
			Body body1 = point.getBody1();
			Body body2 = point.getBody2();

			if (!body1.getFixture(0).isSensor()) {
				// body1 is a pool ball
			} else if (!body2.getFixture(0).isSensor()) {
				// body2 is a pool ball
			}
		}
	}
*/
}
