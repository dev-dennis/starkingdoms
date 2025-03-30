package starkingdoms.service;

import org.springframework.stereotype.Service;

import starkingdoms.game.GameResult;
import starkingdoms.game.OrderException;
import starkingdoms.game.Strategy;
import starkingdoms.game.StrategyRequestBuildOrder;
import starkingdoms.model.BuildOrder;

@Service
public class SCService {

    private SCService() {
    }

    public GameResult runStrategy(BuildOrder pBuildOrder) throws OrderException {

	Strategy lStrategy = new StrategyRequestBuildOrder(pBuildOrder.getOrder());
	lStrategy.setSilent(false);
	return lStrategy.run(96);
    }

    public GameResult runStrategySilent(BuildOrder pBuildOrder) throws OrderException {

	Strategy lStrategy = new StrategyRequestBuildOrder(pBuildOrder.getOrder());
	lStrategy.setSilent(true);
	return lStrategy.run(96);
    }

    public BuildOrder getBestBuildOrder() {

	int[] lBuildOrder = new int[96];
	lBuildOrder[96 - 1] = 24;
	lBuildOrder[86 - 1] = 17;
	lBuildOrder[72 - 1] = 23;
	lBuildOrder[62 - 1] = 21;
	lBuildOrder[50 - 1] = 23;
	lBuildOrder[40 - 1] = 23;
	lBuildOrder[30 - 1] = 23;
	lBuildOrder[21 - 1] = 23;
	lBuildOrder[12 - 1] = 24;
	lBuildOrder[4 - 1] = 24;
	return new BuildOrder(lBuildOrder);
    }
}
