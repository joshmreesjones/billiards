package edu.ncsu.billiards.gamestates;

import edu.ncsu.billiards.Billiards;
import edu.ncsu.billiards.Renderer;

import edu.ncsu.billiards.gameobjects.Cushion;
import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;
import edu.ncsu.billiards.gameobjects.VelocityLine;

import edu.ncsu.billiards.gamestates.GameState;

import edu.ncsu.billiards.setups.Setup;

import edu.ncsu.billiards.world.BilliardsWorld;
import edu.ncsu.billiards.world.GameWorld;
import edu.ncsu.billiards.world.PredictionWorld;

import org.dyn4j.dynamics.Body;

import org.dyn4j.dynamics.contact.ContactAdapter;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;

import org.dyn4j.geometry.Vector2;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SimulationState implements GameState {
	// user-facing world
	private GameWorld gameWorld;
	// world used to predict future collisions
	private PredictionWorld predictionWorld;

	private Image tableBackground;

	private InputHandler inputHandler;

	private Billiards game;





	public SimulationState(Setup setup) {
		gameWorld = new GameWorld(new GameContactHandler());
		predictionWorld = new PredictionWorld(new PredictionContactHandler());

		inputHandler = new InputHandler();

		try {
			tableBackground = new Image("res/table-bg.png");
		} catch (SlickException ex) {
			System.out.println("Error loading background image.");
			System.exit(1);
		}

		setUpGameObjects(setup, gameWorld);
		setUpGameObjects(setup, predictionWorld);
	}

	/**
	 * Adds game objects to the specified world.
	 * 
	 * This method is utilized because the gameWorld and predictionWorld
	 * should have the same setup as each other. Objects should be logically
	 * identical, but should not be the same objects in memory, as
	 * the prediction world will update the Bodies in the world, and we
	 * don't want those to change in the game world.
	 * 
	 * @param setup the setup used to populate the world with game objects
	 * @param world the world to add objects to
	 */
	public void setUpGameObjects(Setup setup, BilliardsWorld world) {
		for (Cushion cushion : setup.generateCushions()) {
			world.addCushion(cushion);
		}

		for (Pocket pocket : setup.generatePockets()) {
			world.addPocket(pocket);
		}

		for (PoolBall ball : setup.generateBalls()) {
			world.addCurrentBall(ball);
		}
	}

	public void update(double delta) {
		// only update if we need to
		if (gameWorld.hasMovingBalls()) {
			gameWorld.update(delta);
		}
	}

	public void render(Graphics g) {
		Renderer.render(tableBackground, 0, 0, g);

		for (Pocket pocket : gameWorld.getPockets()) {
			Renderer.render(pocket, g);
		}

		for (Cushion cushion : gameWorld.getCushions()) {
			//Renderer.render(cushion, g);
		}

		for (PoolBall ball : gameWorld.getCurrentBalls()) {
			Renderer.render(ball, g);
		}
	}





	public void enter(Billiards game) {
		this.game = game;
	}





	public void mouseClicked(int button, float x, float y, int clickCount) {
		inputHandler.mouseClicked(button,
		                          (double) x / Renderer.SCALE,
		                          (double) y / Renderer.SCALE,
		                          clickCount);
	}

	public void mouseDragged(float oldx, float oldy, float newx, float newy) {
		inputHandler.mouseDragged((double) oldx / Renderer.SCALE,
		                          (double) oldy / Renderer.SCALE,
		                          (double) newx / Renderer.SCALE,
		                          (double) newy / Renderer.SCALE);
	}

	public void mouseMoved(float oldx, float oldy, float newx, float newy) {
		inputHandler.mouseMoved((double) oldx / Renderer.SCALE,
		                        (double) oldy / Renderer.SCALE,
		                        (double) newx / Renderer.SCALE,
		                        (double) newy / Renderer.SCALE);
	}

	public void mousePressed(int button, float x, float y) {
		inputHandler.mousePressed(button,
		                          (double) x / Renderer.SCALE,
		                          (double) y / Renderer.SCALE);
	}

	public void mouseReleased(int button, float x, float y) {
		inputHandler.mouseReleased(button,
		                           (double) x / Renderer.SCALE,
		                           (double) y / Renderer.SCALE);
	}





	private class InputHandler {
		boolean draggingFromBall = false;
		PoolBall selectedBall;

		boolean draggingFromPocket = false;
		Pocket selectedPocket;

		public void mouseDragged(double oldX, double oldY,
		                         double newX, double newY) {
			if (draggingFromBall) {
				selectedBall.getVelocityLine().setEnd(newX, newY);
			}

			if (draggingFromPocket) {
				double startX = selectedPocket.getWorldCenter().x;
				double startY = selectedPocket.getWorldCenter().y;
				double x = newX - startX;
				double y = newY - startY;

				selectedPocket.setExitDirection(new Vector2(x, y));
			}
		}

		public void mousePressed(int button, double x, double y) {
			// all balls should be asleep before we can interact with the world
			if (SimulationState.this.gameWorld.hasMovingBalls()) {
				return;
			}

			Vector2 point = new Vector2(x, y);

			// check if mouse press is on current ball
			for (PoolBall ball : SimulationState.this.gameWorld.getCurrentBalls()) {
				if (ball.contains(point)) {
					draggingFromBall = true;
					selectedBall = ball;

					double startX = ball.getWorldCenter().x;
					double startY = ball.getWorldCenter().y;

					selectedBall.getVelocityLine().setStart(startX, startY);
					selectedBall.getVelocityLine().setEnd(startX, startY);
				}
			}

			// check if mouse press is on pocket
			for (Pocket pocket : SimulationState.this.gameWorld.getPockets()) {
				if (pocket.contains(point)) {
					draggingFromPocket = true;
					selectedPocket = pocket;
				}
			}
		}

		public void mouseReleased(int button, double x, double y) {
			// send the ball on its way (if it was on a ball)
			if (draggingFromBall) {
				double[] dragStart = selectedBall.getVelocityLine().getStart();
				double[] dragEnd = selectedBall.getVelocityLine().getEnd();

				Vector2 force = new Vector2(
					(dragEnd[0] - dragStart[0]) * 200,
					(dragEnd[1] - dragStart[1]) * 200
				);

				selectedBall.applyForce(force);

				predictionWorld.runSimulation(SimulationState.this.gameWorld);

				selectedBall.getVelocityLine().setStart(0, 0);
				selectedBall.getVelocityLine().setEnd(0, 0);

				draggingFromBall = false;
				selectedBall = null;
			}

			if (draggingFromPocket) {
				draggingFromPocket = false;
				selectedPocket = null;
			}
		}

		public void mouseClicked(int button, double x, double y, int clickCount) {

		}

		public void mouseMoved(double oldx, double oldy, double newx, double newy) {

		}
	}





	private class GameContactHandler extends ContactAdapter
	                              implements ContactListener {
		public void sensed(ContactPoint point) {
			Body body1 = point.getBody1();
			Body body2 = point.getBody2();

			if (!body1.getFixture(0).isSensor()) {
				// body1 is a pool ball
				handleContact((PoolBall) body1, (Pocket) body2);
			} else if (!body2.getFixture(0).isSensor()) {
				// body2 is a pool ball
				handleContact((PoolBall) body2, (Pocket) body1);
			}
		}

		private void handleContact(PoolBall ball, Pocket pocket) {
			if (!ball.isExiting()) {
				gameWorld.removeCurrentBall(ball);
			}
		}
	}





	private class PredictionContactHandler extends ContactAdapter
	                                    implements ContactListener {
		public void sensed(ContactPoint point) {
			Body body1 = point.getBody1();
			Body body2 = point.getBody2();

			if (body1.getFixture(0).isSensor()) {
				// body1 is the pocket
				// body2 is the pool ball
				handleContact((PoolBall) body2, (Pocket) body1);
			} else if (body2.getFixture(0).isSensor()) {
				// body1 is the pool ball
				// body2 is the pocket
				handleContact((PoolBall) body1, (Pocket) body2);
			}
		}

		private void handleContact(PoolBall ball, Pocket pocket) {
			// translate to (0, 0), the top left corner
			ball.translateToOrigin();

			Pocket destination = pocket.getDestination();
			
			// move ball to pocket
			ball.translate(destination.getWorldCenter().x,
			               destination.getWorldCenter().y);

			// set velocity of ball - in direction that pocket
			// specifies, but with the original magnitude of the ball
			Vector2 exitDirection = destination.getExitDirection();
			double magnitude = ball.getLinearVelocity().getMagnitude();
			exitDirection.setMagnitude(magnitude);

			ball.setLinearVelocity(exitDirection);

			// record the time
			ball.setEntryTime(predictionWorld.getTime() + pocket.getTimeDifference());

			// add ball to gameWorld's futureBalls
			gameWorld.addFutureBall(ball);

			// remove from predictionWorld's currentBalls
			predictionWorld.removeCurrentBall(ball);
		}
	}
}
