package starkingdoms.buildorder.game;

import starkingdoms.buildorder.model.BuildOrder;

public class StrategyRequestBuildOrderOld extends Strategy {

	private BuildOrder mBuildOrder;

	public StrategyRequestBuildOrderOld(int[] pBuildOrder) {
		setBuildOrder(new BuildOrder(pBuildOrder));
		getGame().setSilent(false);
	}

	public StrategyRequestBuildOrderOld(BuildOrder pBuildOrder) {
		setBuildOrder(pBuildOrder);
		getGame().setSilent(false);
	}

	public StrategyRequestBuildOrderOld(int[] pBuildOrder, boolean pSilent) {
		setBuildOrder(new BuildOrder(pBuildOrder));
		getGame().setSilent(pSilent);
	}

	public StrategyRequestBuildOrderOld(BuildOrder pBuildOrder, boolean pSilent) {
		setBuildOrder(pBuildOrder);
		getGame().setSilent(pSilent);
	}

	public BuildOrder getBuildOrder() {
		return mBuildOrder;
	}

	public void setBuildOrder(BuildOrder pBuildOrder2) {
		mBuildOrder = pBuildOrder2;
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
