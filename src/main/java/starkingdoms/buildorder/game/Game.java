package starkingdoms.buildorder.game;

public class Game {

	private boolean mSilent;

	public Game(int pLand) {

		setData(new GameData(pLand));
	}

	private GameData mData;

	public void requestFullQueWhenPossible() throws OrderException {

		requestFullQueWhenPossible(getData());
	}

	public void requestFullQueWhenPossible(GameData pData) throws OrderException {

		if (pData.getOrderable() >= pData.getQueSize()) {
			requestFullQue(pData);
		}
	}

	public void requestFullQue() throws OrderException {

		requestOrder(getData(), getData().getQueSize());
	}

	public void requestFullQue(GameData pData) throws OrderException {

		requestOrder(pData, pData.getQueSize());
	}

	public int getTurnsUntilNextTenPotencyWhenOrderMax() {

		return getTurnsUntilNextTenPotencyWhenOrderMaxAndWaiting(0);
	}

	private int getTurnsUntilNextTenPotencyWhenOrderMaxAndWaiting(int pTurns) {

		GameData lClone = new GameData(getData());
		waitTurnsAndOrderOrderable(lClone, pTurns);
		return getTurnsUntilNextTenPot(lClone) + pTurns;
	}

	public void orderWhenWaitingIsNotBetter() throws OrderException {

		if (getLandAtNextTenPotencyWhenOrderMaxAndWaiting(0) >= getLandAtNextTenPotencyWhenOrderMaxAndWaiting(1)) {
			requestOrderable();
		}
	}

	private int getLandAtNextTenPotencyWhenOrderMaxAndWaiting(int pTurns) {

		GameData lClone = new GameData(getData());
		waitTurnsAndOrderOrderable(lClone, pTurns);
		return getLandAtNextTenPot(lClone);
	}

	public static int getLandAtNextTenPot(GameData pData) {

		int lNextTenPot = pData.getNextTenPot();

		for (int i = 0; ((i < pData.getQueSize()) && (pData.getLand() < lNextTenPot)); i++) {
			pData.turn();
		}

		return pData.getLand();
	}

	private static void waitTurnsAndOrderOrderable(GameData lClone, int pTurns) {

		lClone.turn(pTurns);
		orderOrderable(lClone);
	}

	private static void orderOrderable(GameData pData) {

		try {
			pData.requestOrder(pData.getOrderable());
		} catch (OrderException e) {
			e.printStackTrace();
		}
	}

	public int getTurnsUntilNextTenPot() {

		GameData clone = new GameData(getData());
		return getTurnsUntilNextTenPot(clone);
	}

	public static int getTurnsUntilNextTenPot(GameData pData) {

		int lTurns = 0;
		int lNextTenPot = pData.getNextTenPot();

		for (int i = 0; ((i < pData.getQueSize()) && (pData.getLand() < lNextTenPot)); i++) {
			lTurns++;
			pData.turn();
		}

		return lTurns;
	}

	public void printQueAndLand() {

		printQue();
		printLand();
	}

	private void printLand() {

		System.out.print("Land:" + getData().getLand() + "\\t");
	}

	public void printQue() {

		System.out.print("|");
		for (int i = 0; i < getData().getQueSize(); i++) {
			System.out.print(getData().getQue()[i] + "|");
		}
		System.out.print("\t");
	}

	private void printOrder(GameData data, int pLand) {

		if (!data.isCloned() && !isSilent()) {
			System.out.print("Ordering:" + pLand);
			System.out.print(" (InQue:" + data.getLandInQue() + ",Orderable:" + data.getOrderable() + ")");
			System.out.print("\t");
		}
	}

	public void requestOrderable() throws OrderException {

		requestOrder(getData(), getData().getOrderable());
	}

	public void requestOrder(int pLand) throws OrderException {

		requestOrder(getData(), pLand);
	}

	private void requestOrder(GameData pData, int pLand) throws OrderException {

		pData.requestOrder(pLand);

		if (!pData.isCloned() && !isSilent() && (pLand > 0)) {
			printOrder(pData, pLand);
		}
	}

	private boolean isSilent() {

		return mSilent;
	}

	public void setSilent(boolean pSilent) {

		mSilent = pSilent;
	}

	GameData getData() {

		return mData;
	}

	public void setData(GameData pData) {

		mData = pData;
	}

	public void turn() {

		getData().turn();
	}

	public int getLandInQue() {

		return getData().getLandInQue();
	}

	public int getLand() {

		return getData().getLand();
	}

	public int[] getQue() {

		return getData().getQue();
	}

	public int getOrderableNextTurn() {

		GameData lClone = new GameData(getData());
		lClone.turn();
		return lClone.getOrderable();
	}

}
