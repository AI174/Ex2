package bricker.brick_strategies;

import bricker.gameobjects.Heart;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class ExtraLifeStrategy extends BasicCollisionStrategy implements CollisionStrategy {
    private final Renderable heartImage;
    private final GameObjectCollection gameObjectCollection;
    private final Counter livesCounter;
    private final Vector2 windowDimensions;

    public ExtraLifeStrategy(GameObjectCollection gameObjectCollection,Renderable renderable,Vector2 windowDimensions, Counter livesCounter,Counter currBricksNumber) {
        super(gameObjectCollection,currBricksNumber);
        this.gameObjectCollection = gameObjectCollection;
        this.heartImage = renderable;
        this.livesCounter = livesCounter;
        this.windowDimensions = windowDimensions;
    }


    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        addMovingHeart(thisObj);
    }

    private void addMovingHeart(GameObject brick) {

        GameObject heart = new Heart(heartImage,windowDimensions, gameObjectCollection, livesCounter);
        heart.setCenter(brick.getCenter());
        heart.setVelocity(Vector2.DOWN.mult(Constants.HEART_SPEED));
        heart.setTag("MOVING_HEART"); // didn't use this until now
        gameObjectCollection.addGameObject(heart);
    }






}