package starkingdoms.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import starkingdoms.model.Action;

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
				Action action = new Action(list.get(2), list.get(3), Integer.valueOf(list.get(4)), Integer.valueOf(list.get(1)));
				actions.add(action);
			}
		}

		model.addAttribute("actions", actions);

		return "uploadForm";
	}

}