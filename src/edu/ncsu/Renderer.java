package edu.ncsu;

import java.util.Random;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Vector2;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Renderer {
	public static void render(PoolBall ball, Graphics g) {
		Color tempColor = g.getColor();

		int color = (new Random()).nextInt(3);

		g.setColor(Color.red);

		float x = (float) ball.getWorldCenter().x;
		float y = (float) ball.getWorldCenter().y;
		float width  = ball.getRadius() * 2;
		float height = ball.getRadius() * 2;

		g.fillOval(x, y, width, height);

		g.setColor(tempColor);
	}
}
