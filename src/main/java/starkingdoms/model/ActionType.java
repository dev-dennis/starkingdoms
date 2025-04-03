package starkingdoms.model;

import java.util.List;

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