package edu.ncsu.billiards.gamestates;

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

	void mouseClicked(int button, double x, double y, int clickCount);
	void mouseDragged(double oldx, double oldy, double newx, double newy);
	void mouseMoved(double oldx, double oldy, double newx, double newy);
	void mousePressed(int button, double x, double y);
	void mouseReleased(int button, double x, double y);
}
