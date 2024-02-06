package bricker.main;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Random;

public class BrickerGameManager extends GameManager{
    private static final float SPACE_BETWEEN_BRICKS = 3;
    private static int bricksRows = 7;
    private static int bricksPerRow = 8;
    private static final int BRICK_HEIGHT = 15 ;
    private static final int BORDER_WIDTH = 10 ;
    private static final int PADDLE_HEIGHT = 15;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 20;
    private static final float BALL_SPEED = 100;
    public BrickerGameManager(String bouncingBall, Vector2 vector2) {
        super(bouncingBall,vector2);
    }

    public static void main(String[] args) {
        if(args.length == 2){
            bricksPerRow = Integer.parseInt(args[0]);
            bricksRows = Integer.parseInt(args[1]);
        }
        GameManager g = new BrickerGameManager("Bouncing Ball",new Vector2(700,500));
        g.run();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // creating ball
        createBall(imageReader, soundReader, windowController);

        // creating paddles
        createPaddles(imageReader, inputListener, windowController);

        // creating walls
         makeWalls(windowController);

         // background
         createBackground(imageReader, windowController);

         // creating bricks
        createBricks(imageReader, windowController);

    }

    private void createBall(ImageReader imageReader, SoundReader soundReader,
                            WindowController windowController){

        Renderable ballImage = imageReader.readImage("assets/ball.png",true);
        Vector2 windowDimensions = windowController.getWindowDimensions();

        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        GameObject ball = new Ball(Vector2.ZERO,new Vector2(BALL_RADIUS,BALL_RADIUS),ballImage,collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(BALL_SPEED));
        ball.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(ball);

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

    private void createPaddles(ImageReader imageReader, UserInputListener inputListener,
                               WindowController windowController){

        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);
        Vector2 windowDimensions = windowController.getWindowDimensions();

        // creating user paddle
        GameObject userPaddle = new Paddle(Vector2.ZERO,new Vector2(PADDLE_WIDTH,PADDLE_HEIGHT),paddleImage,inputListener,windowDimensions);
        userPaddle.setCenter(new Vector2(windowDimensions.x()/2,(int) windowDimensions.y()-30));
        gameObjects().addGameObject(userPaddle);

    }

    private void makeWalls(WindowController windowController){

        Vector2 windowDimensions = windowController.getWindowDimensions();
        // left wall
        GameObject leftWall = new GameObject(new Vector2(0, BORDER_WIDTH),new Vector2(BORDER_WIDTH, windowDimensions.y()),new RectangleRenderable(Color.MAGENTA));
        gameObjects().addGameObject(leftWall);

        // right wall
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x()-BORDER_WIDTH, BORDER_WIDTH),new Vector2(BORDER_WIDTH, windowDimensions.y()),new RectangleRenderable(Color.RED));
        gameObjects().addGameObject(rightWall);

        // upper wall
        GameObject upWall = new GameObject(Vector2.ZERO,new Vector2(windowDimensions.x(),BORDER_WIDTH),new RectangleRenderable(Color.CYAN));
        gameObjects().addGameObject(upWall);
    }
    private void createBackground(ImageReader imageReader, WindowController windowController){

        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg",false);
        Vector2 windowDimensions = windowController.getWindowDimensions();

        GameObject background = new GameObject(Vector2.ZERO,windowDimensions,backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createBricks(ImageReader imageReader, WindowController windowController){

        Renderable brickImage = imageReader.readImage("assets/brick.png",false);
        Vector2 windowDimensions = windowController.getWindowDimensions();

        float brickLength = (windowDimensions.x() - (2* BORDER_WIDTH)) / bricksPerRow;
        brickLength -= SPACE_BETWEEN_BRICKS;

        for (int i = 0; i < bricksRows; i++) {
            for (int j = 0; j < bricksPerRow; j++){
                GameObject brick = new Brick(Vector2.ZERO, new Vector2(brickLength, BRICK_HEIGHT),
                        brickImage, new BasicCollisionStrategy(gameObjects()));
                brick.setTopLeftCorner(new Vector2(BORDER_WIDTH + ((brickLength + SPACE_BETWEEN_BRICKS) * j)
                        , BORDER_WIDTH + ((BRICK_HEIGHT + SPACE_BETWEEN_BRICKS) * i) ));
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }

        }
    }
}

