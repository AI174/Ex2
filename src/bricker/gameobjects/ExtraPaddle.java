package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * ExtraPaddle class (the paddle that appears in the center of the screen).
 * @author adan.ir1, hayanat2002
 */
public class ExtraPaddle extends Paddle{
    private int curCollisions = 0;
    private final GameObjectCollection gameObjectCollection;

    /**
     * Extra paddle constructor
     * @param renderable the rendering pic of the extra paddle
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowDimensions the dimensions of the screen
     * @param gameObjectCollection collection that contains all the game objects in the game.
     */
    public ExtraPaddle(Renderable renderable,UserInputListener inputListener,Vector2 windowDimensions,
                       GameObjectCollection gameObjectCollection) {
        super(renderable, inputListener, windowDimensions);
        this.setTag(Constants.EXTRA_PADDLE_TAG);
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * when there is a collision between the extra paddle and default layer game object, increment the
     * collisions number, and if it reached the limit it removes the extra paddle from the game.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        curCollisions ++;
        if(curCollisions >= Constants.MAX_EXTRA_PADDLE_COLLISION){
            gameObjectCollection.removeGameObject(this, Layer.DEFAULT);
        }
    }

}