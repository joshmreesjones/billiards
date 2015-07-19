package edu.ncsu.billiards;

import edu.ncsu.billiards.gameobjects.Cushion;
import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;
import edu.ncsu.billiards.gameobjects.VelocityLine;

import edu.ncsu.billiards.ui.Button;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Renderer {
	// 300 pixels per meter
	public static final float SCALE = 300;

	public static void render(Cushion cushion, Graphics graphics) {
		graphics.setColor(Color.orange);

		// x and y are the top left corner of the rectangle
		float x = cushion.getX() * SCALE;
		float y = cushion.getY() * SCALE;
		float width = cushion.getWidth() * SCALE;
		float height = cushion.getHeight() * SCALE;

		graphics.fillRect(x, y, width, height);
	}

	public static void render(Pocket pocket, Graphics graphics) {
		graphics.setColor(Color.black);

		float radius = pocket.getRadius() * SCALE;
		float width = radius * 2;
		float height = radius * 2;
		float centerX = (float) pocket.getWorldCenter().x * SCALE;
		float centerY = (float) pocket.getWorldCenter().y * SCALE;
		float x = centerX - radius;
		float y = centerY - radius;

		graphics.fillOval(x, y, width, height);

		render(pocket.getVelocityLine(), graphics);
	}

	public static void render(PoolBall ball, Graphics graphics) {
		if (!ball.isAsleep()) {
			graphics.setColor(ball.getColor());
		} else {
			graphics.setColor(Color.gray);
		}

		float radius = ball.getRadius() * SCALE;
		float width  = radius * 2;
		float height = radius * 2;
		float centerX = (float) ball.getWorldCenter().x * SCALE;
		float centerY = (float) ball.getWorldCenter().y * SCALE;
		float x = centerX - radius;
		float y = centerY - radius;

		graphics.fillOval(x, y, width, height);

		// we need to render the velocity line as well
		render(ball.getVelocityLine(), graphics);
	}

	private static void render(VelocityLine line, Graphics graphics) {
		graphics.setColor(Color.white);

		double[] start = line.getStart();
		double[] end   = line.getEnd();

		if (start[0] == end[0] &&
		    start[1] == end[1]) {
			return;
		}

		float x1 = (float) start[0] * SCALE;
		float y1 = (float) start[1] * SCALE;
		float x2 = (float)   end[0] * SCALE;
		float y2 = (float)   end[1] * SCALE;

		graphics.drawLine(x1, y1, x2, y2);
	}

	public static void render(Image image, float x, float y, Graphics graphics) {
		graphics.drawImage(image, x, y);
	}

	public static void render(String text, float x, float y, Graphics graphics) {
		graphics.drawString(text, x, y);
	}

	public static void render(Button button, Graphics graphics) {
		float x = (float) button.getX();
		float y = (float) button.getY();
		float width  = (float) button.getWidth();
		float height = (float) button.getHeight();

		String label = button.getLabel();

		int fontWidth = graphics.getFont().getWidth(label);
		int fontHeight = graphics.getFont().getHeight(label);
		float xOffset = (width - fontWidth) / 2;
		float yOffset = (height - fontHeight) / 2;

		graphics.setColor(Color.green);
		graphics.fillRect(x, y, width, height);
		graphics.setColor(Color.white);
		graphics.drawString(label, x + xOffset, y + yOffset);
	}
}
