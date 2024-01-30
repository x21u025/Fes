package x21u025.web.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import x21u025.web.dto.YNChartDto;
import x21u025.web.dto.YNChartListParam;
import x21u025.web.entity.BoardGame;
import x21u025.web.entity.Menu;
import x21u025.web.entity.Table;
import x21u025.web.entity.YNChart;
import x21u025.web.service.BoardGameService;
import x21u025.web.service.MenuService;
import x21u025.web.service.TableService;
import x21u025.web.service.YNChartService;

@Controller
@RequestMapping("/c")
public class CustomerController {

	@Autowired
	BoardGameService boardGameService;

	@Autowired
	MenuService menuService;

	@Autowired
	TableService tableService;

	@Autowired
	YNChartService ynChartService;


	@GetMapping({"/", ""})
	public String index() {
		return "redirect:/";
	}

	@GetMapping("/boardgame")
	public String boardgame(Model model) {
		List<BoardGame> bgList = boardGameService.getAll();

		model.addAttribute("bgList", bgList);
		return "c/boardgame";
	}

	@GetMapping("/table")
	public String table(Model model) {

		List<List<Table>> tableList = tableService.getAllTableList();
		List<BoardGame> bgList = boardGameService.getAll();

		model.addAttribute("bgList", bgList);
		model.addAttribute("tableList", tableList);
		return "c/table";
	}

	@GetMapping("/menu")
	public String menu(Model model) {
		List<Menu> menuList = menuService.getAll();

		model.addAttribute("menuList", menuList);
		return "c/menu";
	}

	@GetMapping("/yn")
	public String yn(Model model) {
		List<YNChart> ynList = ynChartService.getAll();

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode rootNode = JsonNodeFactory.instance.objectNode();

		for (YNChart ynChart : ynList) {
			ObjectNode chartObject = JsonNodeFactory.instance.objectNode();
			chartObject.put("id", ynChart.getId());
			chartObject.put("text", ynChart.getText());
			// Use sort value as the key
			rootNode.set(String.valueOf(ynChart.getSort()), chartObject);
		}

		try {
			model.addAttribute("chart", objectMapper.writeValueAsString(rootNode));
		} catch (Exception e) {}

		return "c/yn";
	}

	@ResponseBody
	@PostMapping("/yn")
	public String ynConfirm(@RequestParam("yn") String yn) {
		try {
			List<BoardGame> bgList = boardGameService.getAll();
			YNChartListParam ynParam = new YNChartListParam();
			ynParam.setYnList(ynChartService.getAll());
			List<YNChartDto> ynList = ynParam.getYnList();
			Map<Integer, YNChartDto> ynMap = ynList.stream().collect(Collectors.toMap(YNChartDto::getId, ync -> ync));

			Map<Integer, Integer> count = new HashMap<Integer, Integer>();
			for(BoardGame bg : bgList) {
				count.put(bg.getId(), 0);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			Map<Integer, Boolean> dataMap = objectMapper.readValue(yn, Map.class);
			Map<String, Object> selectMap = new HashMap<String, Object>();
			for(Entry<Integer, Boolean> entry : dataMap.entrySet()) {
				YNChartDto yesNo = ynMap.get(Integer.parseInt(entry.getKey() + ""));
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("text", yesNo.getText().replace("\\n", ""));
				map.put("yn", entry.getValue() ? "O" : "X");
				selectMap.put(yesNo.getSort() + "", map);

				if(entry.getValue()) {
					for(int bgid : yesNo.getBgId()) {
						count.put(bgid, count.get(bgid) + yesNo.getWeight());
					}
				} else {
					for(BoardGame bg : bgList) {
						int bgid = bg.getId();
						if(IntStream.of(yesNo.getBgId()).anyMatch(num -> num == bgid)) continue;
						count.put(bgid, count.get(bgid) + yesNo.getWeight());
					}
				}
			}

			List<Map<String, Object>> outputList = new ArrayList<Map<String, Object>>();
			for(BoardGame bg : bgList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", bg.getId());
				map.put("pic", bg.getPic());
				map.put("name", bg.getName());
				map.put("desc", bg.getDesc());
				map.put("player", bg.getPlayer());
				map.put("count", count.get(bg.getId()));
				outputList.add(map);
			}


			AtomicInteger ai = new AtomicInteger();
			Map<Integer, Map<String, Object>> resultMap = outputList.stream()
				.sorted(Comparator.comparingInt(item -> (int) ((Map<String, Object>) item).get("count")).reversed())
				.collect(Collectors.toMap(
					item -> ai.incrementAndGet(),
					item -> item,
					(existing, replacement) -> existing,
					LinkedHashMap::new
				));

			resultMap.put(0, selectMap);

			return new ObjectMapper().writeValueAsString(resultMap);
		} catch (JsonProcessingException e) {
			return "{}";
		}
	}

}
