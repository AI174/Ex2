package bricker.gameobjects;


import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


import java.util.ArrayList;
import java.util.List;

public class GraphicCounter extends GameObject {
    private final int SPACE_BETWEEN_HEARTS;
    private final int START_HEART_NUMBER;
    private final int HEART_SIZE;
    private final int startX;
    private final int startY;
    private final Renderable heartImage;
    private final Vector2 windowDimensions;
    private final Counter livesCounter;
    private int validX;
    private int curLiveCount = 0;
    private final GameObjectCollection gameObjects;
    private final List<Heart> heartsArr = new ArrayList<>();

    public GraphicCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                          Vector2 windowDimensions, GameObjectCollection gameObjectCollection,
                          Counter livesCounter, int[] constants){
        super(topLeftCorner, dimensions, null);
        this.heartImage = renderable;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjectCollection;
        this.livesCounter = livesCounter;

        this.HEART_SIZE = constants[0];
        this.START_HEART_NUMBER = constants[1];
        this.SPACE_BETWEEN_HEARTS = constants[2];
        this.startX = constants[3];
        this.startY = constants[4];
        this.validX = startX;

        increaseHearts(START_HEART_NUMBER);
    }


    private void increaseHearts(int n){ // it updates the curLiveCount
        for(int i = 0; i< n ; i++) {
            Heart heart = new Heart(Vector2.ZERO, new Vector2(HEART_SIZE, HEART_SIZE), heartImage,
                    windowDimensions,gameObjects, livesCounter);
            heart.setTopLeftCorner(new Vector2(validX, startY));
            validX += HEART_SIZE + SPACE_BETWEEN_HEARTS;
            gameObjects.addGameObject(heart, Layer.UI);
            heartsArr.add(heart);
            curLiveCount ++;
        }
    }

    private void decreaseHearts(int n){ // it updates the curLiveCount
        for(int i = 0; i < n ; i++) {
            Heart hartToDelete = this.heartsArr.get(curLiveCount-1);
            validX -= HEART_SIZE + SPACE_BETWEEN_HEARTS;
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