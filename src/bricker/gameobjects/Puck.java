package bricker.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
/**
 * Represents a puck object in the game, inheriting from the Ball class.
 * Pucks are balls with additional functionality.
 * @author adan.ir1, hayanat2002
 * @see Ball
 */
public class Puck extends Ball{
    private final GameObjectCollection gameObjectCollection;
    private final Vector2 windowDimensions;
    /**
     * Constructs a new Puck object with the specified renderable, collision sound,
     * game object collection, and window dimensions.
     * @param renderable Used to visually represent the puck.
     * @param collisionSound The sound played upon collision with other game objects.
     * @param gameObjectCollection The collection of game objects.
     * @param windowDimensions The dimensions of the game window.
     */
    public Puck(Renderable renderable, Sound collisionSound,
                GameObjectCollection gameObjectCollection,Vector2 windowDimensions) {
        super(renderable, collisionSound);
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;

    }
    /**
     * Overrides the method called onCollisionEnter.
     * This method checks if the puck has moved beyond the window dimensions and removes
     * it from the game if it has.
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(getCenter().y() > windowDimensions.y()){
            gameObjectCollection.removeGameObject(this);
        }
    }
}
