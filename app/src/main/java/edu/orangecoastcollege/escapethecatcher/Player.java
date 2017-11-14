package edu.orangecoastcollege.escapethecatcher;

/**
 * The Player class encapsulates the member variables and methods of the User's character
 * in 'The Escape'.
 *
 * The two member variables are the ints which represent the player's location, a pair of X,Y
 * coordinates.
 *
 *
 * The move method is called from the movePlayer method in the GameActivity controller.
 * The player reacts to the fling of the User, always moving towards the direction of the fling,
 * if the terrain allows.
 *
 * There are also getters and setters for the row and column member variables.
 */
public class Player {
    private int mRow;
    private int mCol;

    /**
     * move is called from the movePlayer method in GameActivity.
     *
     * The top left corner of the board represents is represented by (0,0).
     * A move "UP" would decrease the row variable.
     * Movement "DOWN" increases the row variable.
     * Movement left decreases the column and moving right increases the column.
     *
     * @param gameBoard is a two-dimensional array of ints that represents the game board.
     * @param direction is the direction of the fling: "UP", "DOWN", "LEFT", "RIGHT"
     */
    public void move(int[][] gameBoard, String direction) {

        // TODO: Implement the logic for the move operation
        // TODO: If the gameBoard is obstacle free in the direction requested,
        // TODO: Move the player in the intended direction.  Otherwise, do nothing (player loses turn)
        switch(direction)
        {
            case "UP":
                if ( gameBoard[mRow - 1][mCol] != BoardCodes.OBSTACLE )
                    --mRow;
                break;
            case "DOWN":
                if ( gameBoard[mRow + 1][mCol] != BoardCodes.OBSTACLE)
                    ++mRow;
                break;
            case "LEFT":
                if ( gameBoard[mRow][mCol - 1] != BoardCodes.OBSTACLE)
                    --mCol;
                break;
            case "RIGHT":
                if ( gameBoard[mRow][mCol + 1] != BoardCodes.OBSTACLE)
                    ++mCol;
                break;
        }
    }

    /**
     * setRow is a typical setter method.
     * @param row an int representing the row.
     */
    public void setRow(int row) {
        mRow = row;
    }

    /**
     * getRow is a typical getter method.
     * @return the row.
     */
    public int getRow() {
        return mRow;
    }

    /**
     * setCol is a typical setter method for the column.
     * @param col an int representing the column.
     */
    public void setCol(int col) {
        mCol = col;
    }

    /**
     * getCol is a typical getter method.
     * @return the column.
     */
    public int getCol() {
        return mCol;
    }

}
