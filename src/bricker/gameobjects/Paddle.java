package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Paddle Class.
 * @author adan.ir1, hayanat2002
 */
public class Paddle extends GameObject {
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    /**
     * creates the paddle in the game.
     * @param renderable the rendering status of the main paddle
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                       a given key is currently pressed by the user or not. See its
     *                       documentation.
     * @param windowDimensions the dimensions of the screen
     */
    public Paddle(Renderable renderable, UserInputListener inputListener,Vector2 windowDimensions) {
        super(Vector2.ZERO, new Vector2(Constants.PADDLE_WIDTH,Constants.PADDLE_HEIGHT), renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * updates the paddle location and make sure to keep it in the game.
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
        if(inputListener == null){
            return;
        }
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDir = movementDir.add(Vector2.LEFT);

        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        // limits 1.left 2.right
        if(getTopLeftCorner().x() < Constants.BORDER_WIDTH){
            setTopLeftCorner(new Vector2(Constants.BORDER_WIDTH,getTopLeftCorner().y()));
        }

        if(getTopLeftCorner().x() > windowDimensions.x()-Constants.BORDER_WIDTH- getDimensions().x()){
            setTopLeftCorner(new Vector2(windowDimensions.x()-Constants.BORDER_WIDTH- getDimensions().x(),
                    getTopLeftCorner().y()));
        }
        setVelocity(movementDir.mult(Constants.PADDLE_MOVEMENT_SPEED));

    }
}
