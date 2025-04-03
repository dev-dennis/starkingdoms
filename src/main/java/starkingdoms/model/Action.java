package starkingdoms.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Action implements Comparable<Action> {

	private String type;
	private String number;
	private int turn;
	private Integer line;

	Action(List<String> list) {

		if (list.size() != 6) {
			throw new IllegalArgumentException();
		}

		setTurn(Integer.valueOf(list.get(1)));
		setType(list.get(3).trim());
		setNumber(list.get(4));
		setLine(Integer.valueOf(list.get(5)));
	}

	@Override
	public int compareTo(Action pAction) {

		return this.line.compareTo(pAction.line);
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}

	@Override
	public int hashCode() {

		return super.hashCode();
	}

}
