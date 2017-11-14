package edu.orangecoastcollege.escapethecatcher;

/**
 * The Zombie class encapsulates the member variables and methods of the enemy of 'The Escape'.
 *
 * The two member variables are the ints which represent the Zombies location, a pair of X,Y
 * coordinates.
 *
 *
 * The move method is called from the movePlayer method in the GameActivity controller.
 * The zombie reacts to the movement of the player, always moving towards the player if the
 * terrain is passable.
 *
 * There are also getters and setters for the row and column member variables.
 */
public class Zombie {

    private int mRow;
    private int mCol;

    /**
     * move takes three parameters.  The two-dimensional array gameBoard,
     * the player's column and the player's row.
     *
     * If the player moved: up, left, down or right, the zombie will mirror the movement
     * of the player, as long as the next position is BoardCodes.EMPTY.
     * @param gameBoard
     * @param playerCol
     * @param playerRow
     */
    public void move(int[][] gameBoard, int playerCol, int playerRow) {
        if (mCol < playerCol && gameBoard[mRow][mCol + 1] == BoardCodes.EMPTY) {
            mCol++;
        } else if (mCol > playerCol && gameBoard[mRow][mCol - 1] == BoardCodes.EMPTY) {
            mCol--;
        } else if (mRow < playerRow && gameBoard[mRow + 1][mCol] == BoardCodes.EMPTY) {
            mRow++;
        } else if (mRow > playerRow && gameBoard[mRow - 1][mCol] == BoardCodes.EMPTY) {
            mRow--;
        }
    }

    /**
     * setRow is a setter for the mRow variable.
     * @param row an int representing the Zombie's row.
     */
    public void setRow(int row) {
        mRow = row;
    }

    /**
     * getRow is a getter for the mRow variable.
     * @return
     */
    public int getRow() {
        return mRow;
    }

    /**
     * setCol is a setter for the mCol variable.
     * @param col an int representing the Zombie's column.
     */
    public void setCol(int col) {
        mCol = col;
    }

    /**
     * getCol is a getter for the mCol variable.
     * @return
     */
    public int getCol() {
        return mCol;
    }

}
