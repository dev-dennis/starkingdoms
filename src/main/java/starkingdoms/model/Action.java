package starkingdoms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Action {

	private String type;
	private int number;
	private int turn;

}
