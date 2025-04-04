package starkingdoms.types;

import java.util.List;

import starkingdoms.model.csvupload.Action;
import starkingdoms.model.csvupload.AssignScientists;
import starkingdoms.model.csvupload.Build;
import starkingdoms.model.csvupload.CancelResearch;
import starkingdoms.model.csvupload.ChangeState;
import starkingdoms.model.csvupload.Explore;
import starkingdoms.model.csvupload.LevelUpRace;
import starkingdoms.model.csvupload.Raze;
import starkingdoms.model.csvupload.TrainMilitary;

public enum ActionType {

	CANCEL_RESEARCH {
		@Override
		public Action createAction(List<String> list) {

			return new CancelResearch(list);
		}
	},

	BUILD {
		@Override
		public Action createAction(List<String> list) {

			return new Build(list);
		}
	},

	EXPLORE {
		@Override
		public Action createAction(List<String> list) {

			return new Explore(list);
		}
	},

	CHANGE_STATE {
		@Override
		public Action createAction(List<String> list) {

			return new ChangeState(list);
		}
	},

	RAZE {
		@Override
		public Action createAction(List<String> list) {

			return new Raze(list);
		}
	},

	TRAIN_MILITARY {
		@Override
		public Action createAction(List<String> list) {

			return new TrainMilitary(list);
		}
	},

	ASSIGN_SCIENTISTS {

		@Override
		public Action createAction(List<String> list) {

			return new AssignScientists(list);
		}
	},

	LEVEL_UP_RACE {

		@Override
		public Action createAction(List<String> list) {

			return new LevelUpRace(list);
		}
	};

	public abstract Action createAction(List<String> list);

	public static ActionType fromString(String type) {

		switch (type) {
		case "Cancel Research":
			return CANCEL_RESEARCH;
		case "Assign Scientists":
			return ASSIGN_SCIENTISTS;
		case "Build":
			return BUILD;
		case "Explore":
			return EXPLORE;
		case "Change State":
			return CHANGE_STATE;
		case "Raze":
			return RAZE;
		case "Train Military":
			return TRAIN_MILITARY;
		case "Level up Race":
			return LEVEL_UP_RACE;
		default:
			throw new IllegalArgumentException("Unknown type: " + type);
		}
	}
}