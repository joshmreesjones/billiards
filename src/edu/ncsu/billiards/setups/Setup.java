package edu.ncsu.billiards.setups;

import edu.ncsu.billiards.gameobjects.Cushion;
import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

/**
 * A class used to generate various game objects in certain configurations.
 *
 * Assuming window dimensions:
 *     - Width:  872 pixels
 *     - Height: 472 pixels
 *
 * and a pixels/meter scale of:
 *     - 328 pixels/meter
 *
 * We obtain these values:
 *     - distance between window edge and pool table: .111 meters
 *     - width of cushions: .106 meters
 *     - total window width: 2.659 meters
 *     - total window height: 1.439 meters
 *     - coordinates of normal cue ball start: (.738, .72)
 */
public abstract class Setup {
	public final ArrayList<Cushion> generateCushions() {
		ArrayList<Cushion> cushions = new ArrayList<Cushion>();

		Cushion top    = new Cushion( .217f,  .111f, 2.225f,  .106f);
		Cushion bottom = new Cushion( .217f, 1.222f, 2.225f,  .106f);
		Cushion left   = new Cushion( .111f,  .217f,  .106f, 1.005f);
		Cushion right  = new Cushion(2.442f,  .217f,  .106f, 1.005f);

		cushions.add(top);
		cushions.add(bottom);
		cushions.add(left);
		cushions.add(right);

		return cushions;
	}

	public abstract ArrayList<Pocket> generatePockets();

	public abstract ArrayList<PoolBall> generateBalls();
}
