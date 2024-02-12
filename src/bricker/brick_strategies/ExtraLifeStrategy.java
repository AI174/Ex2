package bricker.brick_strategies;

import bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class ExtraLifeStrategy extends BasicCollisionStrategy implements CollisionStrategy {
    private static final float HEART_SPEED = 100;
    private Vector2 topLeftCorner;
    private Vector2 dimensions;
    private Renderable heartImage;
    private UserInputListener inputListener;
    private GameObjectCollection gameObjectCollection;
    private Counter livesCounter;

    private Vector2 windowDimensions;

    public ExtraLifeStrategy(GameObjectCollection gameObjectCollection, Vector2 topLeftCorner,
                             Vector2 dimensions, Renderable renderable, UserInputListener inputListener,
                             Vector2 windowDimensions, Counter livesCounter) {
        super(gameObjectCollection);
        this.gameObjectCollection = gameObjectCollection;
        this.topLeftCorner =topLeftCorner;
        this.dimensions = dimensions;
        this.heartImage =renderable;
        this.inputListener = inputListener;
        this.livesCounter = livesCounter;
        this.windowDimensions = windowDimensions;
    }


    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        addMovingHeart(thisObj);
    }

    private void addMovingHeart(GameObject brick) {

        GameObject heart = new Heart(Vector2.ZERO,dimensions,heartImage,windowDimensions,
                gameObjectCollection, livesCounter);
        heart.setCenter(brick.getCenter());
        heart.setVelocity(Vector2.DOWN.mult(HEART_SPEED));
        heart.setTag("MOVING_HEART"); // didn't use this until now
        gameObjectCollection.addGameObject(heart);
    }






}