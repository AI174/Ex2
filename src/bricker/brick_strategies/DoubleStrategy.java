package bricker.brick_strategies;

import bricker.factories.StrategyFactory;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

import java.util.ArrayList;
import java.util.List;

public class DoubleStrategy extends BasicCollisionStrategy{
    private final List<CollisionStrategy> strategies = new ArrayList<>();
    private final StrategyFactory strategyFactory;

    public DoubleStrategy(GameObjectCollection gameObjectCollection, StrategyFactory strategyFactory, Counter currBricksNumber) {
        super(gameObjectCollection,currBricksNumber);
        this.strategyFactory = strategyFactory;
        initializeStrategies();

    }
    private void initializeStrategies(){
        namedStrategy strategy1 = strategyFactory.getSpecialRandomStrategy();
        namedStrategy strategy2 = strategyFactory.getSpecialRandomStrategy();
        // if there was a third strategy it can't be DoubleStrategy
        namedStrategy strategy3 = strategyFactory.getSpecialRandomStrategyWithoutDouble();

        // if both of first and second strategies are not DoubleStrategy -> we don't have a third strategy
        if(strategy1.getStrategyType() != StrategyType.DOUBLE_STRATEGY && strategy2 .getStrategyType() != StrategyType.DOUBLE_STRATEGY){
            strategy3 = null;
        }
        // if both of first and second strategies are DoubleStrategy -> we have 3 non-DoubleStrategy
        else if (strategy1.getStrategyType() == StrategyType.DOUBLE_STRATEGY && strategy2 .getStrategyType() == StrategyType.DOUBLE_STRATEGY) {
            strategy1 = strategyFactory.getSpecialRandomStrategyWithoutDouble();
            strategy2 = strategyFactory.getSpecialRandomStrategyWithoutDouble();
        }
        else {
            // one of the first/second strategies are DoubleStrategy -> we have 3 non-DoubleStrategy
            if (strategy1.getStrategyType() == StrategyType.DOUBLE_STRATEGY){
                strategy1 = strategyFactory.getSpecialRandomStrategyWithoutDouble();
            }
            else {
                strategy2 = strategyFactory.getSpecialRandomStrategyWithoutDouble();
            }
        }
        strategies.add(strategy1.getCollisionStrategy());
        strategies.add(strategy2.getCollisionStrategy());
        if(strategy3!= null){
            strategies.add(strategy3.getCollisionStrategy());
        }

        // if u want to try stuff as u wish :)
//        strategies.add(specialStrategyFactory.getStrategy(StrategyType.EXTRA_PADDLE_STRATEGY));
//        strategies.add(specialStrategyFactory.getStrategy(StrategyType.PUCKS_STRATEGY));
//        strategies.add(specialStrategyFactory.getStrategy(StrategyType.EXTRA_LIFE_STRATEGY));
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        for(CollisionStrategy strategy: strategies){
            strategy.onCollision(thisObj,otherObj);
        }
    }


}
