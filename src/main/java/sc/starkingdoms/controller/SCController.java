package sc.starkingdoms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sc.starkingdoms.game.GameResult;
import sc.starkingdoms.game.OrderException;
import sc.starkingdoms.model.BuildOrder;
import sc.starkingdoms.service.SCService;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class SCController {

    private static final String BUILD_ORDER = "buildOrder";

    private static final String GAME_RESULT = "gameResult";

    private final SCService sCService;

    @GetMapping
    public String getBO(Model model) {

	System.out.println("getBO");

	model.addAttribute(BUILD_ORDER, new BuildOrder());
	model.addAttribute(GAME_RESULT, new GameResult());

	return "sc";
    }

    @PostMapping(value = "/", params = "run")
    public String runBuildOrder(Model model, @Valid @ModelAttribute BuildOrder buildOrder,
	    BindingResult bindingResult) {

	System.out.println("runBuildOrder");

	if (bindingResult.hasErrors()) {
	    System.out.println("hasErrors");
	    model.addAttribute(GAME_RESULT, new GameResult());
	    return "sc";
	}

	return run(model, buildOrder);
    }

    @PostMapping(value = "/", params = "runBestBuildOrder")
    public String runBestBuildOrder(Model model, @ModelAttribute BuildOrder buildOrder) {

	System.out.println("runBestBuildOrder");

	BuildOrder lBestBuildOrder = sCService.getBestBuildOrder();

	return run(model, lBestBuildOrder);
    }

    private String run(Model model, BuildOrder buildOrder) {

	model.addAttribute(BUILD_ORDER, buildOrder);

	GameResult lGameResult;
	try {
	    lGameResult = sCService.runStrategySilent(buildOrder);
	} catch (OrderException e) {
	    lGameResult = new GameResult(0, 0);
	    model.addAttribute("error", e.getMessage());
	}
	model.addAttribute(GAME_RESULT, lGameResult);

	return "sc";
    }

}
