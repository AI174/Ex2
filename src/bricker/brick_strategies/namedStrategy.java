package bricker.brick_strategies;

public class namedStrategy {
    private final CollisionStrategy collisionStrategy;
    private final StrategyType strategyType;

    public namedStrategy(CollisionStrategy collisionStrategy, StrategyType strategyType) {
        this.collisionStrategy = collisionStrategy;
        this.strategyType = strategyType;
    }

    public CollisionStrategy getCollisionStrategy() {
        return collisionStrategy;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }
}
