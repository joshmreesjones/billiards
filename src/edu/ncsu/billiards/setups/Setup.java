package edu.ncsu.billiards.setups;

import edu.ncsu.billiards.gameobjects.Cushion;
import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

public abstract class Setup {
	public abstract ArrayList<Cushion> generateCushions();

	public abstract ArrayList<Pocket> generatePockets();

	public abstract ArrayList<PoolBall> generateBalls();
}
