package edu.orangecoastcollege.escapethecatcher;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * GameActivity is the main controller of 'The Escape'.
 *
 * We declare a GestureDetector, set a FLING_THRESHOLD constant.
 * These will be used to detect the User's flings which will tell
 * the player's character which way to move.
 *
 * We also instantiate constants such as SQUARE and OFFSET, COLS and ROWS,
 * these are used along with the gameBoard[][] two-dimensional array
 * to build the game board.
 *
 * There are also RelativeLayout Views, ImageViews, and TextViews to be inflated.
 *
 * Also among the member variables:
 *  - a List of ImageViews so we know how many views to inflate
 *  - player object
 *  - zombie object
 *  - coordinates of the row and column of the exit
 *  - number of wins / losses
 *  - a LayoutInflater object
 *
 */
public class GameActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener {

    private GestureDetector gestureDetector;

    //FLING THRESHOLD VELOCITY
    final int FLING_THRESHOLD = 500;

    //BOARD INFORMATION
    final int SQUARE = 180;
    final int OFFSET = 5;
    final int COLS = 8;
    final int ROWS = 8;
    final int gameBoard[][] = {
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 1, 2, 2, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 1, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 1, 1},
            {1, 2, 2, 1, 2, 2, 2, 3},
            {1, 2, 1, 2, 2, 2, 2, 1},
            {1, 1, 1, 1, 1, 1, 1, 1}
    };
    private List<ImageView> allGameObjects;
    private Player player;
    private Zombie zombie;

    //LAYOUT AND INTERACTIVE INFORMATION
    private RelativeLayout activityGameRelativeLayout;
    private ImageView zombieImageView;
    private ImageView playerImageView;
    private TextView winsTextView;
    private TextView lossesTextView;

    private int exitRow;
    private int exitCol;

    //  WINS AND LOSSES
    private int wins;
    private int losses;

    private LayoutInflater layoutInflater;

    /**
     * onCreate is called when the app is started or the context changes ( i.e. the user rotates
     * the device )
     *
     * We set the content view.  All of the View widgets are wired up.
     * The layoutInflate object is instantiated,
     * the allGameObjects list of ImageViews is instantiated,
     * and a gestureDetector is instantiated.
     *
     * startNewGame is called to begin the game.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        activityGameRelativeLayout = (RelativeLayout) findViewById(R.id.activity_game);
        winsTextView = (TextView) findViewById(R.id.winsTextView);
        lossesTextView = (TextView) findViewById(R.id.lossesTextView);

        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        allGameObjects = new ArrayList<>();

        // Instantiate our gesture detector
        gestureDetector = new GestureDetector(this, this);

        startNewGame();

    }

    /**
     * When startNewGame is called, all the views on the game board are removed,
     * and the gameObjects are cleared.
     *
     * buildGameBoard is called, createZombie is called and createPlayer is called.
     *
     * Lastly, the number of wins and losses are set to their respective TextViews.
     *
     */
    private void startNewGame() {
        //TASK 1:  CLEAR THE BOARD (ALL IMAGE VIEWS)
        /*
        for (int i = 0; i < allGameObjects.size(); i++) {
            ImageView visualObj = allGameObjects.get(i);
            activityGameRelativeLayout.removeView(visualObj);
        }
        */
        for (ImageView iv : allGameObjects)
            activityGameRelativeLayout.removeView(iv);
        allGameObjects.clear();

        //TASK 2:  REBUILD THE  BOARD
        buildGameBoard();

        //TASK 3:  ADD THE PLAYERS
        createZombie();
        createPlayer();

        winsTextView.setText(getString(R.string.wins, wins));
        lossesTextView.setText(getString(R.string.losses, losses));
    }

    /**
     * buildGameBoard is called from startNewGame().
     * Using doubly-nested for-loops, we inflate all the obstacle locations, and the exit.
     * If the location is an obstacle, we inflate an obstacle layout.
     * If the location is the exit , we inflate the exit layout, and assign the exitRow/exitCol
     * coordinates.
     *
     * If the view to inflate is present (not null), we set the X and Y of the view.  We add the
     * view to the activityGameRelativeLayout and to the list of allGameObjects.
     */
    private void buildGameBoard() {
        // COMPLETED: Inflate the entire game board (obstacles and exit)
        // COMPLETED: (everything but the player and zombie)

        ImageView viewToInflate;
        // Loop through the board
        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                viewToInflate = null;
                if (gameBoard[row][col] == BoardCodes.OBSTACLE)
                {
                    viewToInflate = (ImageView) layoutInflater.inflate(R.layout.obstacle_layout, null);
                }
                else if (gameBoard[row][col] == BoardCodes.EXIT)
                {
                    viewToInflate = (ImageView) layoutInflater.inflate(R.layout.exit_layout, null);
                    exitRow = row;
                    exitCol = col;
                }

                if (viewToInflate != null)
                {
                    // SET the x and y position of the viewToInflate:
                    viewToInflate.setX(col * SQUARE + OFFSET);
                    viewToInflate.setY(row * SQUARE + OFFSET);
                    // Add the view to the relative layout and list of ImageViews
                    activityGameRelativeLayout.addView(viewToInflate);
                    allGameObjects.add(viewToInflate);
                }
            }
        }
    }

    /**
     * createZombie is called from startNewGame().
     *
     * The position the zombie starts at is defined here.
     * Its layout is inflated.
     * A new Zombie object is instantiated and its position coordinates are set.
     */
    private void createZombie() {
        // TODO: Determine where to place the Zombie (at game start)
        // TODO: Then, inflate the zombie layout
        int row = 2;
        int col = 4;
        zombieImageView = (ImageView) layoutInflater.inflate(R.layout.zombie_layout, null);
        zombieImageView.setX(col * SQUARE + OFFSET);
        zombieImageView.setY(row * SQUARE + OFFSET);
        // Add to relative layout and the list
        activityGameRelativeLayout.addView(zombieImageView);
        allGameObjects.add(zombieImageView);
        zombie = new Zombie();
        zombie.setRow(row);
        zombie.setCol(col);
    }

    /**
     * createPlayer is called from startNewGame().
     *
     * The position the player starts at is defined here.
     * Their layout is inflated.
     * A new Player object is instantiated and their position coordinates are set.
     */
    private void createPlayer() {
        // TODO: Determine where to place the Player (at game start)
        // TODO: Then, inflate the player layout
        int row = 1;
        int col = 1;
        playerImageView = (ImageView) layoutInflater.inflate(R.layout.player_layout, null);
        playerImageView.setX(col * SQUARE + OFFSET);
        playerImageView.setY(row * SQUARE + OFFSET);

        activityGameRelativeLayout.addView(playerImageView);
        allGameObjects.add(playerImageView);
        player = new Player();
        player.setRow(row);
        player.setCol(col);
    }


    /**
     * movePlayer is called from onFling with the x and y velocities of the fling.
     * The absolute values of the velocities are obtained.
     * An if else statement checks which velocity was the greatest and direction
     * is assigned that movement.
     *
     * The player is moved in that direction and their imageView is positioned accordingly.
     * Then the zombie moves towards the player and its imageView is drawn.
     *
     * After the move, we check if the player won, by having the same coordinates as the exit,
     * or lost, by having the same coordinates as the zombie.  The respective counter is then
     * incremented and startNewGame is called.
     * @param velocityX
     * @param velocityY
     */
    private void movePlayer(float velocityX, float velocityY) {
        // TODO: This method gets called by the onFling event
        // TODO: Be sure to implement the move method in the Player (model) class

        float absX = Math.abs(velocityX);
        float absY = Math.abs(velocityY);

        String direction;

        // TODO: Determine which absolute velocity is greater (x or y)
        // x is bigger (move left or right)
        // TODO: If x is negative, move player left.  Else if x is positive, move player right.
        // TODO: If y is negative, move player down.  Else if y is positive, move player up.
        if (absX >= absY)
            direction = (velocityX < 0) ? "LEFT" : "RIGHT";
        else
            direction = (velocityY < 0) ? "UP" : "DOWN";

        if (!direction.equals("UNKNOWN"))
        {
            player.move(gameBoard, direction);
            // Move the image view as well
            playerImageView.setX(player.getCol() * SQUARE + OFFSET);
            playerImageView.setY(player.getRow() * SQUARE + OFFSET);

            // TODO: Then move the zombie, using the player's row and column position.
            zombie.move(gameBoard, player.getCol(), player.getRow());
            zombieImageView.setX(zombie.getCol() * SQUARE + OFFSET);
            zombieImageView.setY(zombie.getRow() * SQUARE + OFFSET);
        }

        // Make 2 decisions:
        // 1) Check to see if Player has reached the exit row and col (WIN)
        // 2) OR if the Player and Zombie are touching (LOSE)
        if ( player.getRow() == exitRow && player.getCol() == exitCol )
        {
            wins++;
            startNewGame();
        }

        if ( player.getRow() == zombie.getRow() && player.getCol() == zombie.getCol() )
        {
            losses++;
            playerImageView = (ImageView) layoutInflater.inflate(R.layout.blood_layout, null);
            playerImageView.setX(player.getCol() * SQUARE + OFFSET);
            playerImageView.setY(player.getRow() * SQUARE + OFFSET);
            activityGameRelativeLayout.addView(playerImageView);
            startNewGame();
        }
    }

    /**
     * onTouchEvent is a necessarily implemented callback method of GestureDetector.OnGestureListener
     * which this class implements.
     *
     * When an onTouchEvent is detected, the detected event is passed into
     * gestureDetector.OnTouchEvent(event) and a boolean is returned.
     * @param event
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * onDown is a necessarily implemented callback method of GestureDetector.OnGestureListener
     * which this class implements.
     *
     * The current implementation of the game does not do anything with this callback method
     * but it must be implemented.
     * @param motionEvent
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    /**
     * onShowPress is a necessarily implemented callback method of GestureDetector.OnGestureListener
     * which this class implements.
     *
     * The current implementation of the game does not do anything with this callback method
     * but it must be implemented.
     * @param motionEvent
     */
    @Override
    public void onShowPress(MotionEvent motionEvent) {}

    /**
     * onSingleTapUp is a necessarily implemented callback method of GestureDetector.OnGestureListener
     * which this class implements.
     *
     * The current implementation of the game does not do anything with this callback method
     * but it must be implemented.
     * @param motionEvent
     */
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    /**
     * onScroll is a necessarily implemented callback method of GestureDetector.OnGestureListener
     * which this class implements.
     *
     * The current implementation of the game does not do anything with this callback method
     * but it must be implemented.
     * @param motionEvent
     * @param motionEvent1
     * @param v
     * @param v1
     */
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) { return false; }

    /**
     * onLongPress is a necessarily implemented callback method of GestureDetector.OnGestureListener
     * which this class implements.
     *
     * The current implementation of the game does not do anything with this callback method
     * but it must be implemented.
     * @param motionEvent
     */
    @Override
    public void onLongPress(MotionEvent motionEvent) {}

    /**
     * onFling is a necessarily implemented callback method of GestureDetector.OnGestureListener
     * which this class implements.
     *
     * When an onFling event is detected, the movePlayer method is called using the
     * velocityX / velocityY arguments, and true is returned.
     * @param motionEvent
     * @param motionEvent1
     * @param velocityX the speed of the User's fling in the X direction
     * @param velocityY the speed of the User's fling in the Y direction
     * @return
     */
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY)
    {
        movePlayer(velocityX, velocityY);
        return true;
    }
}
