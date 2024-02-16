package bricker.brick_strategies;
/**
 * Represents a named strategy, associating a collision strategy with a strategy type.
 * This class provides a way to encapsulate and manage different collision strategies
 * along with their corresponding types.
 * @author adan.ir1, hayanat2002
 */
public class namedStrategy {
    private final CollisionStrategy collisionStrategy;
    private final StrategyType strategyType;
    /**
     * Constructs a new NamedStrategy with the specified collision strategy and strategy type.
     * @param collisionStrategy The collision strategy to be associated with the named strategy.
     * @param strategyType The type of strategy being represented.
     */
    public namedStrategy(CollisionStrategy collisionStrategy, StrategyType strategyType) {
        this.collisionStrategy = collisionStrategy;
        this.strategyType = strategyType;
    }
    /**
     * Returns the collision strategy associated with this named strategy.
     * @return The collision strategy.
     */
    public CollisionStrategy getCollisionStrategy() {
        return collisionStrategy;
    }
    /**
     * Returns the strategy type associated with this named strategy.
     * @return The strategy type(enum).
     */
    public StrategyType getStrategyType() {
        return strategyType;
    }
}
