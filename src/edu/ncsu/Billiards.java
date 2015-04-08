package edu.ncsu;

import edu.ncsu.PoolBall;

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
	private static final int WINDOW_WIDTH  = 800;
	private static final int WINDOW_HEIGHT = 440;

	private Image tableBackground;
	private PoolBall poolBall;

	private float time;

	private Vector2f dragStart;
	private Vector2f dragEnd;
	private boolean isMouseDragging;

	public Billiards() {
		super("Billiards");
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

	@Override
	public void init(GameContainer container) throws SlickException {
		tableBackground = new Image("res/pool-wide.png");
		poolBall = new PoolBall(100, 100, 11);

		time = 0;

		isMouseDragging = false;
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		Input input = container.getInput();

		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			/*
			poolBall.setX(input.getMouseX());
			poolBall.setY(input.getMouseY());
			*/

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
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
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
	}
}
