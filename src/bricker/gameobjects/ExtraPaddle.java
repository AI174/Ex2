package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ExtraPaddle extends Paddle{
    private int maxCollisions;
    private int curCollisions = 0;
    private final GameObjectCollection gameObjectCollection;

    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener inputListener, Vector2 windowDimensions, int maxCollisions,
                       GameObjectCollection gameObjectCollection) {
        super(new Vector2(topLeftCorner),
                dimensions, renderable, inputListener, windowDimensions);
        this.maxCollisions = maxCollisions;
        this.setTag("EXTRA_PADDLE");
        this.gameObjectCollection = gameObjectCollection;
    }


    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        curCollisions ++;
        if(curCollisions >= maxCollisions){
            gameObjectCollection.removeGameObject(this, Layer.DEFAULT);
        }
    }

}