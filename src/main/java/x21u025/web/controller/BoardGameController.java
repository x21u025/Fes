package x21u025.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import x21u025.web.entity.BoardGame;
import x21u025.web.service.BoardGameService;

@Controller
@RequestMapping("/boardgame")
public class BoardGameController {

	@Autowired
	BoardGameService boardGameService;

	@Value("${app.image.root}")
	private String imageRootPath;

	@GetMapping("/{boardGameName}")
	public String boardGameDetail(@PathVariable String boardGameName, Model model) {
		List<BoardGame> bgList = boardGameService.getByEnName(boardGameName);
		if(bgList.size() == 0) return "redirect:/boardgame";
		BoardGame bg = bgList.get(0);

		File file = new File(imageRootPath + boardGameName);
		List<File> pngList = Stream.of(file.listFiles()).filter(f -> {
			try {
				return ImageIO.read(f) != null;
			} catch (IOException e) {}
			return false;
		}).toList();

		if(pngList.size() == 0) return "redirect:/boardgame";

		model.addAttribute("name", bg.getName());
		model.addAttribute("path", boardGameName);
		model.addAttribute("count", pngList.size());
		model.addAttribute("bgList", bgList);
		return "c/boardgamedetail";
	}



}
