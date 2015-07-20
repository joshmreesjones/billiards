package edu.ncsu.billiards.setups;

import edu.ncsu.billiards.gameobjects.Cushion;
import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

public abstract class Setup {
	private ArrayList<Cushion> cushions;
	private ArrayList<Pocket> pockets;
	private ArrayList<PoolBall> balls;

	public Setup() {
		cushions = new ArrayList<Cushion>();
		pockets = new ArrayList<Pocket>();
		balls = new ArrayList<PoolBall>();

		configureCushions();
		configurePockets();
		configureBalls();
	}



	public abstract void configureCushions();

	public abstract void configurePockets();

	public abstract void configureBalls();



	public void addCushion(Cushion cushion) {
		cushions.add(cushion);
	}

	public void addPocket(Pocket pocket) {
		pockets.add(pocket);
	}

	public void addBall(PoolBall ball) {
		balls.add(ball);
	}



	public ArrayList<Cushion> getCushions() {
		return cushions;
	}

	public ArrayList<Pocket> getPockets() {
		return pockets;
	}

	public ArrayList<PoolBall> getBalls() {
		return balls;
	}
}
