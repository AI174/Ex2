package bricker.main;

import bricker.gameobjects.Ball;
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

public class BrickerGameManager extends GameManager{
    public BrickerGameManager(String bouncingBall, Vector2 vector2) {
        super(bouncingBall,vector2);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        // creating ball
         Renderable ballImage = imageReader.readImage("assets/ball.png",true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        GameObject ball = new Ball(Vector2.ZERO,new Vector2(20,20),ballImage,collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(300));
        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(ball);

        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);
        // creating user paddle
        GameObject userPaddle = new Paddle(Vector2.ZERO,new Vector2(100,15),paddleImage,inputListener,windowDimensions);
        userPaddle.setCenter(new Vector2(windowDimensions.x()/2,(int) windowDimensions.y()-30));
        gameObjects().addGameObject(userPaddle);

        // creating ai paddle    *** not sure about the null ***
        GameObject aiPaddle = new Paddle(Vector2.ZERO,new Vector2(100,15),paddleImage, null,windowDimensions);
        aiPaddle.setCenter(new Vector2(windowDimensions.x()/2,30));
        gameObjects().addGameObject(aiPaddle);

        // walls
         makeWalls(windowDimensions);

         // background
         Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg",false);
         GameObject background = new GameObject(Vector2.ZERO,windowDimensions,backgroundImage);
         background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
         gameObjects().addGameObject(background, Layer.BACKGROUND);


    }

    public static void main(String[] args) {
        GameManager g = new BrickerGameManager("Bouncing Ball",new Vector2(700,500));
        g.run();
        // just a test for me
    }

    private void makeWalls(Vector2 windowDimensions){
        // left wall
        GameObject leftWall = new GameObject(new Vector2(0, 10),new Vector2(10, windowDimensions.y()),new RectangleRenderable(Color.MAGENTA));
        gameObjects().addGameObject(leftWall);

        // right wall
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x()-10, 10),new Vector2(10, windowDimensions.y()),new RectangleRenderable(Color.red));
        gameObjects().addGameObject(rightWall);

        // upper wall
        GameObject upWall = new GameObject(Vector2.ZERO,new Vector2(windowDimensions.x(),10),new RectangleRenderable(Color.CYAN));
        gameObjects().addGameObject(upWall);
    }
}

