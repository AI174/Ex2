package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * The camera Strategy class, when it happened it creates an extra paddle and keep it until it hits 4 other
 * default game objects.
 * @author adan.ir1, hayanat2002
 */
public class CameraStrategy extends BasicCollisionStrategy{
    private final Ball ball;
    private final Vector2 windowDimensions;
    private final BrickerGameManager brickerGameManager;

    /**
     * Constructor for the Camera Strategy.
     * @param gameObjectCollection collection that contains all the game objects in the game.
     * @param windowDimensions the dimensions of the screen
     * @param brickerGameManager the game manager
     * @param currBricksNumber the current bricks number in the game
     */
    public CameraStrategy(GameObjectCollection gameObjectCollection, Vector2 windowDimensions,
                          BrickerGameManager brickerGameManager, Counter currBricksNumber, Ball ball) {
        super(gameObjectCollection, currBricksNumber);
        this.windowDimensions = windowDimensions;
        this.brickerGameManager = brickerGameManager;
        this.ball = ball;
    }

    /**
     * when the collision happen between the brick and the main ball. if there was already camera
     * then return.
     * @param thisObj The first game object involved in the collision.
     * @param otherObj The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj); // the brick disappear
        if(brickerGameManager.camera() != null || !otherObj.getTag().equals(Constants.MAIN_BALL_TAG)){
            // camera is busy or the collision wasn't with the main ball
            return;
        }
        brickerGameManager.setCamera(new Camera(ball, Vector2.ZERO, windowDimensions.mult(1.2f),
                windowDimensions));
        ball.resetBallCounter();
    }

}