package starkingdoms.buildorder.game;

public class StrategyFive extends Strategy {

	@Override
	public void order() throws OrderException {

		getGame().requestFullQueWhenPossible();
		if (getGame().getTurnsUntilNextTenPotencyWhenOrderMax() < getGame().getTurnsUntilNextTenPot()) {
			getGame().orderWhenWaitingIsNotBetter();
		}

	}

}
