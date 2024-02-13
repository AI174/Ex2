package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class ExtraPaddleStrategy extends BasicCollisionStrategy implements CollisionStrategy{

    private final Renderable renderable;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjectCollection;

    public ExtraPaddleStrategy(GameObjectCollection gameObjectCollection, Renderable renderable,
                               UserInputListener inputListener, Vector2 windowDimensions,
                               Counter currBricksNumber) {
        super(gameObjectCollection,currBricksNumber);
        this.gameObjectCollection = gameObjectCollection;
        this.renderable = renderable;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj); // the brick disappear
        ExtraPaddle extraPaddle = new ExtraPaddle(renderable, inputListener,
                windowDimensions, gameObjectCollection);
        extraPaddle.setCenter( new Vector2(windowDimensions.x()/2, windowDimensions.y()/2));
        for (GameObject g:gameObjectCollection){
            if (g.getTag().equals("EXTRA_PADDLE")){
                return;
            }
        }
        gameObjectCollection.addGameObject(extraPaddle);
    }


}