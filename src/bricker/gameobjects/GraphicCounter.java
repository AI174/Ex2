package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.List;

public class GraphicCounter extends GameObject {
    private int SPACE;
    private int START_HEART_NUMBER;
    private int HEART_SIZE;
    private int startX;
    private int startY;
    private int validX;
    private Vector2 topLeftCorner;
    private Vector2 heartDimensions;
    private Renderable heartImage;
    private Counter livesCounter;
    private int curLiveCount = 0;
    private GameObjectCollection gameObjects;
    private List<Heart> heartsArr = new ArrayList<>();

    public GraphicCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                          GameObjectCollection gameObjectCollection, Counter livesCounter, int[] constants){
        super(topLeftCorner, dimensions, null);
        this.topLeftCorner = topLeftCorner;
        this.heartImage = renderable;
        this.heartDimensions = dimensions;
        this.gameObjects = gameObjectCollection;
        this.livesCounter = livesCounter;

        this.HEART_SIZE = constants[0];
        this.START_HEART_NUMBER = constants[1];
        this.SPACE = constants[2];
        this.startX = constants[3];
        this.startY = constants[4];

        validX = startX ;
        increaseHearts(START_HEART_NUMBER);
    }


    private void increaseHearts(int n){
        for(int i = 0; i<n ; i++) {
            Heart heart = new Heart(Vector2.ZERO, new Vector2(HEART_SIZE, HEART_SIZE), heartImage);
            heart.setTopLeftCorner(new Vector2(validX, startY));
            validX += HEART_SIZE + SPACE;
            gameObjects.addGameObject(heart, Layer.UI);
            heartsArr.add(curLiveCount, heart);
            curLiveCount ++;
        }
    }

//    private void addOneHeart(){
//        Heart heart = new Heart(Vector2.ZERO, new Vector2(HEART_SIZE, HEART_SIZE), heartImage);
//        heart.setTopLeftCorner(new Vector2(validX, startY));
//        validX += HEART_SIZE + SPACE;
//        gameObjects.addGameObject(heart, Layer.UI);
//        heartsArr.add(curLiveCount, heart);
//        curLiveCount ++;
//    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (livesCounter.value() > curLiveCount){
            increaseHearts(livesCounter.value() - curLiveCount);
        }

        if (livesCounter.value() < curLiveCount){
            curLiveCount -- ;
            gameObjects.removeGameObject(this.heartsArr.get(livesCounter.value()), Layer.UI);
            heartsArr.add(livesCounter.value(), null);
        }
    }

}