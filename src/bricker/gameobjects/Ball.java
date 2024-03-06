package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
/**
 * Represents a ball object used in the game.
 * This class provides features and extends the GameObject class.
 * specific to the behavior of a ball, like sound effects and collision handling.
 * @author adan.ir1, hayanat2002
 * @see GameObject
 */
public class Ball extends GameObject {
    private final Sound collisionSound;
    private final Counter collisionCounter = new Counter(0);
    /**
     * Constructs a new Ball object with the specified renderable and collision sound
     * and with size of BALL_RADIUS*BALL_RADIUS.
     * @param renderable Used to visually represent the ball.
     * @param collisionSound The sound played upon collision with other game objects.
     */
    public Ball(Renderable renderable, Sound collisionSound) {
        super(Vector2.ZERO, new Vector2(Constants.BALL_RADIUS,Constants.BALL_RADIUS), renderable);
        this.collisionSound = collisionSound;
    }
    /**
     * Returns the collision counter associated with the ball - to use in the camera strategy.
     * may be rested at any time of the game.
     * @return The collision counter (as an integer number).
     */
    public int getCollisionCounter() {
        return collisionCounter.value();
    }
    /**
     * Overrides the method called onCollisionEnter.
     * The ball's velocity is modified in response to a collision by taking the
     * collision normal into account and incrementing the collision counter A collision sound is also played.
     * @param other The other game object involved in the collision.
     * @param collision The collision data.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionCounter.increaseBy(1);
        Vector2 newVal = getVelocity().flipped(collision.getNormal());
        setVelocity(newVal);
        collisionSound.play();

    }
    /**
     * Resets the collision counter (to Zero) associated with the ball.
     */
    public void resetBallCounter(){
        collisionCounter.reset();
    }
}
