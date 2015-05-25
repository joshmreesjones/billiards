package edu.ncsu;

import java.util.ArrayList;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;

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

	// physics engine world
	private World world;

	// physical game objects
	private ArrayList<PoolBall> currentBalls;
	private ArrayList<PoolBall> futureBalls;
	private ArrayList<Pocket> pockets;
	private ArrayList<Cushion> cushions;

	// rendered objects
	private Image tableBackground;
	private VelocityLine ballVelocityLine;
	private ArrayList<VelocityLine> pocketVelocityLines;

	// input handling
	private InputHandler inputHandler;





	public Billiards() {
		super(GAME_TITLE);

		world = new World();

		currentBalls = new ArrayList<PoolBall>();
		futureBalls = new ArrayList<PoolBall>();
		pockets = new ArrayList<Pocket>();
		cushions = new ArrayList<Cushion>();

		ballVelocityLine = new VelocityLine();
		pocketVelocityLines = new ArrayList<VelocityLine>();

		inputHandler = new InputHandler();
	}





	@Override
	public void init(GameContainer container) throws SlickException {
		tableBackground = new Image("res/pool-wide.png");

		// create and configure world
		world.setGravity(new Vector2(0, 0));
		world.getSettings().setSleepAngularVelocity(Double.MAX_VALUE);

		// create game objects
		currentBalls.add(new PoolBall(1, 1, Color.red));

		// add game objects to world
		for (PoolBall ball : currentBalls) {
			world.add(ball);
		}

		// create rendered objects

		// create input handler
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		world.update((double) delta / 1000);
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
		for (VelocityLine line : pocketVelocityLines) {
			Renderer.render(line, g);
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

		public void mouseClicked(int button, double x, double y, int clickCount) {
		}

		public void mouseDragged(double oldx, double oldy,
								 double newx, double newy) {
			if (draggingFromBall) {
				Billiards.this.ballVelocityLine.setEnd(newx, newy);
			}
		}

		public void mouseMoved(double oldx, double oldy, double newx, double newy) {
		}

		public void mousePressed(int button, double x, double y) {
			// check if mouse press is on current, asleep ball
			Vector2 point = new Vector2(x, y);

			for (PoolBall ball : Billiards.this.currentBalls) {
				if (ball.contains(point) && ball.isAsleep()) {
					double startX = ball.getWorldCenter().x;
					double startY = ball.getWorldCenter().y;

					Billiards.this.ballVelocityLine.setStart(startX, startY);
					Billiards.this.ballVelocityLine.setEnd(startX, startY);

					draggingFromBall = true;
				}
			}
		}

		public void mouseReleased(int button, double x, double y) {
			Billiards.this.ballVelocityLine.setStart(0, 0);
			Billiards.this.ballVelocityLine.setEnd(0, 0);

			draggingFromBall = false;

			// send the ball on its way (if it was on a ball)
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
