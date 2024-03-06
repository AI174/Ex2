package bricker.brick_strategies;

import danogl.GameObject;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a collision strategy decorator that combines multiple collision strategies
 * into a single strategy. It executes the collision behavior of multiple strategies.
 * @author adan.ir1, hayanat2002
 * @see BasicCollisionStrategy
 */
public class DoubleStrategy implements CollisionStrategyDecorator{
    private final List<CollisionStrategy> strategies;

    /**
     * Constructs a new DoubleStrategy with the specified parameters.
     * @param strategies the strategies to execute;
     */
    public DoubleStrategy(List<CollisionStrategy> strategies) {
        this.strategies = new ArrayList<>(strategies);
    }
    /**
     * Handles the collision between two game objects.
     * This method invokes the collision behavior of each strategy included in this double strategy.
     * @param thisObj The first game object involved in the collision.
     * @param otherObj The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        for(CollisionStrategy strategy: strategies){
            strategy.onCollision(thisObj,otherObj);
        }
    }

}