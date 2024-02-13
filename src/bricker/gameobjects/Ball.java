package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Ball extends GameObject {
    private final Sound collisionSound;
    private Counter collisionCounter = new Counter(0);

    public Ball(Renderable renderable, Sound collisionSound) {
        super(Vector2.ZERO, new Vector2(Constants.BALL_RADIUS,Constants.BALL_RADIUS), renderable);
        this.collisionSound = collisionSound;
    }

    public Counter getCollisionCounter() {
        return collisionCounter;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionCounter.increaseBy(1);
        Vector2 newVal = getVelocity().flipped(collision.getNormal());
        setVelocity(newVal);
        collisionSound.play();

    }
}
