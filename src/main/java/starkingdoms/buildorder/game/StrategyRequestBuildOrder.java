package starkingdoms.buildorder.game;

import starkingdoms.buildorder.model.BuildOrder;

public class StrategyRequestBuildOrder extends Strategy {

	private BuildOrder mBuildOrder;

	public StrategyRequestBuildOrder(int[] pBuildOrder) {

		setBuildOrder(new BuildOrder(pBuildOrder));
		getGame().setSilent(isSilent());
	}

	public StrategyRequestBuildOrder(BuildOrder pBuildOrder) {

		setBuildOrder(pBuildOrder);
		getGame().setSilent(isSilent());
	}

	public BuildOrder getBuildOrder() {

		return mBuildOrder;
	}

	public void setBuildOrder(BuildOrder pBuildOrder) {

		mBuildOrder = pBuildOrder;
	}

	@Override
	public void order() throws OrderException {

		int lCurrentTick = getBuildOrder().getOrder().length - 1 - getTick();
		getGame().requestOrder(getBuildOrder().getOrder()[lCurrentTick]);
	}

	@Override
	public GameResult run(int pTurns) throws OrderException {

		GameResult lGameResult = super.run(pTurns);
		lGameResult.setBuildOrder(getBuildOrder());
		return lGameResult;
	}

}
