package bricker.gameobjects;


import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


import java.util.ArrayList;
import java.util.List;

public class GraphicCounter extends GameObject {
    private final int startX;
    private final int startY;
    private final Renderable heartImage;
    private final Vector2 windowDimensions;
    private final Counter livesCounter;
    private int validX;
    private int curLiveCount = 0;
    private final GameObjectCollection gameObjects;
    private final List<Heart> heartsArr = new ArrayList<>();

    public GraphicCounter(Renderable renderable, Vector2 windowDimensions,
                          GameObjectCollection gameObjectCollection, Counter livesCounter){

        super(Vector2.ZERO, new Vector2(Constants.HEART_SIZE, Constants.HEART_SIZE), null);
        this.heartImage = renderable;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjectCollection;
        this.livesCounter = livesCounter;

        this.startX = Constants.BORDER_WIDTH + Constants.COUNTERS_SPACE + Constants.NUMERIC_COUNTER_SIZE  +
                Constants.SPACE_BETWEEN_GRAPHIC_NUMERIC;
        this.startY = (int)windowDimensions.y() - Constants.COUNTERS_SPACE - Constants.HEART_SIZE;
        this.validX = startX;

        increaseHearts(Constants.START_HEARTS_NUMBER);
    }


    private void increaseHearts(int n){ // it updates the curLiveCount
        for(int i = 0; i< n ; i++) {
            Heart heart = new Heart(heartImage, windowDimensions,gameObjects, livesCounter);
            heart.setTopLeftCorner(new Vector2(validX, startY));
            validX += Constants.HEART_SIZE + Constants.SPACE_BETWEEN_HEARTS;
            gameObjects.addGameObject(heart, Layer.UI);
            heartsArr.add(heart);
            curLiveCount ++;
        }
    }

    private void decreaseHearts(int n){ // it updates the curLiveCount
        for(int i = 0; i < n ; i++) {
            Heart hartToDelete = this.heartsArr.get(curLiveCount-1);
            validX -= Constants.HEART_SIZE + Constants.SPACE_BETWEEN_HEARTS;
            gameObjects.removeGameObject(hartToDelete, Layer.UI);
            heartsArr.remove(hartToDelete);
            curLiveCount--;
        }
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (livesCounter.value() > curLiveCount){
            increaseHearts(livesCounter.value() - curLiveCount);
        }

        if (livesCounter.value() < curLiveCount){
            decreaseHearts(curLiveCount - livesCounter.value());
        }
    }

}