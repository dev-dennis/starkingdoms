package starkingdoms.buildorder.game;

public class StrategyRequestFullQues extends Strategy {

	@Override
	public void order() throws OrderException {

		getGame().requestFullQueWhenPossible();
	}

}
