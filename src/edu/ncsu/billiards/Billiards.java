package edu.ncsu.billiards;

import edu.ncsu.billiards.gamestates.GameState;
import edu.ncsu.billiards.gamestates.SimulationState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Billiards extends BasicGame {
	// window title
	private static final String GAME_TITLE = "Billiards";

	// dimensions in pixels
	private static final int WINDOW_WIDTH  = 800;
	private static final int WINDOW_HEIGHT = 440;

	private GameState gameState;



	public Billiards() {
		super(GAME_TITLE);
	}



	public void changeState(GameState newState) {
		gameState = newState;
	}



	@Override
	public void init(GameContainer container) throws SlickException {
		gameState = new SimulationState();
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		gameState.update((double) delta / 1000);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		gameState.render(g);
	}



	public void mouseClicked(int button, int x, int y, int clickCount) {
		gameState.mouseClicked(button,
		            (double) x / Renderer.SCALE,
		            (double) y / Renderer.SCALE,
		            clickCount);
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		gameState.mouseDragged(
		            (double) oldx / Renderer.SCALE,
		            (double) oldy / Renderer.SCALE,
		            (double) newx / Renderer.SCALE,
		            (double) newy / Renderer.SCALE);
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		gameState.mouseMoved(
		            (double) oldx / Renderer.SCALE,
		            (double) oldy / Renderer.SCALE,
		            (double) newx / Renderer.SCALE,
		            (double) newy / Renderer.SCALE);
	}

	public void mousePressed(int button, int x, int y) {
		gameState.mousePressed(button,
		            (double) x / Renderer.SCALE,
		            (double) y / Renderer.SCALE);
	}

	public void mouseReleased(int button, int x, int y) {
		gameState.mouseReleased(button,
		            (double) x / Renderer.SCALE,
		            (double) y / Renderer.SCALE);
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
