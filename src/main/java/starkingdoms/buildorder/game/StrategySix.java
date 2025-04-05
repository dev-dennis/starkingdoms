package starkingdoms.buildorder.game;

public class StrategySix extends Strategy {

	@Override
	public void order() throws OrderException {

		getGame().requestFullQueWhenPossible();
		if (getGame().getOrderableNextTurn() > getGame().getQue().length) {
			getGame().requestOrderable();
		}
		if (getGame().getTurnsUntilNextTenPotencyWhenOrderMax() < getGame().getTurnsUntilNextTenPot()) {
			getGame().orderWhenWaitingIsNotBetter();
		}

	}

}
