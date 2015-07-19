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

	private Button exitButton;





	public MenuState() throws SlickException {
		menuBackground = new Image("res/menu-bg.png");

		exitButton = new Button("Exit", 300, 300);
	}





	public void update(double delta) {
		
	}

	public void render(Graphics graphics) {
		Renderer.render(menuBackground, 0, 0, graphics);

		Renderer.render("Time Travel Pool", 100, 100, graphics);

		Renderer.render("Click to start.", 100, 200, graphics);

		Renderer.render(exitButton, graphics);

		// render buttons
	}





	public void enter(Billiards stateMachine) {
		this.stateMachine = stateMachine;
	}





	public void mouseClicked(int button, double x, double y, int clickCount) {
		stateMachine.changeState(new SimulationState());
	}

	public void mouseDragged(double oldx, double oldy, double newx, double newy) {

	}

	public void mouseMoved(double oldx, double oldy, double newx, double newy) {

	}

	public void mousePressed(int button, double x, double y) {

	}

	public void mouseReleased(int button, double x, double y) {

	}
}
