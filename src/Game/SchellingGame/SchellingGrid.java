package Game.SchellingGame;

import Game.Square;
import Game.ImmigrationGame.ImmigrationGrid;
import Utils.AppUtils;

import java.util.LinkedList;

/**
 * Logic behind the Schelling's Model of Segregation, extending the ImmigrationGrid class.
 */
public class SchellingGrid extends ImmigrationGrid {

    // Attributes
    private final int K;
    private final LinkedList<Square> freeSquares;

    /**
     * Constructor for the SchellingGrid class.
     *
     * @param w The width of the grid.
     * @param h The height of the grid.
     * @param n The number of states in the Immigration Game.
     * @param K The tolerance level for neighbors of different types in Schelling's Model.
     */
    public SchellingGrid(int w, int h, int n, int K) {
        super(w, h, n);
        this.K = K;
        this.freeSquares = new LinkedList<>();
    }

    /**
     * Check if two squares are neighbors in Schelling's Model.
     *
     * @param i0 Row index of the current square.
     * @param j0 Column index of the current square.
     * @param i1 Row index of the potential neighbor.
     * @param j1 Column index of the potential neighbor.
     * @return True if the potential neighbor is occupied and of a different type, false otherwise.
     */
    @Override
    public boolean isNeighbors(int i0, int j0, int i1, int j1) {
        int baseState = getLastSquareStateAtIndex(i0, j0);
        int targetState = getLastSquareStateAtIndex(i1, j1);
        return (targetState != 0 && baseState != targetState);
    }

    /**
     * Update the state of a square based on the rules of Schelling's Model.
     *
     * @param i Row index of the square.
     * @param j Column index of the square.
     */
    @Override
    public void computeSquare(int i, int j) {
        if (getLastSquareStateAtIndex(i, j) == 0) return;

        Square currSquare = getSquareAtIndex(i, j);

        int cptDiffNeighbors = getLastNeighborsCount(i, j);

        if (cptDiffNeighbors >= K) {
            Square targetSquare = freeSquares.poll();
            if (targetSquare != null) {

                targetSquare.setState(currSquare.getState());
                currSquare.setState(0);

                addFreeSquareRandomized(currSquare);
            } else {
                System.out.println("No available space!");
            }
        }
    }

    /**
     * Add a square to the list of free squares at a random position.
     *
     * @param square The square to be added to the list.
     */
    public void addFreeSquareRandomized(Square square) {
        int iRd = AppUtils.random.nextInt(freeSquares.size() + 1);
        freeSquares.add(iRd, square);
    }

    /**
     * Generate a random square with a 60% chance of being occupied in Schelling's Model.
     * If the square is empty, add it to the list of free squares.
     *
     * @param i Row index of the square.
     * @param j Column index of the square.
     * @return A randomly generated square.
     */
    @Override
    public Square genRdSquare(int i, int j) {
        Square square;
        if (AppUtils.random.nextFloat() < 0.6) square = super.genRdSquare(i, j);
        else square = new Square(i, j);

        if (square.getState() == 0) addFreeSquareRandomized(square);

        return square;
    }

    /**
     * Clear the list of free squares and reset the grid.
     */
    @Override
    public void reset() {
        freeSquares.clear();
        super.reset();
    }

}
