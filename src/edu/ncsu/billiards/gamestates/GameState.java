package edu.ncsu.billiards.gamestates;

import edu.ncsu.billiards.Billiards;

import org.newdawn.slick.Graphics;

public interface GameState {
	/**
	 * Updates the state of the game by the specified number of seconds.
	 *
	 * @param elapsedTime the number of seconds since the last update
	 */
	void update(double elapsedTime);



	/**
	 * Renders the state of the game with the specified Graphics object.
	 *
	 * @param g the graphics object to which to render
	 */
	void render(Graphics g);



	/**
	 * Enters this game state with the specified game if the game state
	 * needs to call back to the game to change states, get window
	 * dimensions, etc.
	 *
	 * @param game the game object used to switch states
	 */
	void enter(Billiards game);



	void mouseClicked(int button, float x, float y, int clickCount);
	void mouseDragged(float oldx, float oldy, float newx, float newy);
	void mouseMoved(float oldx, float oldy, float newx, float newy);
	void mousePressed(int button, float x, float y);
	void mouseReleased(int button, float x, float y);
}
