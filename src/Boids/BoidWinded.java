package Boids;

import Balls.MovingPoint;
import Utils.AppUtils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Boid with their speed adjust by a wind rule.
 */
public class BoidWinded extends Boid {
    public static final double WIND_DIVISOR = 2.;

    /**
     * Constructor.
     *
     * @param x  Initial x-coordinate.
     * @param y  Initial y-coordinate.
     * @param dx Initial x velocity.
     * @param dy Initial y velocity.
     */
    public BoidWinded(int x, int y, int dx, int dy) {
        super(x, y, dx, dy);
    }

    /**
     * Wind behavior.
     *
     * @return Point representing the wind force.
     */
    public Point windRule() {
        Point2D.Double w = new Point2D.Double(0, 0);

        double halfMax = getMaxSpeed() / 2.;

        w.x = ((double) this.x / AppUtils.GUI_W) * halfMax;
        w.y = ((double) this.y / AppUtils.GUI_H) * halfMax;
        w.y -= halfMax;

        w.x /= WIND_DIVISOR;
        w.y /= WIND_DIVISOR;

        return new Point((int) Math.round(w.x), (int) Math.round(w.y));
    }

    /**
     * Cycle method to simulate wind effect on the boid.
     *
     * @param boids ArrayList of MovingPoints representing Boids.
     */
    @Override
    public void cycle(ArrayList<MovingPoint> boids) {
        super.cycle(boids);
        // Apply wind rule and adjust Boid's speed
        Point w = windRule();
        setSpeed(getDx() - w.x, getDy() - w.y);
    }

    /**
     * Get the border color of a BoidWinded.
     * @return The border color of the BoidWinded.
     */
    @Override
    public Color getBorderColor() {
        return Color.GREEN;
    }

}
