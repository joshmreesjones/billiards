package edu.ncsu.billiards.world;

import edu.ncsu.billiards.gameobjects.Cushion;
import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

import org.dyn4j.dynamics.World;

import org.dyn4j.geometry.Vector2;

public abstract class BilliardsWorld extends World {
	private ArrayList<PoolBall> currentBalls;
	private ArrayList<Pocket> pockets;
	private ArrayList<Cushion> cushions; 


	public BilliardsWorld() {
		currentBalls = new ArrayList<PoolBall>();
		pockets = new ArrayList<Pocket>();
		cushions = new ArrayList<Cushion>();

		this.setGravity(World.ZERO_GRAVITY);
		this.getSettings().setSleepAngularVelocity(Double.MAX_VALUE);
		this.getSettings().setRestitutionVelocity(0);
	}



	public void addCurrentBall(PoolBall ball) {
		// add to currentBalls
		currentBalls.add(ball);
		// add to world
		super.addBody(ball);
	}

	public void addPocket(Pocket pocket) {
		// add to currentBalls
		pockets.add(pocket);
		// add to world
		super.addBody(pocket);
	}

	public void addCushion(Cushion cushion) {
		// add to currentBalls
		cushions.add(cushion);
		// add to world
		super.addBody(cushion);
	}



	public ArrayList<PoolBall> getCurrentBalls() {
		return currentBalls;
	}

	public ArrayList<Pocket> getPockets() {
		return pockets;
	}

	public ArrayList<Cushion> getCushions() {
		return cushions;
	}



	public void removeCurrentBall(PoolBall ball) {
		// remove from currentBalls
		System.out.println("Removed from list: " + getCurrentBalls().remove(ball));

		// remove from world
		System.out.println("Removed from world: " + super.removeBody(ball));
	}

	public void clearCurrentBalls() {
		for (PoolBall ball : getCurrentBalls()) {
			super.removeBody(ball);
		}

		getCurrentBalls().clear();
	}



	public boolean hasMovingBalls() {
		for (PoolBall ball : currentBalls) {
			// if ball is not asleep
			if (!ball.isAsleep()) {
				// ball is moving
				return true;
			}
		}

		return false;
	}
}
