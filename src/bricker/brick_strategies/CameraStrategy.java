package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

public class CameraStrategy extends BasicCollisionStrategy{
    private final Ball ball;
    private final Vector2 windowDimensions;
    private final BrickerGameManager brickerGameManager;

    public CameraStrategy(GameObjectCollection gameObjectCollection, Vector2 windowDimensions,
                          BrickerGameManager brickerGameManager, Counter currBricksNumber) {
        super(gameObjectCollection, currBricksNumber);
        this.windowDimensions = windowDimensions;
        this.brickerGameManager = brickerGameManager;
        this.ball = brickerGameManager.getMainBall();
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj); // the brick disappear
        if(brickerGameManager.camera() != null || !otherObj.getTag().equals("MAIN_BALL")){
            // camera is busy or the collision wasn't with the main ball
            return;
        }
        brickerGameManager.setCamera(new Camera(ball, Vector2.ZERO, windowDimensions.mult(1.2f),
                windowDimensions));
        ball.resetBallCounter();
    }

}