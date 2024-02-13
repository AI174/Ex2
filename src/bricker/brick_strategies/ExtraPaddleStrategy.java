package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class ExtraPaddleStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    private static final int collisionNumberToDisappear = 4;
    private Vector2 topLeftCorner;
    private Vector2 dimensions;
    private Renderable renderable;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private final GameObjectCollection gameObjectCollection;

    public ExtraPaddleStrategy(GameObjectCollection gameObjectCollection, Vector2 topLeftCorner, Vector2
            dimensions, Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions, Counter currBricksNumber) {
        super(gameObjectCollection,currBricksNumber);
        this.gameObjectCollection = gameObjectCollection;
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.renderable = renderable;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj); // the brick disappear
        ExtraPaddle extraPaddle = new ExtraPaddle(
                new Vector2(windowDimensions.x()/2, windowDimensions.y()/2),
                dimensions, renderable, inputListener, windowDimensions, collisionNumberToDisappear,
                gameObjectCollection);
        for (GameObject g:gameObjectCollection){
            if (g.getTag().equals("EXTRA_PADDLE")){
                return;
            }
        }
        gameObjectCollection.addGameObject(extraPaddle);
    }


}