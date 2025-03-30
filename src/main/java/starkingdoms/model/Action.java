package starkingdoms.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Action {

	private String name;
	private String type;
	private int number;
	private int turn;
}
