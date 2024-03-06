package bricker.main;

import bricker.brick_strategies.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;
import java.util.Random;


/**
 * The manager of the Arkanoid game.
 * @author adan.ir1, hayanat2002
 */
public class BrickerGameManager extends GameManager{
    private static int bricksPerRow;
    private static int bricksRows;
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private UserInputListener inputListener;
    private Counter livesCounter;
    private Counter currBricksNumber;

    Renderable heartImage;
    Renderable paddleImage;

    /**
     * Constructor
     * @param bouncingBall The name of the game.
     * @param vector2 The dimensions of the Screen.
     */
    public BrickerGameManager(String bouncingBall, Vector2 vector2) {
        super(bouncingBall,vector2);
    }

    /**
     * The main function, that runs the game
     * @param args an array that contains the information about the bricks:
     *            arg[0]: the bricksPerRow, arg[1]: the bricksRows
     */
    public static void main(String[] args) {
        bricksPerRow = Constants.DEFAULT_BRICKS_PER_ROW;
        bricksRows = Constants.DEFAULT_BRICKS_ROW;
        if(args.length == 2){
            bricksPerRow = Integer.parseInt(args[0]);
            bricksRows = Integer.parseInt(args[1]);
        }
        GameManager gameManager = new BrickerGameManager(Constants.GAME_NAME,
                new Vector2(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT));
        gameManager.run();
    }

    /**
     * initializes the Game, and builds some objects.
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // initialize
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.windowDimensions = windowController.getWindowDimensions();
        this.livesCounter = new Counter(Constants.START_HEARTS_NUMBER);
        this.currBricksNumber = new Counter(Constants.START_BRICK_NUMBER);
        heartImage = imageReader.readImage(Constants.HEART_IMAGE_PATH,true);
        paddleImage = imageReader.readImage(Constants.PADDLE_IMAGE_PATH,true);


        createBall(imageReader, soundReader);// creating ball
        createPaddles(imageReader, inputListener);// creating paddles
        makeWalls();// creating walls
        createBackground(imageReader);// background
        createBricks(imageReader,soundReader);// creating bricks
        createNumericCounter();// creating numeric lives counter
        createGraphicHearts(imageReader);// creating Hearts Graphic
    }

    /**
     * updates the game, calls the relevant methods.
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updateCamera();
        checkForGameEnd();
    }

    private void createBall(ImageReader imageReader, SoundReader soundReader){

        Renderable ballImage = imageReader.readImage(Constants.BALL_IMAGE_PATH,true);
        Sound collisionSound = soundReader.readSound(Constants.COLLISION_SOUND_PATH);

        ball = new Ball(ballImage,collisionSound);
        ball.setTag(Constants.MAIN_BALL_TAG);
        ball.setVelocity(Vector2.DOWN.mult(Constants.BALL_SPEED));
        gameObjects().addGameObject(ball);
        recenterBall();
    }

    private void createPaddles(ImageReader imageReader, UserInputListener inputListener){

        Renderable paddleImage = imageReader.readImage(Constants.PADDLE_IMAGE_PATH,true);
        GameObject userPaddle = new Paddle(paddleImage,inputListener,windowDimensions);
        userPaddle.setTag(Constants.MAIN_PADDLE_TAG);
        userPaddle.setCenter(new Vector2(windowDimensions.x()/2,
                (int) windowDimensions.y()- Constants.SPACE_BETWEEN_PADDLE_AND_BOTTOM));
        gameObjects().addGameObject(userPaddle);

    }

    private void makeWalls(){

        // left wall
        GameObject leftWall = new GameObject(new Vector2(0, Constants.BORDER_WIDTH),
                new Vector2(Constants.BORDER_WIDTH, windowDimensions.y()),null);
        gameObjects().addGameObject(leftWall);

        // right wall
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x()-Constants.BORDER_WIDTH,
                Constants.BORDER_WIDTH),new Vector2(Constants.BORDER_WIDTH,
                windowDimensions.y()),null);
        gameObjects().addGameObject(rightWall);

        // upper wall
        GameObject upWall = new GameObject(Vector2.ZERO,new Vector2(windowDimensions.x(),
                Constants.BORDER_WIDTH),null);
        gameObjects().addGameObject(upWall);
    }
    private void createBackground(ImageReader imageReader){

        Renderable backgroundImage = imageReader.readImage(Constants.BACKGROUND_IMAGE_PATH,false);

        GameObject background = new GameObject(Vector2.ZERO,windowDimensions,backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createBricks(ImageReader imageReader,SoundReader soundReader){
        StrategyFactory strategyFactory =new StrategyFactory(gameObjects(),
                currBricksNumber,imageReader,soundReader,windowDimensions,inputListener,livesCounter,
                this, this.ball);

        Renderable brickImage = imageReader.readImage(Constants.BRICK_IMAGE_PATH,false);

        float brickLength = (windowDimensions.x() - (2* Constants.BORDER_WIDTH)) / bricksPerRow;
        brickLength -= Constants.SPACE_BETWEEN_BRICKS;
        int start = Constants.BORDER_WIDTH + Constants.BORDER_SPACE;

        for (int i = 0; i < bricksRows; i++) {
            for (int j = 0; j < bricksPerRow; j++){
                CollisionStrategy strategy = strategyFactory.getRandomStrategy().getCollisionStrategy();
                GameObject brick = new Brick(new Vector2(brickLength, Constants.BRICK_HEIGHT),
                        brickImage,strategy,currBricksNumber);

                brick.setTopLeftCorner(new Vector2(start + ((brickLength +
                        Constants.SPACE_BETWEEN_BRICKS) * j), start +
                        ((Constants.BRICK_HEIGHT + Constants.SPACE_BETWEEN_BRICKS) * i) ));
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }

        }
    }

    private void createNumericCounter(){
        TextRenderable textRenderable = new TextRenderable(Integer.toString(livesCounter.value()));
        GameObject numericCounter = new NumericCounter(textRenderable,livesCounter);
        numericCounter.setTopLeftCorner(new Vector2(Constants.BORDER_WIDTH +Constants. COUNTERS_SPACE,
                windowDimensions.y() - Constants.COUNTERS_SPACE - Constants.NUMERIC_COUNTER_SIZE));
        gameObjects().addGameObject(numericCounter, Layer.UI);
    }

    private void createGraphicHearts(ImageReader imageReader) {
        Renderable heartImage = imageReader.readImage(Constants.HEART_IMAGE_PATH,true);
        GraphicCounter hearts = new GraphicCounter(heartImage,windowDimensions,gameObjects(),livesCounter);
        gameObjects().addGameObject(hearts , Layer.UI);
    }

    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        boolean isPressedW = inputListener.isKeyPressed(KeyEvent.VK_W);
        String prompt = "";
        if(currBricksNumber.value() <= 0 || isPressedW ){ // finished all the bricks
            prompt = Constants.WINNING_PROMPT;
        }
        else if(ballHeight > windowDimensions.y()) { //we lost a soul or lost the game
            livesCounter.increaseBy(-1);   //livesCounter--;
            recenterBall();
            if(livesCounter.value() <= 0){      // lost the game -> update the message
                prompt = Constants.LOOSING_PROMPT;
            }
        }
        if(!prompt.isEmpty()) {
            prompt += Constants.QUESTION_PROMPT;
            if(windowController.openYesNoDialog(prompt))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }
    }

    private void updateCamera(){
        if (ball.getCollisionCounter() > Constants.COLLISIONS_TO_RESET_CAMERA){
            ball.resetBallCounter();
            setCamera(null);
        }

    }
    private void recenterBall(){
        ball.setCenter(windowDimensions.mult(0.5f));
        float ballVelX = Constants.BALL_SPEED;
        float ballVelY = Constants.BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean()){
            ballVelX *= -1;
        }
        if(rand.nextBoolean()){
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX , ballVelY));
    }

}