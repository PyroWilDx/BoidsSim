package Game;

import Simulation.Simulator;
import gui.GUISimulator;

import java.awt.*;

/**
 * Represents an abstract grid that acts as a simulator for various grid-based game types.
 */
public abstract class Grid implements Simulator {
    // Constants
    public static final int SQUARE_DISPLAY_SIZE = 10;

    // Attributes
    private final int width;
    private final int height;
    private final Square[][] grid;
    private final int[][] lastGrid;

    /**
     * Constructor for the Grid class.
     * @param width The width of the grid.
     * @param height The height of the grid.
     */
    public Grid(int width, int height) {
        // Initialize grid dimensions
        this.width = width;
        this.height = height;

        // Initialize grid and lastGrid arrays
        this.grid = new Square[width][height];
        this.lastGrid = new int[width][height];
    }

    /**
     * Abstract method to determine if two squares are neighbors, game-specific.
     * @param i0 Row index of the first square.
     * @param j0 Column index of the first square.
     * @param i1 Row index of the second square.
     * @param j1 Column index of the second square.
     * @return True if the squares are neighbors, false otherwise.
     */
    public abstract boolean isNeighbors(int i0, int j0, int i1, int j1);

    /**
     * Calculate the number of neighbors a cell had in the last cycle.
     * @param i Row index of the cell.
     * @param j Column index of the cell.
     * @return The number of neighbors the cell had in the last cycle.
     */
    public int getLastNeighborsCount(int i, int j) {
        // Initialize a counter for neighbors
        int cpt = 0;

        // Loop through neighboring cells
        for (int k = i - 1; k < i + 2; k++) {
            for (int l = j - 1; l < j + 2; l++) {
                // Skip the central cell
                if (k == i && l == j) continue;

                int mk = (k == -1) ? width - 1 : (k == width) ? 0 : k;
                int ml = (l == -1) ? height - 1 : (l == height) ? 0 : l;

                // Check if the cell is a neighbor
                if (isNeighbors(i, j, mk, ml)) cpt++;
            }
        }

        // Return the count of neighbors
        return cpt;
    }

    /**
     * Abstract method to update the state of a square based on the specific rules of the game.
     * @param i Row index of the square.
     * @param j Column index of the square.
     */
    public abstract void computeSquare(int i, int j);

    /**
     * Update the entire grid based on the rules of the game.
     */
    public void cycle() {
        // Update each square in the grid
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                computeSquare(i, j);
            }
        }

        // Save the state of each square for the next cycle
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                lastGrid[i][j] = grid[i][j].getState();
            }
        }
    }

    /**
     * Abstract method to generate a random square in the grid.
     * @param i Row index of the square.
     * @param j Column index of the square.
     * @return A randomly generated square.
     */
    public abstract Square genRdSquare(int i, int j);

    /**
     * Reset the grid by generating and assigning a random square to each cell.
     */
    public void reset() {
        // Generate and assign a random square to each cell in the grid
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Square rdSquare = genRdSquare(i, j);
                grid[i][j] = rdSquare;
                lastGrid[i][j] = rdSquare.getState();
            }
        }
    }

    /**
     * Draw the entire grid on the provided GUI.
     * @param gui The graphical user interface where the grid will be drawn.
     */
    public void drawSelf(GUISimulator gui) {
        // Draw each square on the GUI
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color fillColor = getSquareColorAtIndex(i, j);
                int halfSize = SQUARE_DISPLAY_SIZE / 2;
                // Create a graphical rectangle element and add it to the GUI
                gui.addGraphicalElement(new gui.Rectangle(
                        i * SQUARE_DISPLAY_SIZE + halfSize, j * SQUARE_DISPLAY_SIZE + halfSize,
                        Color.GRAY, fillColor,
                        SQUARE_DISPLAY_SIZE, SQUARE_DISPLAY_SIZE));
            }
        }
    }

    /**
     * Abstract method to get the color of a square at the specified coordinate.
     * @param i Row index of the square.
     * @param j Column index of the square.
     * @return The color of the square.
     */
    public abstract Color getSquareColorAtIndex(int i, int j);

    /**
     * Get the state of the square from the last cycle at the specified index.
     * @param i Row index of the square.
     * @param j Column index of the square.
     * @return The state of the square from the last cycle.
     */
    public int getLastSquareStateAtIndex(int i, int j) {
        return lastGrid[i][j];
    }

    /**
     * Get the square at the specified index.
     * @param i Row index of the square.
     * @param j Column index of the square.
     * @return The square at the specified index.
     */
    public Square getSquareAtIndex(int i, int j) {
        return grid[i][j];
    }
}