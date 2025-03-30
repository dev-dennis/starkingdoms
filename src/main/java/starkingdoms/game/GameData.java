package starkingdoms.game;

import java.util.Arrays;

public class GameData {

    private static final int PERCENT_ORDERABLE = 10;

    private int[] mQue = new int[24];

    private int mLand;

    private int mTick;

    private boolean mCloned;

    private int mOrdered;

    public GameData(int pLand) {
	setLand(pLand);
    }

    public GameData(int pLand, int[] pQue) {

	setLand(pLand);
	setQue(pQue);
	setCloned();
    }

    public GameData(GameData pGameData) {

	int[] lQueClone = Arrays.copyOf(pGameData.getQue(), pGameData.getQueSize());
	setLand(pGameData.getLand());
	setQue(lQueClone);
	setTick(pGameData.getTick());
	setOrdered(pGameData.getOrdered());
	setCloned();
    }

    public int getLandInQue() {

	int lLand = 0;
	for (int l : getQue()) {
	    lLand += l;
	}
	return lLand;
    }

    public int getMaxOrderable() {
	return getLand() / PERCENT_ORDERABLE;
    }

    public int getOrderable() {
	return getMaxOrderable() - getLandInQue();
    }

    public int getNextPotencyOf(int i) {
	return getLand() - (getLand() % i) + i;
    }

    public int getNextTenPot() {
	return getNextPotencyOf(10);
    }

    public int getQueSize() {
	return getQue().length;
    }

    public void requestOrder(int pLand) throws OrderException {

	if (pLand > getOrderable()) {
	    throw new OrderException(pLand, getOrderable());
	} else {
	    order(pLand);
	}
    }

    private void order(int pLand) {

	for (int i = 0; i < pLand; i++) {
	    getQue()[getQueSize() - 1 - (i % getQueSize())]++;
	}
	setOrdered(pLand);
    }

    private void setOrdered(int pLand) {
	mOrdered = pLand;
    }

    public void turn(int pTurns) {

	for (int i = 0; i < pTurns; i++) {
	    turn();
	}
    }

    public void turn() {

	// adding produced land
	addLand(getQue()[0]);

	// copying queue positions to the left
	for (int i = 0; i < getQueSize() - 1; i++) {
	    getQue()[i] = getQue()[i + 1];
	}

	// clearing last queue position
	getQue()[getQueSize() - 1] = 0;
    }

    public int[] getQue() {
	return mQue;
    }

    public String getQueString() {
	return Arrays.toString(mQue);
    }

    private void setQue(int[] pQue) {
	mQue = pQue;
    }

    public int getLand() {
	return mLand;
    }

    private void setLand(int pLand) {
	mLand = pLand;
    }

    private void addLand(int pLand) {
	setLand(getLand() + pLand);
    }

    public boolean isCloned() {
	return mCloned;
    }

    private void setCloned() {
	mCloned = true;
    }

    @Override
    public String toString() {
	return String.valueOf(getLand());
    }

    public int getTick() {
	return mTick;
    }

    public void setTick(int mTick) {
	this.mTick = mTick;
    }

    public int getOrdered() {
	return mOrdered;
    }

}