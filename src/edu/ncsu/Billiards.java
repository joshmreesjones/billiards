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

		// add game objects to world

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
		Renderer.render(ballVelocityLine, g);

		// pocket velocity lines
		for (VelocityLine line : pocketVelocityLines) {
			Renderer.render(line, g);
		}
	}





	public void mouseClicked(int button, int x, int y, int clickCount) {
		inputHandler.mouseClicked(button, x, y, clickCount);
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		inputHandler.mouseDragged(oldx, oldy, newx, newy);
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		inputHandler.mouseMoved(oldx, oldy, newx, newy);
	}

	public void mousePressed(int button, int x, int y) {
		inputHandler.mousePressed(button, x, y);
	}

	public void mouseReleased(int button, int x, int y) {
		inputHandler.mouseReleased(button, x, y);
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
