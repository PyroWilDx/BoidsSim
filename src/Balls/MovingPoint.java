package Balls;

import gui.GraphicalElement;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents a point that moves by a certain speed (dx, dy) in each frame.
 */
public class MovingPoint extends Point {

    // Attributes
    private final int initX, initY;
    private int initDx, initDy;
    private int dx, dy;

    /**
     * Constructor with initial position.
     * @param x The initial x-coordinate of the ball.
     * @param y The initial y-coordinate of the ball.
     */
    public MovingPoint(int x, int y) {
        super(x, y);

        this.initX = x;
        this.initY = y;
        this.initDx = 0;
        this.initDy = 0;

        setSpeed(0, 0);
    }

    /**
     * Constructor with initial position and speed.
     * @param x The initial x-coordinate of the ball.
     * @param y The initial y-coordinate of the ball.
     * @param dx The initial speed in the x direction.
     * @param dy The initial speed in the y direction.
     */
    public MovingPoint(int x, int y, int dx, int dy) {
        this(x, y);

        this.initDx = dx;
        this.initDy = dy;

        setSpeed(dx, dy);
    }

    /**
     * Translate the ball by its current speed (dx, dy).
     */
    public void translate() {
        super.translate(dx, dy);
    }

    /**
     * Handle the translation of the ball when it hits the borders of the specified maximum width and height.
     * @param maxW The maximum width of the gui.
     * @param maxH The maximum height of the gui.
     * @param bounce If true, the ball bounces on the border.
     */
    public void translateBorder(int maxW, int maxH, boolean bounce) {
        int lastX = (int) getX();
        int lastY = (int) getY();

        this.translate();

        boolean xInf0 = getX() < 0;
        boolean xSupMax = getX() > maxW;
        boolean yInf0 = getY() < 0;
        boolean ySupMax = getY() > maxH;

        if (xInf0 || xSupMax) {
            if (bounce) {
                if (xInf0) setLocation(-dx - lastX, getY());
                else setLocation(maxW - (dx - (maxW - lastX)), getY());
                dx = -dx;
            } else {
                if (xInf0) setLocation(maxW - (-dx - lastX), getY());
                else setLocation(dx - (maxW - lastX), getY());
            }
        }
        if (yInf0 || ySupMax) {
            if (bounce) {
                if (yInf0) setLocation(getX(), -dy - lastY);
                else setLocation(getX(), maxH - (dy - (maxH - lastY)));
                dy = -dy;
            } else {
                if (yInf0) setLocation(getX(), maxH - (-dy - lastY));
                else setLocation(getX(), dy - (maxH - lastY));
            }
        }
    }

    /**
     * Method to be overridden by subclasses for a cycle.
     * @param allPoints The list of all MovingPoints in the simulation.
     */
    public void cycle(ArrayList<MovingPoint> allPoints) {
        // To be implemented by subclasses
    }

    /**
     * Reset the position and speed of the ball to its initial state.
     */
    public void reset() {
        super.setLocation(initX, initY);
        this.dx = initDx;
        this.dy = initDy;
    }

    /**
     * Get the fill color of the ball.
     * @return The fill color of the ball.
     */
    public Color getFillColor() {
        return Color.WHITE;
    }

    /**
     * Get the border color of the ball.
     * @return The border color of the ball.
     */
    public Color getBorderColor() {
        return Color.WHITE;
    }

    /**
     * Get the graphical representation of the ball.
     * @return A graphical element representing the ball.
     */
    public GraphicalElement getGraphics() {
        return new gui.Oval(x + 4, y + 4,
                getBorderColor(), getFillColor(), 8, 8);
    }

    /**
     * Set the speed of the ball for each translation (frame) of the simulation.
     * @param dx The speed in the x direction.
     * @param dy The speed in the y direction.
     */
    public void setSpeed(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Get the speed of the ball in the x direction.
     * @return The speed in the x direction.
     */
    public int getDx() {
        return this.dx;
    }

    /**
     * Get the speed of the ball in the y direction.
     * @return The speed in the y direction.
     */
    public int getDy() {
        return this.dy;
    }

    /**
     * Get a string representation of the ball's position and speed.
     * @return A string representation of the ball speed and position.
     */
    @Override
    public String toString() {
        return super.toString() + "[dx=" + dx + ",dy=" + dy + "]";
    }
}
