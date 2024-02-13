package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy;
    private final Counter currBricksNumber;

    public Brick(Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, Counter currBricksNumber) {
        super(Vector2.ZERO, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.currBricksNumber = currBricksNumber;
        this.currBricksNumber.increaseBy(1);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision ) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this,other);

    }
}