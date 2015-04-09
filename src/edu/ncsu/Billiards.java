package edu.ncsu;

import edu.ncsu.Pocket;
import edu.ncsu.PoolBall;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Billiards extends BasicGame {
	/*
		GAME CONSTANTS
	*/

	// title of game
	private static final String GAME_TITLE = "Billiards";

	// width and height of game window
	private static final int WINDOW_WIDTH  = 800;
	private static final int WINDOW_HEIGHT = 440;

	// used to determine what to render on the screen
	private static final int SETUP_STATE = 0;
	private static final int  GAME_STATE = 1;

	// standard radius of pool ball
	private static final float POOL_BALL_RADIUS = 11;



	/*
		RENDERED OBJECTS
	*/

	// pool table background
	private Image tableBackground;

	// balls currently on the table
	private ArrayList<PoolBall> currentBalls;
	
	// balls that will appear on the table in the future
	private ArrayList<PoolBall> futureBalls;

	// pockets on the table
	private ArrayList<Pocket> pockets;

	// a velocity line attached to a ball (only one at a time)
	private VelocityLine ballVelocityLine;

	// velocity lines attached to pockets
	private ArrayList<VelocityLine> pocketVelocityLines;



	/*
		COLLISION UTILITIES
	*/

	// bounding cushions of the pool table
	private Rectangle topLeftCushion;
	private Rectangle topRightCushion;
	private Rectangle rightCushion;
	private Rectangle bottomLeftCushion;
	private Rectangle bottomRightCushion;
	private Rectangle leftCushion;

	private Rectangle[] cushions;

	// circle used in intersections to test for ball collisions
	private Circle collisionCircle;



	/*
		MOUSE INPUT TRACKING
	*/

	// whether the mouse is being dragged or not
	boolean dragging;

	// location of the start of a mouse drag
	private Circle mouseDragStart;

	// used in detecting mouse intersections
	private Circle mouseLocation;

	// the ball, if any, that was at the start of the
	// current mouse drag
	private PoolBall activeBall;



	// in-game time (used for ball timing)
	private float tick;

	// our current game state
	private int currentState;



	public Billiards() {
		super(GAME_TITLE);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		tableBackground = new Image("res/pool-wide.png");
		tick = 0;



		currentBalls = new ArrayList<PoolBall>();
		futureBalls = new ArrayList<PoolBall>();
		pockets = new ArrayList<Pocket>();
		
		pocketVelocityLines = new ArrayList<VelocityLine>();



		topLeftCushion     = new Rectangle(150, 71, 236, 29);
		topRightCushion    = new Rectangle(415, 71, 236, 29);
		rightCushion       = new Rectangle(671, 121, 29, 200);
		bottomLeftCushion  = new Rectangle(150, 341, 236, 29);
		bottomRightCushion = new Rectangle(415, 341, 236, 29);
		leftCushion        = new Rectangle(101, 121, 29, 200);

		cushions = new Rectangle[6];

		cushions[0] = topLeftCushion;
		cushions[1] = topRightCushion;
		cushions[2] = rightCushion;
		cushions[3] = bottomLeftCushion;
		cushions[4] = bottomRightCushion;
		cushions[5] = leftCushion;



		collisionCircle = new Circle(-100, -100, POOL_BALL_RADIUS);

		// start in setup
		currentState = GAME_STATE;

		// when we start we are not dragging
		dragging = false;

		currentBalls.add(new PoolBall(300, 200, POOL_BALL_RADIUS));
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		for (PoolBall ball : currentBalls) {
			// if ball.nextPosition intersects with any walls
				// call ball.handleCushionCollision(cushion, delta)
			// else if ball.nextPosition intersects with any balls.nextPosition
				// call ball.handleBallCollision(other ball, delta)
			// else
				// call ball.updatePosition(delta)

			float[] nextPosition = ball.nextPosition(delta);
			collisionCircle.setCenterX(nextPosition[0]);
			collisionCircle.setCenterY(nextPosition[1]);

			if (false) {

			} else if (false) {

			} else {
				ball.updatePosition(delta);
			}
		}

		tick++;
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// background
		tableBackground.draw(0, 0);

		g.setColor(Color.black);

		for (int i = 0; i < cushions.length; i++) {
			g.draw(cushions[i]);
		}

		// hit boxes
		// TODO draw hit boxes

		// pool balls
		for (PoolBall ball : currentBalls) {
			ball.draw(g);
		}

		// buttons
		// TODO draw buttons

		if (currentState == SETUP_STATE) {
			// draw pocket velocity lines
			// draw pocket link lines
			// draw pocket colors
			// draw time change selection
		} else if (currentState == GAME_STATE) {
			// ball velocity lines
			if (ballVelocityLine != null) {
				ballVelocityLine.draw(g);
			}
		}
	}

	public Rectangle intersectsCushion(Shape object) {
		for (int i = 0; i < cushions.length; i++) {
			if (object.intersects(cushions[i])) {
				return cushions[i];
			}
		}

		return null;
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		System.out.println(x + " " + y);

		// TODO handle buttons
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		// initialize mouse tracking
		mouseDragStart = new Circle((float) x, (float) y, 0.5f);
		mouseLocation  = new Circle((float) x, (float) y, 0.5f);
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		// update mouse location
		mouseLocation.setLocation((float) newx, (float) newy);

		// check for intersection with balls on the table
		for (PoolBall ball : currentBalls) {
			// if the mouse started on this ball
			if (ball.intersects(mouseDragStart)) {
				// we need activeBall when we release the drag
				// to set the velocity of that ball
				activeBall = ball;

				if (ballVelocityLine == null) {
					ballVelocityLine = new VelocityLine(
									mouseDragStart.getX(),
									mouseDragStart.getY(),
									mouseLocation.getX(),
									mouseLocation.getY());
				} else {
					ballVelocityLine.set(
									mouseDragStart.getCenter(),
									mouseLocation.getCenter());
				}
			}
		}

		// check for intersection with pockets
		// TODO

		// check for (startx, starty) to be on a ball or a pocket
		// if it is on a ball, add to ballVelocityLines
		// if it is on a pocket
			// if (newx, newy) is on another pocket, add to pocketLinkLines
			// if (newx, newy) is not on another pocket, add to pocketVelocityLines

		// update dragging flag
		dragging = true;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if (dragging) {
			System.out.println("Released from drag.");
		} else {
			System.out.println("Released from click.");
		}

		// if we released the mouse from a velocity line,
		// update that ball's velocity
		if (activeBall != null) {
			// this velocity is incorrectly calculated
			float vectorX = mouseLocation.getCenterX() - mouseDragStart.getCenterX();
			float vectorY = mouseLocation.getCenterY() - mouseDragStart.getCenterY();

			activeBall.setVelocity(vectorX, vectorY);
			activeBall = null;
		}

		// remove ball velocity line
		ballVelocityLine = null;

		// update mouse tracking shapes
		mouseDragStart = null;
		mouseLocation  = null;

		// update dragging flag
		dragging = false;
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
