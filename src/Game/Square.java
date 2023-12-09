package Game;

import java.awt.*;

/**
 * Represents a square with a state.
 * state: the state
 */
public class Square extends Point {
    // Attributes
    private int state;

    /**
     * Constructor to create an empty square.
     *
     * @param x The x-coordinate of the square.
     * @param y The y-coordinate of the square.
     */
    public Square(int x, int y) {
        super(x, y);
        this.state = 0;
    }

    /**
     * Constructor to create a square with a specified state.
     *
     * @param x     The x-coordinate of the square.
     * @param y     The y-coordinate of the square.
     * @param state The initial state of the square.
     */
    public Square(int x, int y, int state) {
        this(x, y); // Call the default constructor to set coordinates.
        this.state = state;
    }

    /**
     * Sets the state of the square.
     *
     * @param state The new state to set for the square.
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Gets the current state of the square.
     *
     * @return The current state of the square.
     */
    public int getState() {
        return state;
    }
}