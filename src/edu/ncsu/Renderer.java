package edu.ncsu;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Vector2;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Renderer {
	public static void render(PoolBall ball, Graphics g) {
		Color tempColor = g.getColor();
		g.setColor(Color.red);

		float x      = ball.getX();
		float y      = ball.getY();
		float width  = ball.getRadius() * 2;
		float height = ball.getRadius() * 2;

		System.out.println(x + " " + y + " " + width + " " + height);

		g.fillOval(x, y, width, height);

		g.setColor(tempColor);
	}
}
