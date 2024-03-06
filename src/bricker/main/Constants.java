package bricker.main;

/**
 * Represents a collection of constants used in the application.
 * @author adan.ir1, hayanat2002
 */
public class Constants {
    private Constants(){}
    /**
     * The initial number of hearts at the start of the game.
     */
    public static final int START_HEARTS_NUMBER = 3;
    /**
     * The initial number of bricks at the start of the game.
     */
    public static final int START_BRICK_NUMBER = 0;
    /**
     * The width of the border surrounding the game area.
     */
    public static final int BORDER_WIDTH = 10 ;
    /**
     * The height of the paddle used by the player.
     */
    public static final int PADDLE_HEIGHT = 15;
    /**
     * The width of the paddle used by the player.
     */
    public static final int PADDLE_WIDTH = 100;
    /**
     * The radius of the ball used in the game.
     */
    public static final int BALL_RADIUS = 20;
    /**
     * The speed of the ball.
     */
    public static final float BALL_SPEED = 150;
    /**
     * The height of a brick in the game.
     */
    public static final int BRICK_HEIGHT = 15 ;
    /**
     * The space between bricks.
     */
    public static final int SPACE_BETWEEN_BRICKS = 3;
    /**
     * The space between heart icons displayed on the screen.
     */
    public static final int SPACE_BETWEEN_HEARTS = 4;
    /**
     * The space between the border and the counters.
     */
    public static final int BORDER_SPACE = 1;
    /**
     * The space between the counters icons.
     */
    public static final int COUNTERS_SPACE = 5;
    /**
     * The space between the graphic counter and numeric counter.
     */
    public static final int SPACE_BETWEEN_GRAPHIC_NUMERIC = 10;
    /**
     * The size of numeric counters displayed within the game interface.
     */
    public static final int NUMERIC_COUNTER_SIZE = 15;
    /**
     * The size of heart icons representing player lives.
     */
    public static final int HEART_SIZE = 15;
    /**
     * The default number of bricks per row in the game layout.
     */
    public static int DEFAULT_BRICKS_PER_ROW = 8;
    /**
     * The default number of rows containing bricks in the game layout.
     */
    public static int DEFAULT_BRICKS_ROW = 7;
    /**
     * The maximum number of additional collisions allowed with the extra paddle.
     */
    public static final int MAX_EXTRA_PADDLE_COLLISION = 4;
    /**
     * The heart speed.
     */
    public static final float HEART_SPEED = 100;
    /**
     * The number pucks per brick.
     */
    public static final int PUCKS_NUMBER = 2;
    /**
     * The  factor applied to adjust the size of puck objects.
     */
    public static final float PUCK_SIZE_FACTOR = (float) 0.75;
    /**
     * The number of collisions required to reset the camera position.
     */
    public static final int COLLISIONS_TO_RESET_CAMERA = 4;
    /**
     * Represents the width of the game screen.
     */
    public static final int SCREEN_WIDTH = 700;
    /**
     * Represents the height of the game screen.
     */
    public static final int SCREEN_HEIGHT = 500;
    /**
     * Represents the name of the game.
     */
    public static final String GAME_NAME = "Bouncing Ball";
    /**
     * Represents the file path to the image of the ball.
     */
    public static final String BALL_IMAGE_PATH = "assets/ball.png";
    /**
     * Represents the file path to the collision sound.
     */
    public static final String COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
    /**
     * Represents the file path to the image of the paddle.
     */
    public static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    /**
     * Represents the file path to the background image.
     */
    public static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";
    /**
     * Represents the file path to the image of the brick.
     */
    public static final String BRICK_IMAGE_PATH = "assets/brick.png";
    /**
     * Represents the file path to the image of the heart.
     */
    public static final String HEART_IMAGE_PATH = "assets/heart.png";
    /**
     * Represents the file path to the image of the puck.
     */
    public static final String PUCK_IMAGE_PATH = "assets/mockBall.png";
    /**
     * Represents the main ball tag
     */
    public static final String MAIN_BALL_TAG = "MAIN_BALL";
    /**
     * Represents the main paddle tag
     */
    public static final String MAIN_PADDLE_TAG = "PADDLE";
    /**
     * Represents the extra paddle tag
     */
    public static final String EXTRA_PADDLE_TAG = "EXTRA_PADDLE";
    /**
     * Represents the moving heart tag
     */
    public static final String MOVING_HEART_TAG = "MOVING_HEART";
    /**
     * the winning prompt to show when wining
     */
    public static final String WINNING_PROMPT = "You win!";
    /**
     * the loosing prompt to show when loose
     */
    public static final String LOOSING_PROMPT = "You Lose!";
    /**
     * the question prompt to show after winning/ loosing
     */
    public static final String QUESTION_PROMPT = " Play again?";
    /**
     * set the numeric counter to green if number is greater or equal to this
     */
    public static final int NUMERIC_COUNTER_GREEN = 3;
    /**
     * set the numeric counter to yellow if number is equal to this
     */
    public static final int NUMERIC_COUNTER_YELLOW = 2;
    /**
     * Represents the space between the main paddle and the screen bottom
     */
    public static final int SPACE_BETWEEN_PADDLE_AND_BOTTOM = 30;
    /**
     * Represents the Maximum lives in the game.
     */
    public static final int MAX_LIVES = 4;
    /**
     * Represents the Paddle speed.
     */
    public static final float PADDLE_MOVEMENT_SPEED = 300;


}