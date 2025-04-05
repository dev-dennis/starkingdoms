package starkingdoms.csvupload.types;

public enum TrainMilitaryType {

	SCIENTISTS("Scientists"),

	FIGHTERS("Fighters"),

	LASER_DRAGOONS("Laser Dragoons"),

	SOLDIERS("Soldiers");

	private String type;

	TrainMilitaryType(String type) {

		setType(type);

	}

	public String getType() {

		return type;
	}

	private void setType(String type) {

		this.type = type;
	}

}
