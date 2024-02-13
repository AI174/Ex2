package bricker.main;

import bricker.brick_strategies.*;
import bricker.factories.StrategyFactory;
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

public class BrickerGameManager extends GameManager{
    private static final int START_HEARTS_NUMBER = 3;  // made this public
    private static final int START_BRICK_NUMBER = 0;
    private static final int BORDER_WIDTH = 10 ;
    private static final int PADDLE_HEIGHT = 15;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 20;
    private static final float BALL_SPEED = 200;
    private static final int BRICK_HEIGHT = 15 ;
    private static final int SPACE_BETWEEN_BRICKS = 3;
    private static final int SPACE_BETWEEN_HEARTS = 4;
    private static final int BORDER_SPACE = 1;
    private static final int COUNTERS_SPACE = 5;
    private static final int SPACE_BETWEEN_GRAPHIC_NUMERIC = 10;
    private static final int NUMERIC_COUNTER_SIZE = 15;
    private static final int HEART_SIZE = 15;
    private static int bricksPerRow = 8;
    private static int bricksRows = 7;
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private UserInputListener inputListener;
    private Counter livesCounter;
    private Counter currBricksNumber;

    Renderable heartImage;
    Renderable paddleImage;


    public BrickerGameManager(String bouncingBall, Vector2 vector2) {
        super(bouncingBall,vector2);
    }

    public static void main(String[] args) {
        if(args.length == 2){
            bricksPerRow = Integer.parseInt(args[0]);
            bricksRows = Integer.parseInt(args[1]);
        }
        GameManager gameManager = new BrickerGameManager("Bouncing Ball",new Vector2(700,500));
        gameManager.run();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // initialize
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.windowDimensions = windowController.getWindowDimensions();
        this.livesCounter = new Counter(START_HEARTS_NUMBER);
        this.currBricksNumber = new Counter(START_BRICK_NUMBER);
        heartImage = imageReader.readImage("assets/heart.png",true);
        paddleImage = imageReader.readImage("assets/paddle.png",true);

        // creating ball
        createBall(imageReader, soundReader);

        // creating paddles
        createPaddles(imageReader, inputListener);

        // creating walls
        makeWalls();

        // background
        createBackground(imageReader);

        // creating bricks
        createBricks(imageReader,soundReader);

        // creating numeric lives counter
        createNumericCounter();

        // creating Hearts Graphic
        createGraphicHearts(imageReader);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    private void createBall(ImageReader imageReader, SoundReader soundReader){

        Renderable ballImage = imageReader.readImage("assets/ball.png",true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");

        ball = new Ball(Vector2.ZERO,new Vector2(BALL_RADIUS,BALL_RADIUS),ballImage,collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(BALL_SPEED));
        gameObjects().addGameObject(ball);
        recenterBall();

    }

    private void createPaddles(ImageReader imageReader, UserInputListener inputListener){

        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);

        // creating user paddle
        GameObject userPaddle = new Paddle(Vector2.ZERO,new Vector2(PADDLE_WIDTH,PADDLE_HEIGHT),paddleImage,inputListener,windowDimensions);
        userPaddle.setTag("PADDLE");
        userPaddle.setCenter(new Vector2(windowDimensions.x()/2,(int) windowDimensions.y()-30));
        gameObjects().addGameObject(userPaddle);

    }

    private void makeWalls(){

        // left wall
        GameObject leftWall = new GameObject(new Vector2(0, BORDER_WIDTH),new Vector2(BORDER_WIDTH, windowDimensions.y()),null);
        gameObjects().addGameObject(leftWall);

        // right wall
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x()-BORDER_WIDTH, BORDER_WIDTH),new Vector2(BORDER_WIDTH, windowDimensions.y()),null);
        gameObjects().addGameObject(rightWall);

        // upper wall
        GameObject upWall = new GameObject(Vector2.ZERO,new Vector2(windowDimensions.x(),BORDER_WIDTH),null);
        gameObjects().addGameObject(upWall);
    }
    private void createBackground(ImageReader imageReader){

        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg",false);

        GameObject background = new GameObject(Vector2.ZERO,windowDimensions,backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createBricks(ImageReader imageReader,SoundReader soundReader){
        StrategyFactory strategyFactory =new StrategyFactory(gameObjects(),
                imageReader,soundReader,BALL_RADIUS,BALL_SPEED,windowDimensions,
                Vector2.ZERO,new Vector2(PADDLE_WIDTH,PADDLE_HEIGHT),
                heartImage,paddleImage,inputListener,new Vector2(HEART_SIZE, HEART_SIZE),
                livesCounter,currBricksNumber);

        Renderable brickImage = imageReader.readImage("assets/brick.png",false);

        float brickLength = (windowDimensions.x() - (2* BORDER_WIDTH)) / bricksPerRow;
        brickLength -= SPACE_BETWEEN_BRICKS;
        int start = BORDER_WIDTH + BORDER_SPACE;

        for (int i = 0; i < bricksRows; i++) {
            for (int j = 0; j < bricksPerRow; j++){
                CollisionStrategy strategy = strategyFactory.getRandomStrategy().getCollisionStrategy();
                GameObject brick = new Brick(Vector2.ZERO, new Vector2(brickLength, BRICK_HEIGHT),
                        brickImage,strategy,currBricksNumber);

                brick.setTopLeftCorner(new Vector2(start + ((brickLength + SPACE_BETWEEN_BRICKS) * j)
                        , start + ((BRICK_HEIGHT + SPACE_BETWEEN_BRICKS) * i) ));
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }

        }
    }

    private void createNumericCounter(){
        TextRenderable textRenderable = new TextRenderable(Integer.toString(livesCounter.value()));
        GameObject numericCounter = new NumericCounter(Vector2.ZERO,
                new Vector2(NUMERIC_COUNTER_SIZE, NUMERIC_COUNTER_SIZE), null, textRenderable,livesCounter);
        numericCounter.setTopLeftCorner(new Vector2(BORDER_WIDTH + COUNTERS_SPACE, windowDimensions.y() - COUNTERS_SPACE - NUMERIC_COUNTER_SIZE));
        gameObjects().addGameObject(numericCounter, Layer.UI);
    }

    private void createGraphicHearts(ImageReader imageReader) {
        Renderable heartImage = imageReader.readImage("assets/heart.png",true);
        int startX = BORDER_WIDTH + COUNTERS_SPACE + NUMERIC_COUNTER_SIZE  +  SPACE_BETWEEN_GRAPHIC_NUMERIC;
        int startY = (int)windowDimensions.y() - COUNTERS_SPACE - HEART_SIZE;
        int[] constants = {HEART_SIZE,START_HEARTS_NUMBER, SPACE_BETWEEN_HEARTS, startX, startY};
        GraphicCounter hearts = new GraphicCounter(Vector2.ZERO, new Vector2(HEART_SIZE, HEART_SIZE),
                heartImage, windowDimensions,gameObjects(), livesCounter, constants);
        gameObjects().addGameObject(hearts , Layer.UI);
    }


    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        boolean isPressedW = inputListener.isKeyPressed(KeyEvent.VK_W);
        String prompt = "";
        if(currBricksNumber.value() <= 0 || isPressedW ){ // finished all the bricks
            prompt = "You win!";
        }
        else if(ballHeight > windowDimensions.y()) { //we lost a soul or lost the game
            livesCounter.increaseBy(-1);   //livesCounter--;
            recenterBall();
            if(livesCounter.value() <= 0){      // lost the game -> update the message
                prompt = "You Lose!";
            }
        }
        if(!prompt.isEmpty()) {
            prompt += " Play again?";
            if(windowController.openYesNoDialog(prompt))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }
    }

    private void recenterBall(){
        ball.setCenter(windowDimensions.mult(0.5f));
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
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