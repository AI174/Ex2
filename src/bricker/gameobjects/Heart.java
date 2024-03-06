package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * The Heart Class.
 * @author adan.ir1, hayanat2002
 */
public class Heart extends GameObject {
    private final GameObjectCollection gameObjectCollection;
    private final Counter livesCounter;
    private final Vector2 windowDimensions;

    /**
     * the Heart Constructor, initializes the needed fields.
     * @param renderable the rendering status of the extra paddle
     * @param windowDimensions the dimensions of the screen
     * @param gameObjectCollection collection that contains all the game objects in the game.
     * @param livesCounter the current lives in the game.
     */
    public Heart(Renderable renderable, Vector2 windowDimensions
            ,GameObjectCollection gameObjectCollection, Counter livesCounter) {
        super(Vector2.ZERO, new Vector2(Constants.HEART_SIZE, Constants.HEART_SIZE), renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.livesCounter = livesCounter;
        this.windowDimensions = windowDimensions;
    }

    /**
     * declare which objects the heart can collide with.
     * @param other The other GameObject.
     * @return true if it should collide with, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if(this.getTag().equals(Constants.MOVING_HEART_TAG)){
            if(other.getTag().equals(Constants.MAIN_PADDLE_TAG)){
                return true;
            }
            return false;
        }
        return super.shouldCollideWith(other);  // normal heart
    }

    /**
     * when the collision happens between the main paddle and the heart we remove the heart.
     * also update the livesCounter.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        gameObjectCollection.removeGameObject(this);
        if(livesCounter.value() < Constants.MAX_LIVES){
            livesCounter.increaseBy(1);
        }
    }

    /**
     * updates the heart, if it got outside the screen to remove it from the game.
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getTopLeftCorner().y() > this.windowDimensions.y())
        {
            this.gameObjectCollection.removeGameObject(this);
        }
    }
}