package bricker.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Puck extends Ball{
    private final GameObjectCollection gameObjectCollection;
    private final Vector2 windowDimensions;

    public Puck(Renderable renderable, Sound collisionSound, GameObjectCollection gameObjectCollection,Vector2 windowDimensions) {
        super(renderable, collisionSound);
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(getCenter().y() > windowDimensions.y()){
            gameObjectCollection.removeGameObject(this);
        }
    }
}
