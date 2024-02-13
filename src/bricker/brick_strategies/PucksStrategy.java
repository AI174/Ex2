package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;


public class PucksStrategy extends BasicCollisionStrategy{
    private final GameObjectCollection gameObjectCollection;
    private final Vector2 windowDimensions;
    private final Sound collisionSound;
    private final Renderable puckImage;


    public PucksStrategy(GameObjectCollection gameObjectCollection, ImageReader imageReader,
                         SoundReader soundReader,Vector2 windowDimensions, Counter currBricksNumber) {

        super(gameObjectCollection,currBricksNumber);
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;
        this.puckImage = imageReader.readImage("assets/mockBall.png",true);
        this.collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");

    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        creatPuckBalls(thisObj);
    }

    private void creatPuckBalls(GameObject objWithPuckStrategy) {
        for (int i = 0; i < Constants.PUCKS_NUMBER; i++) {
            GameObject puck = new Puck(puckImage,collisionSound,gameObjectCollection,windowDimensions);
            puck.setCenter(objWithPuckStrategy.getCenter());
            puck.setDimensions(new Vector2(Constants.BALL_RADIUS * Constants.PUCK_SIZE_FACTOR,
                    Constants.BALL_RADIUS * Constants.PUCK_SIZE_FACTOR));
            setRandomVelocity(puck);
            gameObjectCollection.addGameObject(puck);
        }
    }

    private void setRandomVelocity(GameObject puck){
        float ballVelX = Constants.BALL_SPEED;
        float ballVelY = Constants.BALL_SPEED;

        Random random = new Random();
        if (random.nextBoolean())
            ballVelX *= (-1);
        if (random.nextBoolean())
            ballVelY *= (-1);

        puck.setVelocity(new Vector2(ballVelX,ballVelY));
    }
}
