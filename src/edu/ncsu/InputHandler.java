package edu.ncsu;

public class InputHandler {
	private int startX;
	private int startY;
	private int endX;
	private int endY;

	private VelocityLine ballVelocityLine;

	public InputHandler(VelocityLine ballVelocityLine) {
		this.ballVelocityLine = ballVelocityLine;
	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		endX = newx;
		endY = newy;

		ballVelocityLine.setEnd(newx, newy);
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
	}

	public void mousePressed(int button, int x, int y) {
		startX = x;
		startY = y;

		ballVelocityLine.setStart(x, y);
	}

	public void mouseReleased(int button, int x, int y) {
	}
}
