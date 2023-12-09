package Boids;

import Balls.MovingPoint;
import Utils.AppUtils;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class adjusting boid behavior to go to the center of the gui.
 */
public class BoidGoal extends Boid {
    public static final int GOAL_DIVISOR = 100;

    /**
     * Constructor.
     *
     * @param x  Initial x-coordinate.
     * @param y  Initial y-coordinate.
     * @param dx Initial x velocity.
     * @param dy Initial y velocity.
     */
    public BoidGoal(int x, int y, int dx, int dy) {
        super(x, y, dx, dy);
    }

    public Point goalRule() {
        Point g = new Point(0, 0);

        int goalX = AppUtils.GUI_W / 2;
        int goalY = AppUtils.GUI_H / 2;

        g.x = goalX - x;
        g.y = goalY - y;

        g.x /= GOAL_DIVISOR;
        g.y /= GOAL_DIVISOR;

        return g;
    }

    /**
     * Cycle method to implement goal behavior.
     * 
     * @param boids ArrayList of MovingPoints representing Boids.
     */
    @Override
    public void cycle(ArrayList<MovingPoint> boids) {
        super.cycle(boids);

        Point g = goalRule();
        setLimitedSpeed(getDx() + g.x, getDy() + g.y);
    }

    /**
     * Get the border color of a BoidGoal.
     * @return The border color of the BoidGoal.
     */
    @Override
    public Color getBorderColor() {
        return Color.BLUE;
    }
}
