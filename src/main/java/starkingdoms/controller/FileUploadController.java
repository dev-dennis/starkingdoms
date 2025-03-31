package starkingdoms.controller;

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

		List<List<String>> records = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(COMMADELIMITER);
				records.add(Arrays.asList(values));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

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

		Map<Integer, List<Action>> turnMap = actions.stream().collect(Collectors.groupingBy(Action::getTurn));

		List<Turn> turns = new ArrayList<>();
		for (Map.Entry<Integer, List<Action>> entry : turnMap.entrySet()) {

			Turn turn = new Turn();
			turn.setNumber(entry.getKey());

			List<Action> list = entry.getValue();

			List<AssignScientists> assignScientistslist = list.stream().filter(AssignScientists.class::isInstance).map(AssignScientists.class::cast).toList();
			turn.setAssignScientistsList(assignScientistslist);

			List<Build> buildList = list.stream().filter(Build.class::isInstance).map(Build.class::cast).toList();
			turn.setBuildList(buildList);

			List<CancelResearch> cancelResearchList = list.stream().filter(CancelResearch.class::isInstance).map(CancelResearch.class::cast).toList();
			turn.setCancelResearchList(cancelResearchList);

			List<ChangeState> changeStateList = list.stream().filter(ChangeState.class::isInstance).map(ChangeState.class::cast).toList();
			turn.setChangeStateList(changeStateList);

			List<Explore> exploreList = list.stream().filter(Explore.class::isInstance).map(Explore.class::cast).toList();
			turn.setExploreList(exploreList);

			List<Raze> razeList = list.stream().filter(Raze.class::isInstance).map(Raze.class::cast).toList();
			turn.setRazeList(razeList);

			List<TrainMilitary> trainMilitaryList = list.stream().filter(TrainMilitary.class::isInstance).map(TrainMilitary.class::cast).toList();
			turn.setTrainMilitaryList(trainMilitaryList);

			turns.add(turn);
		}

		Collections.sort(turns);

		List<Turn> compressedTurns = compress(turns);

		model.addAttribute("turns", compressedTurns);

		return "uploadForm";
	}

	private List<Turn> compress(List<Turn> turns) {

		Turn currentTurn = null;
		List<Turn> results = new ArrayList<>();

		for (Turn turn : turns) {

			if (currentTurn == null) {
				currentTurn = turn;
			} else if (currentTurn.equals(turn)) {
				currentTurn.setTillNumber(turn.getNumber());
			} else {
				results.add(currentTurn);
				currentTurn = turn;
			}
		}
		results.add(turns.get(turns.size() - 1));
		return results;
	}

	private Action newCancelResearch(List<String> list) {

		CancelResearch action = new CancelResearch();
		action.setType(list.get(3).trim());
		action.setTurn(Integer.valueOf(list.get(1)));
		return action;
	}

	private Action newAssignScientists(List<String> list) {

		AssignScientists action = new AssignScientists();
		action.setType(list.get(3).trim());
		action.setNumber(Integer.valueOf(list.get(4)));
		action.setTurn(Integer.valueOf(list.get(1)));
		return action;
	}

	private Action newBuild(List<String> list) {

		Build action = new Build();
		action.setType(list.get(3));
		action.setNumber(Integer.valueOf(list.get(4)));
		action.setTurn(Integer.valueOf(list.get(1)));
		return action;
	}

	private Action newExplore(List<String> list) {

		Explore action = new Explore();
		action.setNumber(Integer.valueOf(list.get(4)));
		action.setTurn(Integer.valueOf(list.get(1)));
		return action;
	}

	private Action newChangeState(List<String> list) {

		ChangeState action = new ChangeState();
		action.setType(list.get(3));
		action.setTurn(Integer.valueOf(list.get(1)));
		return action;
	}

	private Action newRaze(List<String> list) {

		Raze action = new Raze();
		action.setType(list.get(3));
		action.setNumber(Integer.valueOf(list.get(4)));
		action.setTurn(Integer.valueOf(list.get(1)));
		return action;
	}

	private Action newTrainMilitary(List<String> list) {

		TrainMilitary action = new TrainMilitary();
		action.setType(list.get(3));
		action.setNumber(Integer.valueOf(list.get(4)));
		action.setTurn(Integer.valueOf(list.get(1)));
		return action;
	}

}