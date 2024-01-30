package x21u025.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import x21u025.web.service.ShortService;

@Controller
@RequestMapping("/s")
public class ShortController {

	@Autowired
	ShortService shortService;

	@GetMapping("/{su}")
	public String redirect(@PathVariable String su) {
		x21u025.web.entity.Short s = shortService.getByUrl(su);
		if(s == null) return "redirect:/";

		return "redirect:" + s.getPath();
	}

}
