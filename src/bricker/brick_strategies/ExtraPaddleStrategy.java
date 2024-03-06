package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * The extra paddle Strategy class, when it happened it creates an extra paddle and add lives until the ball
 * or pucks collides with 4 times, then it disappears.
 * @author adan.ir1, hayanat2002
 */
public class ExtraPaddleStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    private final Renderable renderable;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjectCollection;

    /**
     * Constructor for the Extra Paddle Strategy.
     * @param gameObjectCollection collection that contains all the game objects in the game.
     * @param renderable the rendering pic of the extra paddle
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                       a given key is currently pressed by the user or not. See its
     *                       documentation.
     * @param windowDimensions the dimensions of the screen
     * @param currBricksNumber the current bricks number in the game
     */
    public ExtraPaddleStrategy(GameObjectCollection gameObjectCollection, Renderable renderable,
                               UserInputListener inputListener, Vector2 windowDimensions,
                               Counter currBricksNumber) {
        super(gameObjectCollection,currBricksNumber);
        this.gameObjectCollection = gameObjectCollection;
        this.renderable = renderable;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * add an extra paddle when the collision between brick and ball happens.
     * @param thisObj The first game object involved in the collision.
     * @param otherObj The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj); // the brick disappears
        ExtraPaddle extraPaddle = new ExtraPaddle(renderable, inputListener,
                windowDimensions, gameObjectCollection);
        extraPaddle.setCenter( new Vector2(windowDimensions.x()/2, windowDimensions.y()/2));
        for (GameObject g:gameObjectCollection){
            if (g.getTag().equals(Constants.EXTRA_PADDLE_TAG)){
                return;
            }
        }
        gameObjectCollection.addGameObject(extraPaddle);
    }


}