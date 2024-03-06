package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * the bricks class
 * @author adan.ir1, hayanat2002
 */
public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy;


    /**
     * Constructor of the brick
     * @param dimensions the dimensions of the brick
     * @param renderable the picture of the brick
     * @param collisionStrategy the strategy to use on the brick when another game object hits it
     * @param currBricksNumber the current bricks number in the game
     */
    public Brick(Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, Counter currBricksNumber) {
        super(Vector2.ZERO, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        currBricksNumber.increaseBy(1);
    }
    /**
     * when the collision happens call the strategy
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision ) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this,other);

    }
}