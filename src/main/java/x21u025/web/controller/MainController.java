package x21u025.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ua_parser.Client;
import ua_parser.Parser;


@Controller
public class MainController implements ErrorController {

	@GetMapping("/")
	public String index(@RequestHeader("User-Agent") String userAgentString, Model model) {
		Parser uaParser = new Parser();
		Client c = uaParser.parse(userAgentString);
		try {
			if(c.os.family.equals("iOS") && Double.parseDouble(c.os.major + "." + c.os.minor) < 16.5) {
				model.addAttribute("cssnest", "あなたのiOSバージョンが古いため、正常に表示されません。");
			}
		} catch(Exception e) {}
		return "m/index";
	}

	@GetMapping("/boardgame")
	public String cBoardGame() {
		return "forward:c/boardgame";
	}

	@GetMapping("/table")
	public String cTable() {
		return "forward:c/table";
	}

	@GetMapping("/menu")
	public String cMenu() {
		return "forward:c/menu";
	}

	@GetMapping("/yn")
	public String cYN() {
		return "forward:c/yn";
	}


	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping("/error")
	public String error() {
		return "redirect:/";
	}

	@ResponseBody
	@GetMapping("/ios")
	public String getIOSVersion(@RequestHeader("User-Agent") String userAgentString) {
		Parser uaParser = new Parser();
		Client c = uaParser.parse(userAgentString);
		return "iOS Version: " + c.os.major + "." + c.os.minor;
	}

}
