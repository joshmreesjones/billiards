package edu.ncsu.billiards.gamestates;

import edu.ncsu.billiards.Billiards;
import edu.ncsu.billiards.Renderer;

import edu.ncsu.billiards.gamestates.SimulationState;

import edu.ncsu.billiards.ui.Button;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MenuState implements GameState {
	private Image menuBackground;

	private Billiards stateMachine;

	private InputHandler inputHandler;

	private Button playButton;
	private Button exitButton;





	public MenuState() throws SlickException {
		menuBackground = new Image("res/menu-bg.png");

		inputHandler = new InputHandler();

		playButton = new Button("Play", 300, 250);
		exitButton = new Button("Exit", 300, 300);
	}





	public void update(double delta) {
		
	}

	public void render(Graphics graphics) {
		Renderer.render(menuBackground, 0, 0, graphics);

		Renderer.render("Time Travel Pool", 100, 100, graphics);

		Renderer.render(playButton, graphics);
		Renderer.render(exitButton, graphics);

		// render buttons
	}





	public void enter(Billiards stateMachine) {
		this.stateMachine = stateMachine;
	}





	public void mouseClicked(int button, float x, float y, int clickCount) {
		inputHandler.mouseClicked(button, x, y, clickCount);
	}

	public void mouseDragged(float oldx, float oldy, float newx, float newy) {
		inputHandler.mouseDragged(oldx, oldy, newx, newy);
	}

	public void mouseMoved(float oldx, float oldy, float newx, float newy) {
		inputHandler.mouseMoved(oldx, oldy, newx, newy);
	}

	public void mousePressed(int button, float x, float y) {
		inputHandler.mousePressed(button, x, y);
	}

	public void mouseReleased(int button, float x, float y) {
		inputHandler.mouseReleased(button, x, y);
	}

	private class InputHandler {
		public void mouseDragged(float oldX, float oldY,
		                         float newX, float newY) {
		}

		public void mousePressed(int button, float x, float y) {
		}

		public void mouseReleased(int button, float x, float y) {
		}

		public void mouseClicked(int button, float x, float y, int clickCount) {
			System.out.println(x + " " + y);
			if (playButton.getRectangle().contains(x, y)) {
				stateMachine.changeState(new SimulationState());
			} else if (exitButton.getRectangle().contains(x, y)) {
				System.exit(0);
			}
		}

		public void mouseMoved(float oldx, float oldy, float newx, float newy) {
		}
	}
}
