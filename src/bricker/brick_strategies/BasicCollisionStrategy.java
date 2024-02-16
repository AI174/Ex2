package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
/**
 * Represents a basic collision strategy used for handling collisions between game objects.
 * @author adan.ir1, hayanat2002
 * @see CollisionStrategy
 */
public class BasicCollisionStrategy implements CollisionStrategy{
    private final GameObjectCollection gameObjectCollection;
    private final Counter currBricksNumber;
    /**
     * Constructs a new BasicCollisionStrategy with the specified game object collection
     * and current bricks counter.
     * @param gameObjectCollection The collection of game objects.
     * @param currBricksNumber The counter tracking the current number of bricks.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjectCollection, Counter currBricksNumber) {
        this.gameObjectCollection = gameObjectCollection;
        this.currBricksNumber = currBricksNumber;
    }
    /**
     * Handles the collision between two game objects.
     * If the removing the thisObj(brick) is successful,
     * the current bricks counter is decremented accordingly.
     * @param thisObj The first game object involved in the collision.
     * @param otherObj The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if(this.gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS)){
            currBricksNumber.increaseBy(-1);
        }
    }
}
