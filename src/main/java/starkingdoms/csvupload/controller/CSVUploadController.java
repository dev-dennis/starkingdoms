package starkingdoms.csvupload.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import starkingdoms.csvupload.model.Action;
import starkingdoms.csvupload.model.AssignScientists;
import starkingdoms.csvupload.model.Build;
import starkingdoms.csvupload.model.CancelResearch;
import starkingdoms.csvupload.model.ChangeState;
import starkingdoms.csvupload.model.Explore;
import starkingdoms.csvupload.model.LevelUpRace;
import starkingdoms.csvupload.model.Raze;
import starkingdoms.csvupload.model.TrainMilitary;
import starkingdoms.csvupload.model.Turn;
import starkingdoms.csvupload.types.ActionType;
import starkingdoms.csvupload.types.TrainMilitaryType;

@Controller
@SessionAttributes("turns")
public class CSVUploadController {

	private static final String UPLOAD_FORM = "uploadForm";
	private static final String ANSICHT = "ansicht";
	private static final String DELIMITER = ",";

	@GetMapping("/uploadForm")
	public String listUploadedFiles(Model model) {

		return UPLOAD_FORM;
	}

	@PostMapping(value = "/uploadForm", params = "upload")
	public String handleFileUpload(MultipartFile file, Model model) {

		List<List<String>> records = parse(file);
		List<Action> actions = mapToActions(records);
		List<Turn> turns = mapToTurns(actions);
		Collections.sort(turns);
		List<Turn> compressedTurns = compress(turns);

		model.addAttribute("turns", compressedTurns);
		model.addAttribute(ANSICHT, "other");
		return UPLOAD_FORM;
	}

	@PostMapping(value = "/uploadForm", params = "clear")
	public String clear(MultipartFile file, Model model, SessionStatus status) {

		status.setComplete();

		return UPLOAD_FORM;
	}

	@PostMapping(value = "/ansicht", params = "columns")
	public String handleColumns(Model model) {

		model.addAttribute(ANSICHT, "columns");

		return UPLOAD_FORM;
	}

	@PostMapping(value = "/ansicht", params = "other")
	public String handleOther(Model model) {

		model.addAttribute(ANSICHT, "other");

		return UPLOAD_FORM;
	}

	private List<Turn> mapToTurns(List<Action> actions) {

		Map<Integer, List<Action>> turnMap = actions.stream().collect(Collectors.groupingBy(Action::getTurn));

		List<Turn> turns = new ArrayList<>();
		for (Map.Entry<Integer, List<Action>> entry : turnMap.entrySet()) {

			Turn turn = new Turn();
			turn.setNumber(entry.getKey());

			List<Action> list = entry.getValue();
			turn.setAssignScientistsList(filterByType(list, AssignScientists.class));
			turn.setBuildList(filterByType(list, Build.class));
			turn.setCancelResearchList(filterByType(list, CancelResearch.class));
			turn.setChangeStateList(filterByType(list, ChangeState.class));
			turn.setExploreList(filterByType(list, Explore.class));
			turn.setRazeList(filterByType(list, Raze.class));
			turn.setTrainMilitaryList(filterByType(list, TrainMilitary.class));
			turn.setLevelUpRaceList(filterByType(list, LevelUpRace.class));
			createOtherActions(turn);

			turns.add(turn);
		}
		return turns;
	}

	private void createOtherActions(Turn turn) {

		List<Action> otherActionsList = new ArrayList<>();
		otherActionsList.addAll(turn.getAssignScientistsList());
		otherActionsList.addAll(turn.getBuildList());
		otherActionsList.addAll(turn.getCancelResearchList());
		otherActionsList.addAll(turn.getChangeStateList());
		otherActionsList.addAll(turn.getRazeList());
		otherActionsList.addAll(turn.getLevelUpRaceList());
		Collections.sort(otherActionsList);
		turn.setOtherActionsList(otherActionsList);
	}

	private <T extends Action> ArrayList<T> filterByType(List<Action> list, Class<T> className) {

		ArrayList<T> arrayList = new ArrayList<>(list.stream().filter(className::isInstance).map(className::cast).toList());
		Collections.sort(arrayList);
		return arrayList;
	}

	private List<Action> mapToActions(List<List<String>> records) {

		List<Action> actions = new ArrayList<>();
		for (List<String> list : records) {
			String name = list.get(2);
			actions.add(ActionType.fromString(name).createAction(list));
		}
		return actions;
	}

	private List<List<String>> parse(MultipartFile file) {

		List<List<String>> records = new ArrayList<>();
		int counter = 0;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String line;
			while ((line = br.readLine()) != null) {
				counter++;
				String[] values = line.split(DELIMITER, -1);
				List<String> asList = Arrays.asList(values);
				ArrayList<String> arrayList = new ArrayList<>(asList);
				String valueOf = String.valueOf(counter);
				arrayList.add(valueOf);
				records.add(arrayList);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return records;
	}

	private List<Turn> compress(List<Turn> turns) {

		Turn currentTurn = null;
		List<Turn> results = new ArrayList<>();

		for (Turn turn : turns) {

			if (!turn.isMilitaryOnly() && currentTurn == null) {
				results.add(turn);
			} else if (!turn.isMilitaryOnly() && currentTurn != null) {
				results.add(currentTurn);
				results.add(turn);
				currentTurn = null;
			} else if (currentTurn == null && turn.isMilitaryOnly()) {
				currentTurn = turn;
			} else if (currentTurn != null && turn.isMilitaryOnly() && !currentTurn.equalsMilitaryTypes(turn)) {
				results.add(currentTurn);
				currentTurn = turn;
			} else if (currentTurn != null && turn.isMilitaryOnly() && currentTurn.equalsMilitaryTypes(turn)) {
				compressTurns(currentTurn, turn);
			} else {
				throw new IllegalArgumentException("Error compressingt turn: " + turn.getNumber());
			}

		}
		return results;
	}

	private void compressTurns(Turn currentTurn, Turn turn) {

		currentTurn.setTillNumber(turn.getNumber());
		for (TrainMilitaryType type : TrainMilitaryType.values()) {
			compressMilitaryOfType(currentTurn.getTrainMilitaryList(), turn.getTrainMilitaryList(), type);
		}

	}

	private void compressMilitaryOfType(List<TrainMilitary> trainMilitaryList, List<TrainMilitary> trainMilitaryList2, TrainMilitaryType type) {

		List<TrainMilitary> currentTurnSoldierslist = trainMilitaryList.stream().filter(m -> m.getType().equals(type.getType())).toList();
		List<TrainMilitary> turnSoldierslist = trainMilitaryList2.stream().filter(m -> m.getType().equals(type.getType())).toList();
		if (currentTurnSoldierslist.size() == 1 && currentTurnSoldierslist.size() == turnSoldierslist.size()) {
			TrainMilitary trainMilitary = currentTurnSoldierslist.get(0);
			trainMilitaryList.remove(trainMilitary);
			String newNumber = trainMilitary.getNumber() + "," + turnSoldierslist.get(0).getNumber();
			trainMilitary.setNumber(newNumber);
			trainMilitaryList.add(trainMilitary);
		}
	}

}