package edu.ncsu;

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
	/*
		GAME CONSTANTS
	*/

	// title of game
	private static final String GAME_TITLE = "Billiards";

	// width and height of game window
	private static final int WINDOW_WIDTH  = 800;
	private static final int WINDOW_HEIGHT = 440;

	private World world;
	private PoolBall ball;



	/*
		RENDERED OBJECTS
	*/

	// pool table background
	private Image tableBackground;



	public Billiards() {
		super(GAME_TITLE);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		tableBackground = new Image("res/pool-wide.png");

		world = new World();
		ball = new PoolBall(0, 0, 10);

		ball.setLinearVelocity(new Vector2(10, 10));

		world.addBody(ball);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		tableBackground = new Image("res/pool-wide.png");

		world.updatev((double) delta);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.drawImage(tableBackground, 0, 0);

		for (int i = 0; i < world.getBodyCount(); i++) {
			Body body = world.getBody(i);

			if (body instanceof PoolBall) {
				Renderer.render((PoolBall) body, g);
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
