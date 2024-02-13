package bricker.factories;

import bricker.brick_strategies.*;
import bricker.main.BrickerGameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class StrategyFactory {
    private final Random rand = new Random();
    private final GameObjectCollection gameObjectCollection;
    private final Counter currBricksNumber;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final Vector2 windowDimensions;
    private final Renderable paddleRenderable;
    private final UserInputListener inputListener;
    private final Renderable heartRenderable;
    private final Counter livesCounter;
    private BrickerGameManager brickerGameManager;

    public StrategyFactory(GameObjectCollection gameObjectCollection, Counter currBricksNumber,
                           ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions,
                           UserInputListener inputListener, Counter livesCounter,BrickerGameManager
                                   brickerGameManager ) {
        this.gameObjectCollection = gameObjectCollection;
        this.currBricksNumber = currBricksNumber;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowDimensions = windowDimensions;
        this.inputListener = inputListener;
        this.livesCounter = livesCounter;
        this.heartRenderable = imageReader.readImage("assets/heart.png", true);
        this.paddleRenderable = imageReader.readImage("assets/paddle.png", true);
        this.brickerGameManager = brickerGameManager;
    }

    public namedStrategy getRandomStrategy() {
        int strategyNum = rand.nextInt(2);
        CollisionStrategy collisionStrategy = null;
        StrategyType strategyType = null;
        switch (strategyNum) {
            case 0:
                collisionStrategy = new BasicCollisionStrategy(gameObjectCollection, currBricksNumber);
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
        int strategyNum = rand.nextInt(5); ///// 5 not 4
        CollisionStrategy collisionStrategy = null;
        StrategyType strategyType = null;
        switch (strategyNum) {
            case 0: // puck
                collisionStrategy = new PucksStrategy(gameObjectCollection, imageReader, soundReader, windowDimensions, currBricksNumber);
                strategyType = StrategyType.PUCKS_STRATEGY;
                break;
            case 1:  // paddle
                collisionStrategy = new ExtraPaddleStrategy(gameObjectCollection, paddleRenderable, inputListener, windowDimensions, currBricksNumber);
                strategyType = StrategyType.EXTRA_PADDLE_STRATEGY;
                break;
            case 2:  // camera
                collisionStrategy = new CameraStrategy(gameObjectCollection, windowDimensions,
                        brickerGameManager, currBricksNumber);
                strategyType = StrategyType.CAMERA_STRATEGY;
                break;
            case 3:   // heart CASE 3
                collisionStrategy = new ExtraLifeStrategy(gameObjectCollection, heartRenderable, windowDimensions, livesCounter, currBricksNumber);
                strategyType = StrategyType.EXTRA_LIFE_STRATEGY;
                break;
            case 4:    // double CASE 4
                collisionStrategy = new DoubleStrategy(gameObjectCollection, this, currBricksNumber);
                strategyType = StrategyType.DOUBLE_STRATEGY;
                break;
        }
        return new namedStrategy(collisionStrategy, strategyType);
    }

    public namedStrategy getSpecialRandomStrategyWithoutDouble() {
        namedStrategy myNamedStrategy = getSpecialRandomStrategy();
        while (myNamedStrategy.getStrategyType() == StrategyType.DOUBLE_STRATEGY) {
            myNamedStrategy = getSpecialRandomStrategy();
        }
        return new namedStrategy(myNamedStrategy.getCollisionStrategy(), myNamedStrategy.getStrategyType());
    }
}