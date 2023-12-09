package Balls;

import Simulation.Simulator;
import Utils.AppUtils;
import gui.GUISimulator;

import java.util.ArrayList;

/**
 * Represent multiple MovingPoint.
 */
public class Balls implements Simulator {

    // Attributes
    private final ArrayList<MovingPoint> balls;
    private boolean bounce;

    /**
     * Constructor.
     */
    public Balls() {
        this.balls = new ArrayList<>();
        this.bounce = false;
    }

    /**
     * Add a ball to the collection of balls.
     * @param ball The ball to be added.
     */
    public void addBall(MovingPoint ball) {
        balls.add(ball);
    }

    /**
     * Translate all balls by dx, dy in the x and y directions.
     * @param dx The change in the x direction.
     * @param dy The change in the y direction.
     */
    public void translate(int dx, int dy) {
        for (MovingPoint ball : balls) {
            ball.translate(dx, dy);
        }
    }

    /**
     * Advance the simulation by one cycle.
     */
    @Override
    public void cycle() {
        for (MovingPoint ball : balls) {
            ball.cycle(balls);
            ball.translateBorder(AppUtils.GUI_W, AppUtils.GUI_H, bounce);
        }
    }

    /**
     * Reset the positions of all balls to their initial state.
     */
    @Override
    public void reset() {
        for (MovingPoint ball : balls) {
            ball.reset();
        }
    }

    /**
     * Draw the current state of the simulation on the gui.
     * @param gui Interface of the simulation.
     */
    @Override
    public void drawSelf(GUISimulator gui) {
        for (MovingPoint ball : balls) {
            gui.addGraphicalElement(ball.getGraphics());
        }
    }

    /**
     * Get a string of all ball positions.
     * @return A string containing the positions of all balls.
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (MovingPoint ball : balls) {
            res.append(ball.toString());
            res.append("\n");
        }
        return res.toString();
    }

    public void setBounce(boolean bounce) {
        this.bounce = bounce;
    }

    /**
     * Get the list of balls.
     * @return The list of balls.
     */
    public ArrayList<MovingPoint> getBalls() {
        return balls;
    }

    /**
     * Get the number of balls in the collection.
     * @return The number of balls.
     */
    public int getSize() {
        return balls.size();
    }
}
