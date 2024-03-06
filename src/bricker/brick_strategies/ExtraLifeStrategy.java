package bricker.brick_strategies;

import bricker.gameobjects.Heart;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * The extra life Strategy class, when it happened it creates a moving heart and add lives only if the
 * main paddle collides with it.
 * @author adan.ir1, hayanat2002
 */
public class ExtraLifeStrategy extends BasicCollisionStrategy implements CollisionStrategy {
    private final Renderable heartImage;
    private final GameObjectCollection gameObjectCollection;
    private final Counter livesCounter;
    private final Vector2 windowDimensions;

    /**
     * adds a moving heart when the collision happens between the ball and the brick.
     * @param gameObjectCollection collection that contains all the game objects in the game.
     * @param renderable the rendering pic of the heart
     * @param windowDimensions the dimensions of the screen
     * @param livesCounter the current number of lives in the game
     * @param currBricksNumber the current number of bricks in the game
     */
    public ExtraLifeStrategy(GameObjectCollection gameObjectCollection,Renderable renderable,
                             Vector2 windowDimensions, Counter livesCounter,Counter currBricksNumber) {
        super(gameObjectCollection,currBricksNumber);
        this.gameObjectCollection = gameObjectCollection;
        this.heartImage = renderable;
        this.livesCounter = livesCounter;
        this.windowDimensions = windowDimensions;
    }


    /**
     * add a moving heart when the collision between brick and ball happens.
     * @param thisObj The first game object involved in the collision.
     * @param otherObj The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        addMovingHeart(thisObj);
    }

    private void addMovingHeart(GameObject brick) {
        GameObject heart = new Heart(heartImage,windowDimensions, gameObjectCollection, livesCounter);
        heart.setCenter(brick.getCenter());
        heart.setVelocity(Vector2.DOWN.mult(Constants.HEART_SPEED));
        heart.setTag(Constants.MOVING_HEART_TAG);
        gameObjectCollection.addGameObject(heart);
    }






}