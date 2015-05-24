package edu.ncsu;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Vector2;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Renderer {
	// 300 pixels per meter
	private static final float SCALE = 300;

	public static void render(PoolBall ball, Graphics g) {
		Color tempColor = g.getColor();

		g.setColor(ball.getColor());

		float radius = ball.getRadius() * SCALE;
		float width  = radius * 2;
		float height = radius * 2;
		float xCenter = (float) ball.getWorldCenter().x * SCALE;
		float yCenter = (float) ball.getWorldCenter().y * SCALE;
		float x = xCenter - radius;
		float y = yCenter - radius;

		g.fillOval(x, y, width, height);

		g.setColor(tempColor);
	}

	public static void render(Bumper cushion, Graphics g) {
		Color tempColor = g.getColor();

		g.setColor(Color.red);

		//  100, 400, 300, 10
		// -150,  -5, 300, 10

		float x = cushion.getX() * SCALE;
		float y = cushion.getY() * SCALE;
		float width = cushion.getWidth() * SCALE;
		float height = cushion.getHeight() * SCALE;

		g.fillRect(x, y, width, height);
		System.out.println(x + " " + y + " " + width + " " + height);

		g.setColor(tempColor);
	}
}
