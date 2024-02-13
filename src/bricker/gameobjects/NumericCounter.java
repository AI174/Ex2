package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericCounter extends GameObject {
    private final Counter livesCounter;
    private final TextRenderable textRenderable;

    public NumericCounter(TextRenderable textRenderable,Counter livesCounter) {
        super(Vector2.ZERO, new Vector2(Constants.NUMERIC_COUNTER_SIZE,Constants.NUMERIC_COUNTER_SIZE),
                null);
        this.livesCounter = livesCounter;
        this.textRenderable = textRenderable;
        // to avoid down casting : this.textRenderable =(TextRenderable) renderable;
        this.renderer().setRenderable(textRenderable);
    }

    private Color getColor(){
        if (livesCounter.value() >= 3)
            return Color.green;
        if (livesCounter.value() == 2)
            return Color.yellow;
        return Color.red;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        textRenderable.setString(Integer.toString(livesCounter.value()));
        textRenderable.setColor(getColor());
    }
}