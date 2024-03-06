package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Numeric lives counter.
 * @author adan.ir1, hayanat2002
 */
public class NumericCounter extends GameObject {
    private final Counter livesCounter;
    private final TextRenderable textRenderable;

    /**
     * the constructor, creates a numeric counter.
     * @param textRenderable the rendering type
     * @param livesCounter counter to the current lives in the game.
     */
    public NumericCounter(TextRenderable textRenderable,Counter livesCounter) {
        super(Vector2.ZERO, new Vector2(Constants.NUMERIC_COUNTER_SIZE,Constants.NUMERIC_COUNTER_SIZE),
                textRenderable);
        this.livesCounter = livesCounter;
        this.textRenderable = textRenderable;
    }

    private Color getColor(){
        if (livesCounter.value() >= Constants.NUMERIC_COUNTER_GREEN)
            return Color.green;
        if (livesCounter.value() == Constants.NUMERIC_COUNTER_YELLOW)
            return Color.yellow;
        return Color.red;
    }
    /**
     * updates the numeric counter.
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        textRenderable.setString(Integer.toString(livesCounter.value()));
        textRenderable.setColor(getColor());
    }
}