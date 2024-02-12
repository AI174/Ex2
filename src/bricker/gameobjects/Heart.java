package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Heart extends GameObject {
    private static final int MAX_LIVES = 4;
    private GameObjectCollection gameObjectCollection;
    private Counter livesCounter;

    private Vector2 windowDimensions;

    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Vector2 windowDimensions
            ,GameObjectCollection gameObjectCollection, Counter livesCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.livesCounter = livesCounter;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        if(this.getTag().equals("MOVING_HEART")){
            if(other.getTag().equals("PADDLE")){
                return true;
            }
            return false;
        }
        return super.shouldCollideWith(other);  // normal heart
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        gameObjectCollection.removeGameObject(this);
        if(livesCounter.value() < MAX_LIVES){
            livesCounter.increaseBy(1);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getTopLeftCorner().y() > this.windowDimensions.y())
        {
            this.gameObjectCollection.removeGameObject(this);
        }
    }
}