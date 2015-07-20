package edu.ncsu.billiards.setups;

import edu.ncsu.billiards.gameobjects.Cushion;
import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;

import org.newdawn.slick.Color;

public class ParadoxSetup extends Setup {
	public void configureCushions() {
		Cushion topLeft     = new Cushion( .5f  ,  .235f, .785f, .1f  );
		Cushion topRight    = new Cushion(1.385f,  .235f, .785f, .1f  );
		Cushion left        = new Cushion( .335f,  .4f  , .1f  , .666f);
		Cushion right       = new Cushion(2.235f,  .4f  , .1f  , .666f);
		Cushion bottomLeft  = new Cushion( .5f  , 1.135f, .785f, .1f  );
		Cushion bottomRight = new Cushion(1.385f, 1.135f, .785f, .1f  );

		addCushion(topLeft);
		addCushion(topRight);
		addCushion(left);
		addCushion(right);
		addCushion(bottomLeft);
		addCushion(bottomRight);
	}

	public void configurePockets() {
		Pocket pocket1 = new Pocket(1, 1);
		Pocket pocket2 = new Pocket(1.3f, .7f);

		pocket1.setDestination(pocket2);
		pocket1.setTimeDifference(-0.2);

		pocket2.setDestination(pocket1);
		pocket2.setTimeDifference(-0.2);

		addPocket(pocket1);
		addPocket(pocket2);
	}

	public void configureBalls() {
		PoolBall ball1 = new PoolBall( .9f, .6f, Color.red);
		PoolBall ball2 = new PoolBall(1.5f,  1f, Color.blue);
		PoolBall ball3 = new PoolBall(1.3f, .8f, Color.green);

		addBall(ball1);
		addBall(ball2);
		addBall(ball3);
	}
}
