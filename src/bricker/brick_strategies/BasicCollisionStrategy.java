package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public class BasicCollisionStrategy implements CollisionStrategy{
    private final GameObjectCollection gameObjectCollection;

    public BasicCollisionStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjectCollection = gameObjectCollection;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        this.gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
    }
}
