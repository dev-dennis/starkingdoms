package starkingdoms.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Turn implements Comparable<Turn> {

	Integer number;
	Integer tillNumber;
	List<TrainMilitary> trainMilitaryList;
	List<Build> buildList;
	List<AssignScientists> assignScientistsList;
	List<CancelResearch> cancelResearchList;
	List<ChangeState> changeStateList;
	List<Explore> exploreList;
	List<Raze> razeList;
	List<LevelUpRace> levelUpRaceList;
	List<Action> otherActionsList;

	@Override
	public int compareTo(Turn pTurn) {

		return (pTurn.number.compareTo(this.number));
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}

	@Override
	public int hashCode() {

		return super.hashCode();
	}

	public boolean isMilitaryOnly() {

		return !trainMilitaryList.isEmpty() && buildList.isEmpty() && assignScientistsList.isEmpty() && cancelResearchList.isEmpty() && changeStateList.isEmpty() && exploreList.isEmpty()
				&& razeList.isEmpty();
	}

}
