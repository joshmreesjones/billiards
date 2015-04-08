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
import org.newdawn.slick.geom.Vector2f;

public class Billiards extends BasicGame {
	// title of game
	private static final String GAME_TITLE = "Billiards";

	// width and height of game window
	private static final int WINDOW_WIDTH  = 800;
	private static final int WINDOW_HEIGHT = 440;

	// used to determine what to render on the screen
	private static final int SETUP_STATE = 0;
	private static final int  GAME_STATE = 1;
	// our current game state
	private int currentState;

	// pool table background
	private Image tableBackground;

	// in-game time (used for ball timing)
	private float time;

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

	// whether the mouse is being dragged or not
	boolean dragging;

	public Billiards() {
		super(GAME_TITLE);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		tableBackground = new Image("res/pool-wide.png");
		time = 0;

		currentBalls = new ArrayList<PoolBall>();
		futureBalls = new ArrayList<PoolBall>();
		pockets = new ArrayList<Pocket>();
		
		pocketVelocityLines = new ArrayList<VelocityLine>();

		// start in setup
		currentState = GAME_STATE;

		// when we start we are not dragging
		dragging = false;

		currentBalls.add(new PoolBall(300, 200, 11));
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		/*
		Input input = container.getInput();

		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();

			if (!isMouseDragging) {
				dragStart = new Vector2f((float) mouseX, (float) mouseY);
				dragEnd   = new Vector2f((float) mouseX, (float) mouseY);

				isMouseDragging = true;
			} else {
				dragEnd.set((float) mouseX, (float) mouseY);
			}
		} else {
			if (isMouseDragging) {
				isMouseDragging = false;
			}
		}

		// update position of poolBall based on its velocity
		Vector2f velocity = poolBall.getVelocity();

		// calculate changes in x and y directions
		float deltaX = velocity.getX() * delta;
		float deltaY = velocity.getY() * delta;
		
		// set new position
		poolBall.setX(poolBall.getX() + deltaX);
		poolBall.setY(poolBall.getY() + deltaY);

		// decrease velocity
		if (velocity.length() < .003f) {
			velocity.set(0, 0);
		} else {
			velocity.scale(.99f);
		}

		poolBall.setVelocity(velocity);

		time++;
		*/
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// background
		tableBackground.draw(0, 0);

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



		/*
		// draw pool table
		tableBackground.draw(0, 0);

		g.setAntiAlias(true);
		g.setColor(Color.white);
		g.draw(poolBall);
		g.fill(poolBall);

		if (isMouseDragging) {
			float startX = dragStart.getX();
			float startY = dragStart.getY();
			float endX = dragEnd.getX();
			float endY = dragEnd.getY();

			g.drawLine(startX, startY, endX, endY);
		}
		*/
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		dragging = true;
		// check for (oldx, oldy) to be on a ball or a pocket
		// if it is on a ball, add to ballVelocityLines
		// if it is on a pocket
			// if (newx, newy) is on another pocket, add to pocketLinkLines
			// if (newx, newy) is not on another pocket, add to pocketVelocityLines
		System.out.println(oldx + " " + oldy + " " + newx + " " + newy);

		ballVelocityLine = new VelocityLine(
						(float) oldx,
						(float) oldy,
						(float) newx,
						(float) newy);
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if (dragging) {
			System.out.println("Released from drag.");
		} else {
			System.out.println("Released from click.");
		}

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
