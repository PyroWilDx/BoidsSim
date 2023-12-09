package Boids;

import Balls.MovingPoint;

import java.awt.*;
import java.util.ArrayList;

/**
 * Correspond to obstacle in the simulation (Immovable Boid).
 */
public class BoidImmovable extends Boid {
    /**
     * Constructor.
     *
     * @param x Initial x-coordinate.
     * @param y Initial y-coordinate.
     */
    public BoidImmovable(int x, int y) {
        super(x, y, 0, 0);
    }

    /**
     * Cycle method to make the boid not move.
     *
     * @param boids ArrayList of MovingPoints representing Boids.
     */
    @Override
    public void cycle(ArrayList<MovingPoint> boids) {
        super.cycle(boids);

        setSpeed(0, 0);
    }

    /**
     * Get the border color of a BoidImmovable.
     * @return The border color of the BoidImmovable.
     */
    @Override
    public Color getBorderColor() {
        return Color.RED;
    }
}
