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
	private VelocityLine ballVelocityLine;
	//private ArrayList<VelocityLine> pocketVelocityLines;

	// input handling
	private InputHandler inputHandler;





	public Billiards() {
		super(GAME_TITLE);

		gameWorld = new GameWorld();
		predictionWorld = new PredictionWorld();

		ballVelocityLine = new VelocityLine();
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
		world.addCurrentBall(ball1);
		world.addCurrentBall(ball2);

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
		// reset predictionWorld to current gameWorld state
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

		// ball velocity line
		if (ballVelocityLine.getStart()[0] == 0 &&
			ballVelocityLine.getStart()[1] == 0 &&
			ballVelocityLine.getEnd()[0]   == 0 &&
			ballVelocityLine.getEnd()[1]   == 0) {
			// don't render the line if it's (0, 0) to (0, 0)
		} else {
			Renderer.render(ballVelocityLine, g);
		}

		// pocket velocity lines
		//for (VelocityLine line : pocketVelocityLines) {
		//	Renderer.render(line, g);
		//}
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
		PoolBall currentDraggingBall = null;

		public void mouseDragged(double oldx, double oldy,
								 double newx, double newy) {
			if (draggingFromBall) {
				Billiards.this.ballVelocityLine.setEnd(newx, newy);
			}
		}

		public void mousePressed(int button, double x, double y) {
			Vector2 point = new Vector2(x, y);

			// all balls should be asleep before we can start another
			if (Billiards.this.gameWorld.hasMovingBalls()) {
				return;
			}

			// check if mouse press is on current, asleep ball
			for (PoolBall ball : Billiards.this.gameWorld.getCurrentBalls()) {
				if (ball.contains(point)) {
					double startX = ball.getWorldCenter().x;
					double startY = ball.getWorldCenter().y;

					Billiards.this.ballVelocityLine.setStart(startX, startY);
					Billiards.this.ballVelocityLine.setEnd(startX, startY);

					draggingFromBall = true;
					currentDraggingBall = ball;
				}
			}
		}

		public void mouseReleased(int button, double x, double y) {
			// send the ball on its way (if it was on a ball)
			if (draggingFromBall) {
				double[] dragStart = Billiards.this.ballVelocityLine.getStart();
				double[] dragEnd = Billiards.this.ballVelocityLine.getEnd();

				Vector2 force = new Vector2(
					(dragEnd[0] - dragStart[0]) * 200,
					(dragEnd[1] - dragStart[1]) * 200
				);

				currentDraggingBall.applyForce(force);

				predictionWorld.sync(Billiards.this.gameWorld);
				predictionWorld.runSimulation();
			}

			Billiards.this.ballVelocityLine.setStart(0, 0);
			Billiards.this.ballVelocityLine.setEnd(0, 0);

			draggingFromBall = false;
			currentDraggingBall = null;
		}


		public void mouseClicked(int button, double x, double y, int clickCount) {

		}

		public void mouseMoved(double oldx, double oldy, double newx, double newy) {

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
