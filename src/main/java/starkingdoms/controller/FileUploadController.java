package starkingdoms.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import starkingdoms.model.Action;
import starkingdoms.model.AssignScientists;
import starkingdoms.model.Build;
import starkingdoms.model.CancelResearch;
import starkingdoms.model.ChangeState;
import starkingdoms.model.Explore;
import starkingdoms.model.Raze;
import starkingdoms.model.TrainMilitary;
import starkingdoms.model.Turn;

@Controller
public class FileUploadController {

	private static final String COMMADELIMITER = ",";

	@GetMapping("/uploadForm")
	public String listUploadedFiles(Model model) throws IOException {

		return "uploadForm";
	}

	@PostMapping("/uploadForm")
	public String handleFileUpload(MultipartFile file, Model model) {

		List<List<String>> records = parse(file);
		List<Action> actions = mapToActions(records);
		List<Turn> turns = mapToTurns(actions);
		Collections.sort(turns);
		List<Turn> compressedTurns = compress(turns);

		model.addAttribute("turns", compressedTurns);
		return "uploadForm";
	}

	private List<Turn> mapToTurns(List<Action> actions) {

		Map<Integer, List<Action>> turnMap = actions.stream().collect(Collectors.groupingBy(Action::getTurn));

		List<Turn> turns = new ArrayList<>();
		for (Map.Entry<Integer, List<Action>> entry : turnMap.entrySet()) {

			Turn turn = new Turn();
			turn.setNumber(entry.getKey());

			List<Action> list = entry.getValue();

			ArrayList<AssignScientists> assignScientistslist = new ArrayList<>(list.stream().filter(AssignScientists.class::isInstance).map(AssignScientists.class::cast).toList());
			Collections.sort(assignScientistslist);
			turn.setAssignScientistsList(assignScientistslist);

			ArrayList<Build> buildList = new ArrayList<>(list.stream().filter(Build.class::isInstance).map(Build.class::cast).toList());
			Collections.sort(buildList);
			turn.setBuildList(buildList);

			ArrayList<CancelResearch> cancelResearchList = new ArrayList<>(list.stream().filter(CancelResearch.class::isInstance).map(CancelResearch.class::cast).toList());
			Collections.sort(cancelResearchList);
			turn.setCancelResearchList(cancelResearchList);

			ArrayList<ChangeState> changeStateList = new ArrayList<>(list.stream().filter(ChangeState.class::isInstance).map(ChangeState.class::cast).toList());
			Collections.sort(changeStateList);
			turn.setChangeStateList(changeStateList);

			ArrayList<Explore> exploreList = new ArrayList<>(list.stream().filter(Explore.class::isInstance).map(Explore.class::cast).toList());
			Collections.sort(exploreList);
			turn.setExploreList(exploreList);

			ArrayList<Raze> razeList = new ArrayList<>(list.stream().filter(Raze.class::isInstance).map(Raze.class::cast).toList());
			Collections.sort(razeList);
			turn.setRazeList(razeList);

			ArrayList<TrainMilitary> trainMilitaryList = new ArrayList<>(list.stream().filter(TrainMilitary.class::isInstance).map(TrainMilitary.class::cast).toList());
			Collections.sort(trainMilitaryList);
			turn.setTrainMilitaryList(trainMilitaryList);

			turns.add(turn);
		}
		return turns;
	}

	private List<Action> mapToActions(List<List<String>> records) {

		List<Action> actions = new ArrayList<>();
		for (List<String> list : records) {
			if (list.get(2).equals("Train Military")) {
				actions.add(newTrainMilitary(list));
			} else if (list.get(2).equals("Raze")) {
				actions.add(newRaze(list));
			} else if (list.get(2).equals("Change State")) {
				actions.add(newChangeState(list));
			} else if (list.get(2).equals("Explore")) {
				actions.add(newExplore(list));
			} else if (list.get(2).equals("Build")) {
				actions.add(newBuild(list));
			} else if (list.get(2).equals("Assign Scientists")) {
				actions.add(newAssignScientists(list));
			} else if (list.get(2).equals("Cancel Research")) {
				actions.add(newCancelResearch(list));
			}
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
				String[] values = line.split(COMMADELIMITER, -1);
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
			} else if (currentTurn != null && turn.isMilitaryOnly() && haveSameMilitaryTypes(currentTurn, turn)) {
				compressTurns(currentTurn, turn);
			} else {
				// turn.isMilitaryOnly() && !haveSameMilitaryTypes(currentTurn, turn)
				results.add(currentTurn);
				currentTurn = turn;
			}
		}
		return results;
	}

	private boolean haveSameMilitaryTypes(Turn currentTurn, Turn turn) {

		List<String> currentTurnTypes = currentTurn.getTrainMilitaryList().stream().map(Action::getType).toList();
		HashSet<String> currentTurnTypesSet = new HashSet<>(currentTurnTypes);
		List<String> turnTypes = turn.getTrainMilitaryList().stream().map(Action::getType).toList();
		HashSet<String> turnTypesSet = new HashSet<>(turnTypes);
		return currentTurnTypesSet.equals(turnTypesSet);
	}

	private void compressTurns(Turn currentTurn, Turn turn) {

		currentTurn.setTillNumber(turn.getNumber());
		
		compressMilitaryofType(currentTurn.getTrainMilitaryList(), turn.getTrainMilitaryList(), "Soldiers");
		compressMilitaryofType(currentTurn.getTrainMilitaryList(), turn.getTrainMilitaryList(), "Laser Dragoons");
		compressMilitaryofType(currentTurn.getTrainMilitaryList(), turn.getTrainMilitaryList(), "Fighters");
		compressMilitaryofType(currentTurn.getTrainMilitaryList(), turn.getTrainMilitaryList(), "Scientists");

	}

	private void compressMilitaryofType(List<TrainMilitary> trainMilitaryList, List<TrainMilitary> trainMilitaryList2, String type) {

		List<TrainMilitary> currentTurnSoldierslist = trainMilitaryList.stream().filter(m -> m.getType().equals(type)).toList();
		List<TrainMilitary> turnSoldierslist = trainMilitaryList2.stream().filter(m -> m.getType().equals(type)).toList();
		if (currentTurnSoldierslist.size() == 1 && currentTurnSoldierslist.size() == turnSoldierslist.size()) {
			TrainMilitary trainMilitary = currentTurnSoldierslist.get(0);
			trainMilitaryList.remove(trainMilitary);
			String newNumber = trainMilitary.getNumber() + "," + turnSoldierslist.get(0).getNumber();
			trainMilitary.setNumber(newNumber);
			trainMilitaryList.add(trainMilitary);
		}
	}

	private Action newCancelResearch(List<String> list) {

		CancelResearch action = new CancelResearch();
		action.setType(list.get(3).trim());
		action.setTurn(Integer.valueOf(list.get(1)));
		action.setLine(Integer.valueOf(list.get(5)));
		return action;
	}

	private Action newAssignScientists(List<String> list) {

		AssignScientists action = new AssignScientists();
		action.setType(list.get(3).trim());
		action.setNumber(list.get(4));
		action.setTurn(Integer.valueOf(list.get(1)));
		action.setLine(Integer.valueOf(list.get(5)));
		return action;
	}

	private Action newBuild(List<String> list) {

		Build action = new Build();
		action.setType(list.get(3));
		action.setNumber(list.get(4));
		action.setTurn(Integer.valueOf(list.get(1)));
		action.setLine(Integer.valueOf(list.get(5)));
		return action;
	}

	private Action newExplore(List<String> list) {

		Explore action = new Explore();
		action.setNumber(list.get(4));
		action.setTurn(Integer.valueOf(list.get(1)));
		action.setLine(Integer.valueOf(list.get(5)));
		return action;
	}

	private Action newChangeState(List<String> list) {

		ChangeState action = new ChangeState();
		action.setType(list.get(3));
		action.setTurn(Integer.valueOf(list.get(1)));
		action.setLine(Integer.valueOf(list.get(5)));
		return action;
	}

	private Action newRaze(List<String> list) {

		Raze action = new Raze();
		action.setType(list.get(3));
		action.setNumber(list.get(4));
		action.setTurn(Integer.valueOf(list.get(1)));
		action.setLine(Integer.valueOf(list.get(5)));
		return action;
	}

	private Action newTrainMilitary(List<String> list) {

		TrainMilitary action = new TrainMilitary();
		action.setType(list.get(3));
		action.setNumber(list.get(4));
		action.setTurn(Integer.valueOf(list.get(1)));
		action.setLine(Integer.valueOf(list.get(5)));
		return action;
	}

}