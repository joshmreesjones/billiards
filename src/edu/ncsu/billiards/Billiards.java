package edu.ncsu.billiards;

import edu.ncsu.billiards.gameobjects.Cushion;
import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;
import edu.ncsu.billiards.gameobjects.VelocityLine;

import edu.ncsu.billiards.world.BilliardsWorld;
import edu.ncsu.billiards.world.GameWorld;
import edu.ncsu.billiards.world.PredictionWorld;

import java.util.ArrayList;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;

import org.dyn4j.dynamics.contact.ContactAdapter;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Billiards extends BasicGame {
	// window title
	private static final String GAME_TITLE = "Billiards";

	// dimensions in pixels
	private static final int WINDOW_WIDTH  = 800;
	private static final int WINDOW_HEIGHT = 440;

	// user-facing world
	private GameWorld gameWorld;
	// world used to predict future collisions
	private PredictionWorld predictionWorld;

	// rendered objects
	private Image tableBackground;
	//private ArrayList<VelocityLine> pocketVelocityLines;

	// input handling
	private InputHandler inputHandler;





	public Billiards() {
		super(GAME_TITLE);

		gameWorld = new GameWorld(new GameContactHandler());
		predictionWorld = new PredictionWorld(new PredictionContactHandler());

		//pocketVelocityLines = new ArrayList<VelocityLine>();

		inputHandler = new InputHandler();
	}





	@Override
	public void init(GameContainer container) throws SlickException {
		tableBackground = new Image("res/pool-wide.png");

		addGameObjects(gameWorld);
		addGameObjects(predictionWorld);
	}

	/**
	 * Adds game objects to the specified world.
	 * 
	 * This method is utilized because the gameWorld and predictionWorld
	 * should have the same setup as each other. Objects should be logically
	 * identical, but should not be the same objects in memory, as
	 * the prediction world will update the Bodies in the world, and we
	 * don't want those to change in the game world.
	 * 
	 * @param world the world to add objects to
	 */
	public void addGameObjects(BilliardsWorld world) {
		PoolBall ball1 = new PoolBall(.9f, .6f, Color.red);
		PoolBall ball2 = new PoolBall(1.5f, 1f, Color.blue);
		PoolBall ball3 = new PoolBall(1.3f, .8f, Color.green);
		world.addCurrentBall(ball1);
		world.addCurrentBall(ball2);
		world.addCurrentBall(ball3);

		Cushion topLeft     = new Cushion( .5f  ,  .235f, .785f, .1f  );
		Cushion topRight    = new Cushion(1.385f,  .235f, .785f, .1f  );
		Cushion left        = new Cushion( .335f,  .4f  , .1f  , .666f);
		Cushion right       = new Cushion(2.235f,  .4f  , .1f  , .666f);
		Cushion bottomLeft  = new Cushion( .5f  , 1.135f, .785f, .1f  );
		Cushion bottomRight = new Cushion(1.385f, 1.135f, .785f, .1f  );

		world.addCushion(topLeft);
		world.addCushion(topRight);
		world.addCushion(left);
		world.addCushion(right);
		world.addCushion(bottomLeft);
		world.addCushion(bottomRight);

		Pocket testPocket = new Pocket(1, 1);
		world.addPocket(testPocket);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// TODO
		// check if we need to add in a futureBall
		// if we do, remove it from gameWorld.futureBalls
		//            and add it to gameWorld.currentBalls
		gameWorld.update((double) delta / 1000);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// background image
		g.drawImage(tableBackground, 0, 0);

		// pockets
		for (Pocket pocket : gameWorld.getPockets()) {
			Renderer.render(pocket, g);
		}

		// cushions
		for (Cushion cushion : gameWorld.getCushions()) {
			Renderer.render(cushion, g);
		}

		// current balls
		for (PoolBall ball : gameWorld.getCurrentBalls()) {
			Renderer.render(ball, g);
		}
	}





	public void mouseClicked(int button, int x, int y, int clickCount) {
		inputHandler.mouseClicked(button,
					(double) x / Renderer.SCALE,
					(double) y / Renderer.SCALE,
					clickCount);
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		inputHandler.mouseDragged(
					(double) oldx / Renderer.SCALE,
					(double) oldy / Renderer.SCALE,
					(double) newx / Renderer.SCALE,
					(double) newy / Renderer.SCALE);
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		inputHandler.mouseMoved(
					(double) oldx / Renderer.SCALE,
					(double) oldy / Renderer.SCALE,
					(double) newx / Renderer.SCALE,
					(double) newy / Renderer.SCALE);
	}

	public void mousePressed(int button, int x, int y) {
		inputHandler.mousePressed(button,
					(double) x / Renderer.SCALE,
					(double) y / Renderer.SCALE);
	}

	public void mouseReleased(int button, int x, int y) {
		inputHandler.mouseReleased(button,
					(double) x / Renderer.SCALE,
					(double) y / Renderer.SCALE);
	}





	private class InputHandler {
		boolean draggingFromBall = false;
		PoolBall selectedBall;

		boolean draggingFromPocket = false;
		Pocket selectedPocket;

		public void mouseDragged(double oldX, double oldY,
								 double newX, double newY) {
			if (draggingFromBall) {
				selectedBall.getVelocityLine().setEnd(newX, newY);
			}

			if (draggingFromPocket) {
				// make vector from velocity line start to (newX, newY)
				VelocityLine line = selectedPocket.getVelocityLine();
				double[] start = line.getStart();
				double startX = start[0];
				double startY = start[1];

				Vector2 mouseVector = new Vector2(newX - startX,
				                                  newY - startY);

				// get unit vector of mouseVector
				unitVector = mouseVector.getNormalized();

				// scale the unit vector
				unitVector.setMagnitude(20);

				// set the velocity line's distance to scaled unit vector
				line.setEnd(startX + unitVector.x, startY + unitVector.y);
			}
		}

		public void mousePressed(int button, double x, double y) {
			Vector2 point = new Vector2(x, y);

			// all balls should be asleep before we can start another
			if (Billiards.this.gameWorld.hasMovingBalls()) {
				return;
			}

			// check if mouse press is on current ball
			for (PoolBall ball : Billiards.this.gameWorld.getCurrentBalls()) {
				if (ball.contains(point)) {
					draggingFromBall = true;
					selectedBall = ball;

					double startX = ball.getWorldCenter().x;
					double startY = ball.getWorldCenter().y;

					selectedBall.getVelocityLine().setStart(startX, startY);
					selectedBall.getVelocityLine().setEnd(startX, startY);
				}
			}

			// check if mouse press is on pocket
			for (Pocket pocket : Billiards.this.gameWorld.getPockets()) {
				if (pocket.contains(point)) {
					draggingFromPocket = true;
					selectedPocket = pocket;

					double startX = pocket.getWorldCenter().x;
					double startY = pocket.getWorldCenter().y;

					selectedPocket.getVelocityLine().setStart(startX, startY);
					selectedPocket.getVelocityLine().setEnd(startX, startY);
				}
			}
		}

		public void mouseReleased(int button, double x, double y) {
			// send the ball on its way (if it was on a ball)
			if (draggingFromBall) {
				double[] dragStart = selectedBall.getVelocityLine().getStart();
				double[] dragEnd = selectedBall.getVelocityLine().getEnd();

				Vector2 force = new Vector2(
					(dragEnd[0] - dragStart[0]) * 200,
					(dragEnd[1] - dragStart[1]) * 200
				);

				selectedBall.applyForce(force);

				predictionWorld.sync(Billiards.this.gameWorld);
				predictionWorld.runSimulation();

				selectedBall.getVelocityLine().setStart(0, 0);
				selectedBall.getVelocityLine().setEnd(0, 0);

				draggingFromBall = false;
				selectedBall = null;
			}
		}

		public void mouseClicked(int button, double x, double y, int clickCount) {

		}

		public void mouseMoved(double oldx, double oldy, double newx, double newy) {

		}
	}





	private class GameContactHandler extends ContactAdapter
								 implements ContactListener {
		public void sensed(ContactPoint point) {
			Body body1 = point.getBody1();
			Body body2 = point.getBody2();

			if (!body1.getFixture(0).isSensor()) {
				// body1 is a pool ball
				gameWorld.removeCurrentBall((PoolBall) body1);
			} else if (!body2.getFixture(0).isSensor()) {
				// body2 is a pool ball
				gameWorld.removeCurrentBall((PoolBall) body2);
			}
		}
	}





	private class PredictionContactHandler extends ContactAdapter
										implements ContactListener {
		public void sensed(ContactPoint point) {
			System.out.println("Contact sensed in simulation world.");

			// TODO The latest:
			// Implement the time travel
			// behavior in Billiards. Start by re-adding the time
			// variable, then move on to the comments below.

			// record the time
			// find out source pocket
			// find out destination pocket
			// set the position and velocity of the ball to what the
			//							destination pocket specifies
			// add ball to gameWorld's futureBalls
			// remove from predictionWorld's currentBalls
		}
	}





	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Billiards());
			app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
			app.start();
		} catch (SlickException ex) {
			ex.printStackTrace();
		}
	}
}
