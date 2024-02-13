package bricker.factories;

import bricker.brick_strategies.*;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class StrategyFactory {
    private final Counter currBricksNumber;
    private Random rand = new Random();
    private GameObjectCollection gameObjectCollection;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private int ballRadius;
    private float ballVelocity;
    private Vector2 windowDimensions;
    private Vector2 topLeftCorner;
    private Vector2 paddleDimensions;
    private Renderable heartRenderable;
    private Renderable paddleRenderable;
    private UserInputListener inputListener;
    private Vector2 heartDimensions;
    private Counter livesCounter;

    public StrategyFactory(GameObjectCollection gameObjectCollection,
                           ImageReader imageReader, SoundReader soundReader, int ballRadius,
                           float ballVelocity, Vector2 windowDimensions,
                           Vector2 topLeftCorner, Vector2 paddleDimensions,
                           Renderable heartRenderable, Renderable paddleRenderable, UserInputListener inputListener,
                           Vector2 heartDimensions, Counter livesCounter, Counter currBricksNumber) {
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.ballRadius = ballRadius;
        this.ballVelocity = ballVelocity;
        this.windowDimensions = windowDimensions;
        this.topLeftCorner = topLeftCorner;
        this.paddleDimensions = paddleDimensions;
        this.heartRenderable = heartRenderable;
        this.paddleRenderable = paddleRenderable;
        this.inputListener = inputListener;
        this.heartDimensions = heartDimensions;
        this.livesCounter = livesCounter;
        this.currBricksNumber = currBricksNumber;
    }

    public namedStrategy getRandomStrategy() {
        int strategyNum = rand.nextInt(2);
        CollisionStrategy collisionStrategy = null;
        StrategyType strategyType = null;
        switch (strategyNum) {
            case 0:
                collisionStrategy = new BasicCollisionStrategy(gameObjectCollection,currBricksNumber);
                strategyType = StrategyType.BASIC_STRATEGY;
                break;
            case 1:
                namedStrategy myNamedStrategy = getSpecialRandomStrategy();
                collisionStrategy = myNamedStrategy.getCollisionStrategy();
                strategyType = myNamedStrategy.getStrategyType();
                break;
        }
        return new namedStrategy(collisionStrategy, strategyType);
    }

    public namedStrategy getSpecialRandomStrategy() {
        int strategyNum = rand.nextInt(4); ///// 5 not 4
        CollisionStrategy collisionStrategy = null;
        StrategyType strategyType = null;
        switch (strategyNum) {
            case 0: // puck
                collisionStrategy = new PucksStrategy(gameObjectCollection, imageReader, soundReader, ballRadius, ballVelocity, windowDimensions, currBricksNumber);
                strategyType = StrategyType.PUCKS_STRATEGY;
                break;
            case 1:  // paddle
                collisionStrategy = new ExtraPaddleStrategy(gameObjectCollection, topLeftCorner, paddleDimensions, paddleRenderable, inputListener, windowDimensions, currBricksNumber);
                strategyType = StrategyType.EXTRA_PADDLE_STRATEGY;
                break;
//            case 2:  // camera
//                collisionStrategy = new CameraStrategy();
//                strategyType = StrategyType.CAMERA_STRATEGY;
//                break;
            case 2:   // heart CASE 3
                collisionStrategy = new ExtraLifeStrategy(gameObjectCollection, topLeftCorner, heartDimensions, heartRenderable, inputListener, windowDimensions, livesCounter, currBricksNumber);
                strategyType = StrategyType.EXTRA_LIFE_STRATEGY;
                break;
            case 3:    // double CASE 4
                collisionStrategy = new DoubleStrategy(gameObjectCollection, this, currBricksNumber);
                strategyType = StrategyType.DOUBLE_STRATEGY;
                break;
        }
        return new namedStrategy(collisionStrategy, strategyType);
    }

    public namedStrategy getSpecialRandomStrategyWithoutDouble() {
        int strategyNum = rand.nextInt(3); /// 4 not 3
        CollisionStrategy collisionStrategy = null;
        StrategyType strategyType = null;
        switch (strategyNum) {
            case 0: // puck
                collisionStrategy = new PucksStrategy(gameObjectCollection, imageReader, soundReader, ballRadius, ballVelocity, windowDimensions, currBricksNumber);
                strategyType = StrategyType.PUCKS_STRATEGY;
                break;
            case 1:  // paddle
                collisionStrategy = new ExtraPaddleStrategy(gameObjectCollection, topLeftCorner, paddleDimensions, paddleRenderable, inputListener, windowDimensions, currBricksNumber);
                strategyType = StrategyType.EXTRA_PADDLE_STRATEGY;
                break;
//            case 2:  // camera
//                collisionStrategy = new CameraStrategy();
//                strategyType = StrategyType.CAMERA_STRATEGY;
//                break;
            case 2:   // heart CASE 3
                collisionStrategy = new ExtraLifeStrategy(gameObjectCollection, topLeftCorner, heartDimensions, heartRenderable, inputListener, windowDimensions, livesCounter, currBricksNumber);
                strategyType = StrategyType.EXTRA_LIFE_STRATEGY;
                break;
        }
        return new namedStrategy(collisionStrategy, strategyType);
    }

    // if u want to try stuff :)
//    public CollisionStrategy getStrategy(StrategyType type){
//        switch (type){
//            case PUCKS_STRATEGY: // puck
//                return new PucksStrategy(gameObjectCollection, imageReader,soundReader,ballRadius,ballVelocity,windowDimensions,currBricksNumber);
//            case EXTRA_PADDLE_STRATEGY:  // paddle
//                return new ExtraPaddleStrategy(gameObjectCollection, topLeftCorner, paddleDimensions,paddleRenderable, inputListener, windowDimensions,currBricksNumber);
////            case 2:  // camera
////                collisionStrategy = new CameraStrategy();
////                strategyType = StrategyType.CAMERA_STRATEGY;
////                break;
//            case EXTRA_LIFE_STRATEGY:   // heart CASE 3
//                return new  ExtraLifeStrategy(gameObjectCollection, topLeftCorner,heartDimensions, heartRenderable, inputListener, windowDimensions,  livesCounter,currBricksNumber);
//        }
//        return null;
//    }
//
}
