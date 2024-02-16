package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.Random;
/**
 * Factory class for creating different collision strategies based on predefined rules.
 * @author adan.ir1, hayanat2002
 */
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
    private final BrickerGameManager brickerGameManager;
    /**
     * Constructs a new StrategyFactory with the specified parameters.
     * @param gameObjectCollection The collection of game objects.
     * @param currBricksNumber The counter tracking the current number of bricks.
     * @param imageReader The image reader used to load images.
     * @param soundReader The sound reader used to load sounds.
     * @param windowDimensions The dimensions of the game window.
     * @param inputListener The user input listener for handling player input.
     * @param livesCounter The counter tracking the player's remaining lives.
     * @param brickerGameManager The game manager responsible for managing game state.
     */
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
    /**
     * Generates a random strategy (the probability of getting a basic strategy is 0.5
     * and for special strategy is 0.5 - 1/10 for each of the special strategies).
     * @return A named strategy representing the randomly generated collision strategy.
     */
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
    /**
     * Generates a special random strategy based on predefined rules.
     * @return A named strategy representing the special randomly generated collision strategy.
     */
    public namedStrategy getSpecialRandomStrategy() {
        int strategyNum = rand.nextInt(5); ///// 5 not 4
        CollisionStrategy collisionStrategy = null;
        StrategyType strategyType = null;
        switch (strategyNum) {
            case 0: // puck
                collisionStrategy = new PucksStrategy(gameObjectCollection, imageReader,
                        soundReader, windowDimensions, currBricksNumber);
                strategyType = StrategyType.PUCKS_STRATEGY;
                break;
            case 1:  // paddle
                collisionStrategy = new ExtraPaddleStrategy(gameObjectCollection,
                        paddleRenderable, inputListener, windowDimensions, currBricksNumber);
                strategyType = StrategyType.EXTRA_PADDLE_STRATEGY;
                break;
            case 2:  // camera
                collisionStrategy = new CameraStrategy(gameObjectCollection, windowDimensions,
                        brickerGameManager, currBricksNumber);
                strategyType = StrategyType.CAMERA_STRATEGY;
                break;
            case 3:   // heart CASE 3
                collisionStrategy = new ExtraLifeStrategy(gameObjectCollection, heartRenderable,
                        windowDimensions, livesCounter, currBricksNumber);
                strategyType = StrategyType.EXTRA_LIFE_STRATEGY;
                break;
            case 4:    // double CASE 4
                collisionStrategy = new DoubleStrategy(gameObjectCollection, this,
                        currBricksNumber);
                strategyType = StrategyType.DOUBLE_STRATEGY;
                break;
        }
        return new namedStrategy(collisionStrategy, strategyType);
    }
    /**
     * Generates a special random strategy without allowing the DoubleStrategy to be included.
     * @return A named strategy representing the special randomly generated collision strategy.
     */
    public namedStrategy getSpecialRandomStrategyWithoutDouble() {
        namedStrategy myNamedStrategy = getSpecialRandomStrategy();
        while (myNamedStrategy.getStrategyType() == StrategyType.DOUBLE_STRATEGY) {
            myNamedStrategy = getSpecialRandomStrategy();
        }
        return new namedStrategy(myNamedStrategy.getCollisionStrategy(), myNamedStrategy.getStrategyType());
    }
}