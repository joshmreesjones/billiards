package edu.ncsu.billiards.world;

import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

import org.dyn4j.dynamics.Body;

import org.dyn4j.dynamics.contact.ContactAdapter;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;

import org.newdawn.slick.Color;

public class PredictionWorld extends BilliardsWorld {
	public PredictionWorld() {
		this.addListener(new PredictionContactHandler());
	}

	public void runSimulation() {
		// TODO the latest: there are multiple copies of each
		// ball in this world instance. The sync() method is
		// broken and needs to be refactored. See issue #2, which
		// can be used as part of a solution to the problem (maybe
		// all of it)
		System.out.println("Running simulation... ");

		System.out.println("Default body sleep time: " +
					super.getSettings().getSleepTime());

		System.out.println("Auto-sleeping enabled by default: " +
					super.getSettings().isAutoSleepingEnabled());

		System.out.println("Sleep angular velocity tolerance: " +
					super.getSettings().getSleepAngularVelocity());

		System.out.println("Sleep linear velocity tolerance: " +
					super.getSettings().getSleepLinearVelocity());

		System.out.println("Number of current balls: " +
					super.getCurrentBalls().size());

		System.out.println("Number of bodies: " +
					super.getBodyCount());

		int iteration = 0;
		// update until all bodies in this prediction world are asleep
		boolean print = false;
		while (super.hasMovingBalls()) {
			// print some information
			System.out.print(print ? "====================================\n" : "");
			for (Body body : this.getBodies()) {
				if ((body instanceof PoolBall) && print) {
					PoolBall ball = (PoolBall) body;

					System.out.println();
					System.out.println();

					System.out.println("Iteration: " + iteration++);

					System.out.println("Ball ID: " + ball.getId());

					System.out.println("Position: " +
								ball.getWorldCenter());

					System.out.println("Linear velocity: " +
								ball.getLinearVelocity());

					System.out.println("Angular velocity: " +
								ball.getAngularVelocity());

					System.out.println("Accumulated force: " +
								ball.getAccumulatedForce());
					
					System.out.println("Accumulated torque: " +
								ball.getAccumulatedTorque());
					
					Color color = ball.getColor();
					String colorString = "";
					if (color.equals(Color.blue)) {
						colorString = "blue";
					} else if (color.equals(Color.red)) {
						colorString = "red";
					}
					System.out.println("Color: " + colorString);

					System.out.println("Auto-sleeping enabled: " +
								ball.isAutoSleepingEnabled());

					System.out.println("Asleep: " +
								ball.isAsleep());

					System.out.println("Active: " +
								ball.isActive());

					System.out.println("World accumulated time: " +
								super.getAccumulatedTime());

					System.out.println("World update required: " +
								super.isUpdateRequired());

					System.out.println();
					System.out.println();
				}
			}

			this.update(1.0 / 60.0);
		}

		System.out.println("Simulation complete.");
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
