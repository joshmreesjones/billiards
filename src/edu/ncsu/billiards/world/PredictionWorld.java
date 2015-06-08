package edu.ncsu.billiards.world;

import org.dyn4j.dynamics.contact.ContactAdapter;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;

public class PredictionWorld extends BilliardsWorld {
	public PredictionWorld() {
		this.addListener(new PredictionContactHandler());
	}

	public void runSimulation() {
		// update until all bodies in this prediction world are asleep
		while (hasMovingBalls()) {
			this.step();
		}
	}

	public void sync(GameWorld gameWorld) {
		// balls
		// pockets
		// cushions
	}

	private class PredictionContactHandler extends ContactAdapter
										implements ContactListener {
		public void sensed(ContactPoint point) {
			System.out.println("Contact sensed in simulation world");

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
