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

	@Override
	public int compareTo(Turn pTurn) {

		return (pTurn.number.compareTo(this.number));
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}
		if (!(o instanceof Turn)) {
			return false;
		}
		Turn other = (Turn) o;
		boolean trainMilitaryListEquals = (this.trainMilitaryList == null && other.trainMilitaryList == null) || (this.trainMilitaryList != null && this.trainMilitaryList.equals(other.trainMilitaryList));
		boolean buildListEquals = (this.buildList == null && other.buildList == null) || (this.buildList != null && this.buildList.equals(other.buildList));
		boolean assignScientistsListEquals = (this.assignScientistsList == null && other.assignScientistsList == null) || (this.assignScientistsList != null && this.assignScientistsList.equals(other.assignScientistsList));
		boolean cancelResearchListEquals = (this.cancelResearchList == null && other.cancelResearchList == null) || (this.cancelResearchList != null && this.cancelResearchList.equals(other.cancelResearchList));
		boolean changeStateListEquals = (this.changeStateList == null && other.changeStateList == null) || (this.changeStateList != null && this.changeStateList.equals(other.changeStateList));
		boolean exploreListEquals = (this.exploreList == null && other.exploreList == null) || (this.exploreList != null && this.exploreList.equals(other.exploreList));
		boolean razeListEquals = (this.razeList == null && other.razeList == null) || (this.razeList != null && this.razeList.equals(other.razeList));
		return trainMilitaryListEquals && buildListEquals && assignScientistsListEquals && cancelResearchListEquals && changeStateListEquals && exploreListEquals && razeListEquals;
	}

}
