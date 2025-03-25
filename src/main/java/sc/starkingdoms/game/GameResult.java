package sc.starkingdoms.game;

import java.util.List;

import sc.starkingdoms.model.BuildOrder;

public class GameResult implements Comparable<GameResult> {

    private int land;

    private int landInQue;

    private BuildOrder buildOrder;

    private List<GameData> datas;

    public GameResult() {
    }

    public GameResult(int pLand, int pLandInQue) {

	setLand(pLand);
	setLandInQue(pLandInQue);
    }

    public GameResult(int pLand, int pLandInQue, List<GameData> pDatas) {

	setLand(pLand);
	setLandInQue(pLandInQue);
	setDatas(pDatas);
    }

    @Override
    public int compareTo(GameResult pGameResult) {

	int result = pGameResult.getLand() - getLand();
	if (result == 0) {
	    result = getLandInQue() - pGameResult.getLandInQue();
	}
	return result;
    }

    @Override
    public boolean equals(Object pGameResult) {
	if (this == pGameResult) {
	    return true;
	}
	if (pGameResult == null || this.getClass() != pGameResult.getClass()) {
	    return false;
	}
	return this.compareTo((GameResult) pGameResult) == 0;

    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    @Override
    public String toString() {
	return "Land:" + getLand() + ",LandInQue:" + getLandInQue() + ",BuildOrder:" + getBuildOrder();
    }

    public int getLand() {
	return land;
    }

    public void setLand(int land) {
	this.land = land;
    }

    public int getLandInQue() {
	return landInQue;
    }

    public void setLandInQue(int landInQue) {
	this.landInQue = landInQue;
    }

    public BuildOrder getBuildOrder() {
	return buildOrder;
    }

    public void setBuildOrder(BuildOrder buildOrder) {
	this.buildOrder = buildOrder;
    }

    public List<GameData> getDatas() {
	return datas;
    }

    public void setDatas(List<GameData> datas) {
	this.datas = datas;
    }

}
