package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 300;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener,Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);

        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // *** for the null in ai paddle ***
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
        if(getTopLeftCorner().x() < 10){
            setTopLeftCorner(new Vector2(10,getTopLeftCorner().y()));
        }

        if(getTopLeftCorner().x() > windowDimensions.x()-10- getDimensions().x()){
            setTopLeftCorner(new Vector2(windowDimensions.x()-10- getDimensions().x(),getTopLeftCorner().y()));
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));

    }
}
