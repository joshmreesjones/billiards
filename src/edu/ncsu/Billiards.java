package edu.ncsu;

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

	// physics engine game world
	private World gameWorld;
	// physics engine prediction world
	private World predictionWorld;

	// physical game objects
	private ArrayList<PoolBall> currentBalls;
	private ArrayList<PoolBall> futureBalls;
	private ArrayList<Pocket> pockets;
	private ArrayList<Cushion> cushions;

	// rendered objects
	private Image tableBackground;
	private VelocityLine ballVelocityLine;
	//private ArrayList<VelocityLine> pocketVelocityLines;

	// input handling
	private InputHandler inputHandler;





	public Billiards() {
		super(GAME_TITLE);

		gameWorld = new World();

		currentBalls = new ArrayList<PoolBall>();
		futureBalls = new ArrayList<PoolBall>();
		pockets = new ArrayList<Pocket>();
		cushions = new ArrayList<Cushion>();

		ballVelocityLine = new VelocityLine();
		//pocketVelocityLines = new ArrayList<VelocityLine>();

		inputHandler = new InputHandler();
	}





	@Override
	public void init(GameContainer container) throws SlickException {
		tableBackground = new Image("res/pool-wide.png");

		// create and configure game world
		gameWorld.setGravity(new Vector2(0, 0));
		gameWorld.getSettings().setSleepAngularVelocity(Double.MAX_VALUE);
		gameWorld.getSettings().setRestitutionVelocity(0);

		gameWorld.addListener(new GameContactHandler());

		// create rendered objects
		Pocket testPocket = new Pocket(1, 1);
		pockets.add(testPocket);

		// create game objects
		PoolBall ball1 = new PoolBall(.9f, .6f, Color.red);
		PoolBall ball2 = new PoolBall(1.5f, 1f, Color.blue);
		//ball1.setLinearVelocity(.5f, 0);
		//ball2.setLinearVelocity(-.5f, 0);
		currentBalls.add(ball1);
		currentBalls.add(ball2);

		Cushion topLeft     = new Cushion( .5f  ,  .235f, .785f, .1f  );
		Cushion topRight    = new Cushion(1.385f,  .235f, .785f, .1f  );
		Cushion left        = new Cushion( .335f,  .4f  , .1f  , .666f);
		Cushion right       = new Cushion(2.235f,  .4f  , .1f  , .666f);
		Cushion bottomLeft  = new Cushion( .5f  , 1.135f, .785f, .1f  );
		Cushion bottomRight = new Cushion(1.385f, 1.135f, .785f, .1f  );

		cushions.add(topLeft);
		cushions.add(topRight);
		cushions.add(left);
		cushions.add(right);
		cushions.add(bottomLeft);
		cushions.add(bottomRight);

		// add game objects to game world
		for (PoolBall ball : currentBalls) {
			gameWorld.add(ball);
		}

		for (Cushion cushion : cushions) {
			gameWorld.add(cushion);
		}

		for (Pocket pocket : pockets) {
			gameWorld.add(pocket);
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		gameWorld.update((double) delta / 1000);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// background image
		g.drawImage(tableBackground, 0, 0);

		// pockets
		for (Pocket pocket : pockets) {
			Renderer.render(pocket, g);
		}

		// cushions
		for (Cushion cushion : cushions) {
			Renderer.render(cushion, g);
		}

		// current balls
		for (PoolBall ball : currentBalls) {
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
		Billiards outside = Billiards.this;

		boolean draggingFromBall = false;
		PoolBall currentDraggingBall = null;

		public void mouseDragged(double oldx, double oldy,
								 double newx, double newy) {
			if (draggingFromBall) {
				outside.ballVelocityLine.setEnd(newx, newy);
			}
		}

		public void mousePressed(int button, double x, double y) {
			Vector2 point = new Vector2(x, y);

			// all balls should be asleep before we can start another
			for (PoolBall ball : outside.currentBalls) {
				if (!ball.isAsleep()) {
					return;
				}
			}

			// check if mouse press is on current, asleep ball
			for (PoolBall ball : outside.currentBalls) {
				if (ball.contains(point)) {
					double startX = ball.getWorldCenter().x;
					double startY = ball.getWorldCenter().y;

					outside.ballVelocityLine.setStart(startX, startY);
					outside.ballVelocityLine.setEnd(startX, startY);

					draggingFromBall = true;
					currentDraggingBall = ball;
				}
			}
		}

		public void mouseReleased(int button, double x, double y) {
			// send the ball on its way (if it was on a ball)
			if (draggingFromBall) {
				double[] dragStart = outside.ballVelocityLine.getStart();
				double[] dragEnd = outside.ballVelocityLine.getEnd();

				//System.out.println();
				//System.out.println(dragEnd[0] - dragStart[0]);
				//System.out.println(dragEnd[1] - dragStart[1]);
				
				Vector2 force = new Vector2(
					(dragEnd[0] - dragStart[0]) * 200,
					(dragEnd[1] - dragStart[1]) * 200
				);

				currentDraggingBall.applyForce(force);
			}

			outside.ballVelocityLine.setStart(0, 0);
			outside.ballVelocityLine.setEnd(0, 0);

			draggingFromBall = false;
			currentDraggingBall = null;
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
				// body1 is the pool ball
				Billiards.this.currentBalls.remove(body1);
			} else if (!body2.getFixture(0).isSensor()) {
				// body2 is a pool ball
				Billiards.this.currentBalls.remove(body2);
			}
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
