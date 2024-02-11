package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;


public class PucksStrategy extends BasicCollisionStrategy{
    private final int BALL_RADIUS;
    private static final int PUCKS_NUMBER = 2;
    private static final float PUCK_SIZE_FACTOR = (float) 0.75;
    private final GameObjectCollection gameObjectCollection;
    private final Vector2 ballVelocity;
    private final Vector2 windowDimensions;
    private final Sound collisionSound;
    private final Renderable puckImage;


    public PucksStrategy(GameObjectCollection gameObjectCollection, ImageReader imageReader,
                         SoundReader soundReader,int ballRadius,
                         Vector2 ballVelocity,Vector2 windowDimensions) {

        super(gameObjectCollection);
        this.gameObjectCollection = gameObjectCollection;
        this.BALL_RADIUS = ballRadius;
        this.ballVelocity = ballVelocity;
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
        for (int i = 0; i < PUCKS_NUMBER; i++) {
            GameObject puck = new Puck(Vector2.ZERO,new Vector2(BALL_RADIUS * PUCK_SIZE_FACTOR,
                    BALL_RADIUS * PUCK_SIZE_FACTOR),puckImage,
                    collisionSound,gameObjectCollection,windowDimensions);
            puck.setCenter(objWithPuckStrategy.getCenter());
            setRandomVelocity(puck);
            gameObjectCollection.addGameObject(puck);
        }
    }

    private void setRandomVelocity(GameObject puck){
        float ballVelX = ballVelocity.y();
        float ballVelY = ballVelocity.y();

        Random random = new Random();
        if (random.nextBoolean())
            ballVelX *= (-1);
        if (random.nextBoolean())
            ballVelY *= (-1);

        puck.setVelocity(new Vector2(ballVelX,ballVelY));
    }
}
