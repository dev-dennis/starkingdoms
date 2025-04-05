package starkingdoms.buildorder.game;

public class StrategyAlwaysRequestOrderable extends Strategy {

	@Override
	public void order() throws OrderException {

		getGame().requestOrderable();
	}

}
