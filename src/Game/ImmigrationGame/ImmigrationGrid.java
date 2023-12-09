package Game.ImmigrationGame;

import Game.Grid;
import Game.Square;
import Utils.AppUtils;

import java.awt.*;

/**
 * Logic behind the Immigration Game.
 */
public class ImmigrationGrid extends Grid {
    // Attributes
    private final int n;

    /**
     * Constructor for the ImmigrationGrid class.
     * @param w The width of the grid.
     * @param h The height of the grid.
     * @param n The number of states in the Immigration Game.
     */
    public ImmigrationGrid(int w, int h, int n) {
        super(w, h);
        this.n = n;
    }

    /**
     * Get the next state in the Immigration Game sequence.
     * @param state The current state.
     * @return The next state.
     */
    private int getNextState(int state) {
        return (state + 1) % n;
    }

    /**
     * Check if two squares are neighbors in the Immigration Game.
     * @param i0 Row index of the first square.
     * @param j0 Column index of the first square.
     * @param i1 Row index of the second square.
     * @param j1 Column index of the second square.
     * @return True if the squares are neighbors, false otherwise.
     */
    @Override
    public boolean isNeighbors(int i0, int j0, int i1, int j1) {
        int baseState = getLastSquareStateAtIndex(i0, j0);
        int targetState = getLastSquareStateAtIndex(i1, j1);
        return (getNextState(baseState) == targetState);
    }

    /**
     * Update the state of a square based on the rules of the Immigration Game.
     * @param i Row index of the square.
     * @param j Column index of the square.
     */
    @Override
    public void computeSquare(int i, int j) {
        Square currSquare = getSquareAtIndex(i, j);
        int state = currSquare.getState();
        int cptNextStateNeighbors = getLastNeighborsCount(i, j);

        // If a square has more than 2 neighbors in the next state, update its state.
        if (cptNextStateNeighbors > 2) {
            currSquare.setState(getNextState(state));
        }
    }

    /**
     * Generate a random square with a random initial state in the Immigration Game.
     * @param i Row index of the square.
     * @param j Column index of the square.
     * @return A randomly generated square.
     */
    @Override
    public Square genRdSquare(int i, int j) {
        int state = AppUtils.random.nextInt(n);
        return new Square(i, j, state);
    }

    /**
     * Get the color of a square at the specified coordinate in the Immigration Game.
     * @param i Row index of the square.
     * @param j Column index of the square.
     * @return The color of the square.
     */
    @Override
    public Color getSquareColorAtIndex(int i, int j) {
        int state = getSquareAtIndex(i, j).getState();
        int colorIntensity = (int) Math.round((255. / (n - 1)) * state);
        colorIntensity = 255 - colorIntensity;
        return new Color(colorIntensity, colorIntensity, colorIntensity);
    }
}
