package edu.ncsu;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Vector2;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Renderer {
	// 300 pixels per meter
	private static final float SCALE = 300;

	public static void render(GameObject object, Graphics g) {
		if (object instanceof PoolBall) {
			render((PoolBall) object, g);
		} else if (object instanceof Bumper) {
			render((Bumper) object, g);
		}
	}

	public static void render(PoolBall ball, Graphics g) {
		Color tempColor = g.getColor();

		g.setColor(ball.getColor());

		float x = (float) ball.getWorldCenter().x;
		float y = (float) ball.getWorldCenter().y;
		float width  = ball.getRadius() * SCALE;
		float height = ball.getRadius() * SCALE;

		g.fillOval(x, y, width, height);

		g.setColor(tempColor);
	}

	public static void render(Bumper cushion, Graphics g) {
		Color tempColor = g.getColor();

		g.setColor(Color.red);

		//  100, 400, 300, 10
		// -150,  -5, 300, 10

		float x = cushion.getX();
		float y = cushion.getY();
		float width = cushion.getWidth();
		float height = cushion.getHeight();

		g.fillRect(x, y, width, height);
		System.out.println(x + " " + y + " " + width + " " + height);

		g.setColor(tempColor);
	}
}
