package bricker.brick_strategies;

import bricker.factories.SpecialStrategyFactory;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

import java.util.ArrayList;
import java.util.List;

public class DoubleStrategy extends BasicCollisionStrategy{
    private final List<CollisionStrategy> strategies = new ArrayList<>();
    private final SpecialStrategyFactory specialStrategyFactory;

    public DoubleStrategy(GameObjectCollection gameObjectCollection, SpecialStrategyFactory specialStrategyFactory) {
        super(gameObjectCollection);
        this.specialStrategyFactory = specialStrategyFactory;
        initializeStrategies();

    }
    private void initializeStrategies(){
        namedStrategy strategy1 = specialStrategyFactory.getRandomStrategy();
        namedStrategy strategy2 = specialStrategyFactory.getRandomStrategy();
        // if there was a third strategy it can't be DoubleStrategy
        namedStrategy strategy3 = specialStrategyFactory.getRandomStrategyWithoutDouble();

        // if both of first and second strategies are not DoubleStrategy -> we don't have a third strategy
        if(strategy1.getStrategyType() != StrategyType.DOUBLE_STRATEGY && strategy2 .getStrategyType() != StrategyType.DOUBLE_STRATEGY){
            strategy3 = null;
        }
        // if both of first and second strategies are DoubleStrategy -> we have 3 non-DoubleStrategy
        else if (strategy1.getStrategyType() == StrategyType.DOUBLE_STRATEGY && strategy2 .getStrategyType() == StrategyType.DOUBLE_STRATEGY) {
            strategy1 = specialStrategyFactory.getRandomStrategyWithoutDouble();
            strategy2 = specialStrategyFactory.getRandomStrategyWithoutDouble();
        }
        else {
            // one of the first/second strategies are DoubleStrategy -> we have 3 non-DoubleStrategy
            if (strategy1.getStrategyType() == StrategyType.DOUBLE_STRATEGY){
                strategy1 = specialStrategyFactory.getRandomStrategyWithoutDouble();
            }
            else {
                strategy2 = specialStrategyFactory.getRandomStrategyWithoutDouble();
            }
        }
        strategies.add(strategy1.getCollisionStrategy());
        strategies.add(strategy2.getCollisionStrategy());
        if(strategy3!= null){
            strategies.add(strategy3.getCollisionStrategy());
        }
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        for(CollisionStrategy strategy: strategies){
            strategy.onCollision(thisObj,otherObj);
        }
    }


}
