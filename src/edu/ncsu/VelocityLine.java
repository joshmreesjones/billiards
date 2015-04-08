package edu.ncsu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.geom.Line;

public class VelocityLine extends Line {
	public VelocityLine(float x1, float y1, float x2, float y2) {
		super(x1, y1, x2, y2);
	}

	public void draw(Graphics g) {
		Color color = g.getColor();
		boolean isAntiAlias = g.isAntiAlias();

		g.setColor(Color.white);
		g.setAntiAlias(true);

		g.drawLine(getX1(), getY1(), getX2(), getY2());

		g.setColor(color);
		g.setAntiAlias(isAntiAlias);
	}
}
