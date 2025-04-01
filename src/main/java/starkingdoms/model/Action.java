package starkingdoms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Action implements Comparable<Action> {

	private String type;
	private String number;
	private int turn;
	private Integer line;

	@Override
	public int compareTo(Action pAction) {

		return this.line.compareTo(pAction.line);
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}
		if (!(o instanceof Action)) {
			return false;
		}
		Action other = (Action) o;
		return (this.line == null && other.line == null) || (this.line != null && this.line.equals(other.line));
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
