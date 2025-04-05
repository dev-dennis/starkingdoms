package starkingdoms.buildorder.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import starkingdoms.buildorder.game.GameResult;
import starkingdoms.buildorder.game.OrderException;
import starkingdoms.buildorder.game.Strategy;
import starkingdoms.buildorder.game.StrategyAlwaysRequestOrderable;
import starkingdoms.buildorder.game.StrategyFive;
import starkingdoms.buildorder.game.StrategyFour;
import starkingdoms.buildorder.game.StrategyRequestBuildOrder;
import starkingdoms.buildorder.game.StrategyRequestBuildOrderOld;
import starkingdoms.buildorder.game.StrategyRequestFullQues;
import starkingdoms.buildorder.game.StrategySix;
import starkingdoms.buildorder.model.BuildOrder;

@Service
public class BuildOrderService {

	private BuildOrderService() {

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

	private static void runStrategy(int pStrategyNumber) throws OrderException {

		Strategy lStrategy = null;
		if (pStrategyNumber == 1) {
			lStrategy = new StrategyAlwaysRequestOrderable();
		} else if (pStrategyNumber == 2) {
			lStrategy = new StrategyRequestFullQues();
		} else if (pStrategyNumber == 3) {
			lStrategy = new StrategyRequestBuildOrder(getBuildOrder1());
		} else if (pStrategyNumber == 4) {
			lStrategy = new StrategyFour();
		} else if (pStrategyNumber == 5) {
			lStrategy = new StrategyFive();
		} else if (pStrategyNumber == 6) {
			lStrategy = new StrategySix();
		}
		if (lStrategy != null) {
			lStrategy.run(96);
		}
	}

	private static BuildOrder getBuildOrder1() {

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

	private static void bruteForceBuildOrders() {

		// test
		List<BuildOrder> lBuildOrders = new ArrayList<>();
		lBuildOrders.addAll(createAllBuildOrders());

		for (BuildOrder buildOrder : lBuildOrders) {
			System.out.println(buildOrder);

		}

		List<GameResult> lGameResults = new ArrayList<>();
		for (BuildOrder lBuildOrder : lBuildOrders) {
			StrategyRequestBuildOrderOld strategy = new StrategyRequestBuildOrderOld(lBuildOrder.getOrder(), false);
			strategy.setSilent(false);
			GameResult lGameResult;
			try {
				lGameResult = strategy.run(96);
				lGameResults.add(lGameResult);
			} catch (OrderException e) {
				System.out.println(e);
			}
		}

		Collections.sort(lGameResults);
		for (GameResult lGameResult : lGameResults) {
			System.out.println(lGameResult);
		}

	}

	private static Collection<BuildOrder> createAllBuildOrders() {

		ArrayList<BuildOrder> pBOs = new ArrayList<>();
		createBuildOrder(null, 0, pBOs);
		return pBOs;
	}

	private static void createBuildOrder(int[] pBO, int pDepth, ArrayList<BuildOrder> pBOs) {

		int lOrderSize = 1;
		int lMaxDepth = 96;

		if (pDepth == 0) {
			for (int i = 0; i < lOrderSize; i++) {
				int[] lBuildOrder = new int[lMaxDepth];
				lBuildOrder[pDepth] = i;
				createBuildOrder(lBuildOrder, pDepth + 1, pBOs);
			}
		} else if (pDepth < lMaxDepth) {
			for (int i = 0; i < lOrderSize; i++) {
				int[] copyOf = Arrays.copyOf(pBO, pBO.length);
				copyOf[pDepth] = i;
				createBuildOrder(copyOf, pDepth + 1, pBOs);
			}
		} else {
			pBOs.add(new BuildOrder(pBO));
		}
	}

}
