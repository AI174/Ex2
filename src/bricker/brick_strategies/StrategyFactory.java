package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Factory class for creating different collision strategies based on predefined rules.
 * @author adan.ir1, hayanat2002
 */
public class StrategyFactory {
    private static final int BASIC = 0;
    private static final int SPECIAL = 1;
    private static final int PUCK = 0;
    private static final int EXTRA_PADDLE = 1;
    private static final int CAMERA = 2;
    private static final int EXTRA_LIFE = 3;
    private static final int DOUBLE = 4;
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
    private final Ball ball;
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
                                   brickerGameManager, Ball ball ) {
        this.gameObjectCollection = gameObjectCollection;
        this.currBricksNumber = currBricksNumber;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowDimensions = windowDimensions;
        this.inputListener = inputListener;
        this.livesCounter = livesCounter;
        this.heartRenderable = imageReader.readImage(Constants.HEART_IMAGE_PATH, true);
        this.paddleRenderable = imageReader.readImage(Constants.PADDLE_IMAGE_PATH, true);
        this.brickerGameManager = brickerGameManager;
        this.ball = ball;
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
            case BASIC:
                collisionStrategy = new BasicCollisionStrategy(gameObjectCollection, currBricksNumber);
                strategyType = StrategyType.BASIC_STRATEGY;
                break;
            case SPECIAL:
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
        int strategyNum = rand.nextInt(5);
        CollisionStrategy collisionStrategy = null;
        StrategyType strategyType = null;
        switch (strategyNum) {
            case PUCK:
                collisionStrategy = new PucksStrategy(gameObjectCollection, imageReader,
                        soundReader, windowDimensions, currBricksNumber);
                strategyType = StrategyType.PUCKS_STRATEGY;
                break;
            case EXTRA_PADDLE:
                collisionStrategy = new ExtraPaddleStrategy(gameObjectCollection,
                        paddleRenderable, inputListener, windowDimensions, currBricksNumber);
                strategyType = StrategyType.EXTRA_PADDLE_STRATEGY;
                break;
            case CAMERA:
                collisionStrategy = new CameraStrategy(gameObjectCollection, windowDimensions,
                        brickerGameManager, currBricksNumber, ball);
                strategyType = StrategyType.CAMERA_STRATEGY;
                break;
            case EXTRA_LIFE:
                collisionStrategy = new ExtraLifeStrategy(gameObjectCollection, heartRenderable,
                        windowDimensions, livesCounter, currBricksNumber);
                strategyType = StrategyType.EXTRA_LIFE_STRATEGY;
                break;
            case DOUBLE:
                collisionStrategy = new DoubleStrategy(this.makeStrategies());
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

    private List<CollisionStrategy> makeStrategies(){
        List<CollisionStrategy> strategies = new ArrayList<>();
        namedStrategy strategy1 = getSpecialRandomStrategy();
        namedStrategy strategy2 = getSpecialRandomStrategy();
        // if there was a third strategy it can't be DoubleStrategy
        namedStrategy strategy3 = getSpecialRandomStrategyWithoutDouble();

        // if both of first and second strategies are not DoubleStrategy -> we don't have a third strategy
        if(strategy1.getStrategyType() != StrategyType.DOUBLE_STRATEGY &&
                strategy2 .getStrategyType() != StrategyType.DOUBLE_STRATEGY){
            strategy3 = null;
        }
        // if both of first and second strategies are DoubleStrategy -> we have 3 non-DoubleStrategy
        else if (strategy1.getStrategyType() == StrategyType.DOUBLE_STRATEGY &&
                strategy2 .getStrategyType() == StrategyType.DOUBLE_STRATEGY) {
            strategy1 = getSpecialRandomStrategyWithoutDouble();
            strategy2 = getSpecialRandomStrategyWithoutDouble();
        }
        else {
            // one of the first/second strategies are DoubleStrategy -> we have 3 non-DoubleStrategy
            if (strategy1.getStrategyType() == StrategyType.DOUBLE_STRATEGY){
                strategy1 = getSpecialRandomStrategyWithoutDouble();
            }
            else {
                strategy2 = getSpecialRandomStrategyWithoutDouble();
            }
        }
        strategies.add(strategy1.getCollisionStrategy());
        strategies.add(strategy2.getCollisionStrategy());
        if(strategy3!= null){
            strategies.add(strategy3.getCollisionStrategy());
        }
        return strategies;
    }
}