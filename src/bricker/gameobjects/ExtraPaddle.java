package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ExtraPaddle extends Paddle{
    private int curCollisions = 0;
    private final GameObjectCollection gameObjectCollection;

    public ExtraPaddle(Renderable renderable,UserInputListener inputListener,Vector2 windowDimensions,
                       GameObjectCollection gameObjectCollection) {
        super(renderable, inputListener, windowDimensions);
        this.setTag("EXTRA_PADDLE");
        this.gameObjectCollection = gameObjectCollection;
    }


    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        curCollisions ++;
        if(curCollisions >= Constants.MAX_EXTRA_PADDLE_COLLISION){
            gameObjectCollection.removeGameObject(this, Layer.DEFAULT);
        }
    }

}