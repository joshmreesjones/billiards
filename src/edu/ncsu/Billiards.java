package edu.ncsu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Billiards extends BasicGame {
	public Billiards() {
		super("Billiards");
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Billiards());
			app.setDisplayMode(640, 480, false);
			app.start();
		} catch (SlickException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {

	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {

	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		Image table = new Image("res/pool.jpg");
		table.draw(0, 0);
	}
}
