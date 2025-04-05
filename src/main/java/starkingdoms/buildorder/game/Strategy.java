package starkingdoms.buildorder.game;

import java.util.ArrayList;
import java.util.List;

public abstract class Strategy {

	private Game mGame;

	public Game getGame() {

		return mGame;
	}

	public void setGame(Game pGame) {

		mGame = pGame;
	}

	protected Strategy() {

		setGame(new Game(300));
	}

	private int mTick;

	private boolean mSilent;

	public int getTick() {

		return mTick;
	}

	private void setTick(int mTick) {

		this.mTick = mTick;
	}

	public abstract void order() throws OrderException;

	public GameResult run(int pTurns) throws OrderException {

		List<GameData> lDatas = new ArrayList<>();
		if (!isSilent()) {
			System.out.print("start\t\t");
			getGame().printQueAndLand();
		}

		for (int i = 0; i < pTurns; i++) {

			setTick(i);
			getGame().getData().setTick(i);
			try {
				order();
			} catch (OrderException e) {
				throw new OrderException(e, i);
			}
			if (!isSilent()) {
				System.out.println();
			}
			lDatas.add(new GameData(getGame().getData()));

			getGame().turn();
			if (!isSilent()) {
				System.out.print("Tick:" + (pTurns - i) + "\t\t");
				getGame().printQueAndLand();
			}
		}
		getGame().getData().setTick(pTurns);
		lDatas.add(new GameData(getGame().getData()));
		if (!isSilent()) {
			System.out.println();
			System.out.print("end\t\t");
			getGame().printQueAndLand();
			System.out.print("InQue:" + getGame().getLandInQue() + "\t");
			System.out.println();
		}

		return new GameResult(getGame().getLand(), getGame().getLandInQue(), lDatas);

	}

	public void setSilent(boolean pSilent) {

		mSilent = pSilent;
	}

	public boolean isSilent() {

		return mSilent;
	}

}
