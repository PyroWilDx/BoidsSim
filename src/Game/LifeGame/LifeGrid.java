package Game.LifeGame;

import Game.Grid;
import Game.Square;
import Utils.AppUtils;

import java.awt.*;

/**
 * Logic behind LifeGame.
 */
public class LifeGrid extends Grid {

    /**
     * Constructor for the LifeGrid class.
     * @param w The width of the grid.
     * @param h The height of the grid.
     */
    public LifeGrid(int w, int h) {
        super(w, h);
    }

    /**
     * Check if a square at specified coordinates has a living neighbor.
     * @param i0 Row index of the current square.
     * @param j0 Column index of the current square.
     * @param i1 Row index of the potential neighbor.
     * @param j1 Column index of the potential neighbor.
     * @return True if the potential neighbor is alive, false otherwise.
     */
    @Override
    public boolean isNeighbors(int i0, int j0, int i1, int j1) {
        int targetState = getLastSquareStateAtIndex(i1, j1);
        return (targetState == 1);
    }

    /**
     * Update the state of a square based on the rules of Conway's Game of Life.
     * @param i Row index of the square.
     * @param j Column index of the square.
     */
    @Override
    public void computeSquare(int i, int j) {
        Square currSquare = getSquareAtIndex(i, j);
        int cptAliveNeighbors = getLastNeighborsCount(i, j);

        if (currSquare.getState() == 0 && cptAliveNeighbors == 3) {
            currSquare.setState(1);
        }
        if (currSquare.getState() == 1 && (cptAliveNeighbors != 2 && cptAliveNeighbors != 3)) {
            currSquare.setState(0);
        }
    }

    /**
     * Generate a random square with a 10% chance of being alive in Conway's Game of Life.
     * @param i Row index of the square.
     * @param j Column index of the square.
     * @return A randomly generated square.
     */
    @Override
    public Square genRdSquare(int i, int j) {
        int state = (AppUtils.random.nextFloat() < 0.1) ? 1 : 0;
        return new Square(i, j, state);
    }

    /**
     * Get the color of a square at the specified coordinate in Conway's Game of Life.
     * @param i Row index of the square.
     * @param j Column index of the square.
     * @return Blue if the square is alive, white otherwise.
     */
    @Override
    public Color getSquareColorAtIndex(int i, int j) {
        return (getSquareAtIndex(i, j).getState() == 1)
                ? Color.BLUE : Color.WHITE;
    }
}
