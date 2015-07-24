package edu.ncsu.billiards;

import edu.ncsu.billiards.gamestates.GameState;
import edu.ncsu.billiards.gamestates.MenuState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Billiards extends BasicGame {
	// window title
	private static final String GAME_TITLE = "Billiards";

	// dimensions in pixels
	private static final int WINDOW_WIDTH  = 872;
	private static final int WINDOW_HEIGHT = 472;

	private GameState gameState;



	public Billiards() {
		super(GAME_TITLE);
	}



	@Override
	public void init(GameContainer container) throws SlickException {
		changeState(new MenuState(WINDOW_WIDTH, WINDOW_HEIGHT));
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		gameState.update((double) delta / 1000);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		gameState.render(g);
	}



	public void changeState(GameState newState) {
		gameState = newState;
		gameState.enter(this);
	}



	public void mouseClicked(int button, int x, int y, int clickCount) {
		gameState.mouseClicked(button, (float) x, (float) y, clickCount);
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		gameState.mouseDragged((float) oldx, (float) oldy,
		                       (float) newx, (float) newy);
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		gameState.mouseMoved((float) oldx, (float) oldy,
		                     (float) newx, (float) newy);
	}

	public void mousePressed(int button, int x, int y) {
		gameState.mousePressed(button, (float) x, (float) y);
	}

	public void mouseReleased(int button, int x, int y) {
		gameState.mouseReleased(button, (float) x, (float) y);
	}



	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Billiards());
			app.setShowFPS(false);
			app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
			app.start();
		} catch (SlickException ex) {
			ex.printStackTrace();
		}
	}
}
