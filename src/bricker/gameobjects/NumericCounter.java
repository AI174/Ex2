package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericCounter extends GameObject {
    private Counter livesCounter;
    private TextRenderable textRenderable;

    public NumericCounter(Vector2 topLeftCorner, Vector2 dimensions,Renderable renderable,
                          TextRenderable textRenderable,Counter livesCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.livesCounter = livesCounter;
        this.textRenderable = textRenderable;
        this.renderer().setRenderable(textRenderable); // to avoid down casting
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