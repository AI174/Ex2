package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class BasicCollisionStrategy implements CollisionStrategy{
    private final GameObjectCollection gameObjectCollection;
    private final Counter currBricksNumber;

    public BasicCollisionStrategy(GameObjectCollection gameObjectCollection, Counter currBricksNumber) {
        this.gameObjectCollection = gameObjectCollection;
        this.currBricksNumber = currBricksNumber;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if(this.gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS)){
            currBricksNumber.increaseBy(-1);
        }
    }
}
