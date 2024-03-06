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
/**
 * This strategy extends the BasicCollisionStrategy and provides
 * additional functionality specific to pucks, such as creating new pucks objects upon collision.
 * @author adan.ir1, hayanat2002
 * @see BasicCollisionStrategy
 */
public class PucksStrategy extends BasicCollisionStrategy{
    private final GameObjectCollection gameObjectCollection;
    private final Vector2 windowDimensions;
    private final Sound collisionSound;
    private final Renderable puckImage;
    /**
     * Constructs a new PucksStrategy with the specified parameters.
     * @param gameObjectCollection The collection of game objects.
     * @param imageReader The image reader used to load puck images.
     * @param soundReader The sound reader used to load collision sounds.
     * @param windowDimensions The dimensions of the game window.
     * @param currBricksNumber The counter tracking the current number of bricks.
     */
    public PucksStrategy(GameObjectCollection gameObjectCollection, ImageReader imageReader,
                         SoundReader soundReader,Vector2 windowDimensions, Counter currBricksNumber) {

        super(gameObjectCollection,currBricksNumber);
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;
        this.puckImage = imageReader.readImage(Constants.PUCK_IMAGE_PATH,true);
        this.collisionSound = soundReader.readSound(Constants.COLLISION_SOUND_PATH);

    }
    /**
     * Handles the collision between two game objects.
     * Upon collision, this method creates new puck objects.
     * @param thisObj The first game object involved in the collision.
     * @param otherObj The second game object involved in the collision.
     */
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
        Random random = new Random();
        double angle = random.nextDouble() * Math.PI;
        float ballVelX = Constants.BALL_SPEED * (float)Math.cos(angle);
        float ballVelY = Constants.BALL_SPEED * (float)Math.sin(angle);
        puck.setVelocity(new Vector2(ballVelX,ballVelY));
    }
}
