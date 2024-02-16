package bricker.brick_strategies;

import danogl.GameObject;
/**
 * Defines the contract for collision strategies used for handling collisions
 * between game objects.
 * @author adan.ir1, hayanat2002
 */
public interface CollisionStrategy {
    /**
     * Handles the collision between two game objects.
     * @param thisObj The first game object involved in the collision.
     * @param otherObj The second game object involved in the collision.
     */
    void onCollision(GameObject thisObj, GameObject otherObj);
}
