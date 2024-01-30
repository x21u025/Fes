package x21u025.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import x21u025.web.Exception.NotStudentException;
import x21u025.web.dto.BoardGameListParam;
import x21u025.web.dto.DetailDto;
import x21u025.web.dto.DetailListParam;
import x21u025.web.dto.MenuListParam;
import x21u025.web.dto.ShortListParam;
import x21u025.web.dto.TableBoardGameDto;
import x21u025.web.dto.TableBoardGameListParam;
import x21u025.web.dto.TableDto;
import x21u025.web.dto.TableListParam;
import x21u025.web.dto.TableTypeListParam;
import x21u025.web.dto.YNChartListParam;
import x21u025.web.entity.BoardGame;
import x21u025.web.entity.Menu;
import x21u025.web.entity.Register;
import x21u025.web.entity.Table;
import x21u025.web.entity.TableBoardGame;
import x21u025.web.entity.TableType;
import x21u025.web.entity.YNChart;
import x21u025.web.service.BoardGameService;
import x21u025.web.service.MenuService;
import x21u025.web.service.RegisterService;
import x21u025.web.service.ShortService;
import x21u025.web.service.TableBoardGameService;
import x21u025.web.service.TableService;
import x21u025.web.service.TableTypeService;
import x21u025.web.service.YNChartService;

@Controller
@RequestMapping("/e")
public class EmployeeController {

	@Autowired
	BoardGameService boardGameService;

	@Autowired
	MenuService menuService;

	@Autowired
	TableService tableService;

	@Autowired
	RegisterService registerService;

	@Autowired
	YNChartService ynChartService;

	@Autowired
	ShortService shortService;

	@Autowired
	TableTypeService tableTypeService;

	@Autowired
	TableBoardGameService tableBoardGameService;


	@GetMapping({"/", ""})
	public String index(@CookieValue(value=OauthController.COOKIE_NAME, required=false, defaultValue = "") String cookie) {
		checkEmployee(cookie);
		return "e/index";
	}

	@GetMapping("/boardgame")
	public String boardGame(@CookieValue(value=OauthController.COOKIE_NAME, required=false, defaultValue = "") String cookie, Model model) {
		checkEmployee(cookie);

		List<BoardGame> bgList = boardGameService.getAll();

		BoardGameListParam bgListParam = new BoardGameListParam();
		bgListParam.setBgList(bgList);

		model.addAttribute("boardGameListParam", bgListParam);
		return "e/boardgame";
	}

	@GetMapping("/table")
	public String table(@CookieValue(value=OauthController.COOKIE_NAME, required=false, defaultValue = "") String cookie, Model model) {
		checkEmployee(cookie);

		Table[][] tableList = tableService.getAllTable();
		List<TableType> tyList = tableTypeService.getAll();

		TableListParam tableListParam = new TableListParam();
		tableListParam.setTableListByTable(tableList);

		model.addAttribute("tyList", tyList);
		model.addAttribute("tableListParam", tableListParam);
		return "e/table";
	}

	@GetMapping("/menu")
	public String menu(@CookieValue(value=OauthController.COOKIE_NAME, required=false, defaultValue = "") String cookie, Model model) {
		checkEmployee(cookie);

		List<Menu> menuList = menuService.getAll();

		MenuListParam menuListParam = new MenuListParam();
		menuListParam.setMenuList(menuList);

		model.addAttribute("menuListParam", menuListParam);
		return "e/menu";
	}

	@GetMapping("/detail")
	public String detail(@CookieValue(value=OauthController.COOKIE_NAME, required=false, defaultValue = "") String cookie, Model model) {
		checkEmployee(cookie);

		List<Register> registerList = registerService.getAll();

		DetailListParam detailListParam = new DetailListParam();
		detailListParam.setDetailList(registerList, menuService);

		model.addAttribute("detailListParam", detailListParam);


		Map<String, DetailDto> groupedData = new LinkedHashMap<>();
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy年MM月dd日 HH");
		DetailDto totalDto = new DetailDto();
		totalDto.setDetailMap(new HashMap<Menu, Integer>());

		for (DetailDto detailDto : detailListParam.getDetailList()) {
			Date createDate = detailDto.getCreateDate();
			if (createDate != null) {
				String key = sdf.format(createDate);
				if (!groupedData.containsKey(key)) {
					DetailDto dto = new DetailDto();
					dto.setDetailMap(new HashMap<Menu, Integer>());
					groupedData.put(key, dto);
				}
				for(Entry<Menu, Integer> entry : detailDto.getDetailMap().entrySet()) {
					Integer value = groupedData.get(key).getDetailMap().get(entry.getKey());
					groupedData.get(key).getDetailMap().put(entry.getKey(), value == null ? entry.getValue() : value + entry.getValue());

					Integer totalValue = totalDto.getDetailMap().get(entry.getKey());
					totalDto.getDetailMap().put(entry.getKey(), totalValue == null ? entry.getValue() : totalValue + entry.getValue());

					groupedData.get(key).setTotal(groupedData.get(key).getTotal() + entry.getValue() * entry.getKey().getPrize());
				}
				totalDto.setTotal(totalDto.getTotal() + detailDto.getTotal());
			}
		}

		groupedData.put("合計", totalDto);
		model.addAttribute("unitTimeMap", groupedData);
		return "e/detail";
	}

	@GetMapping("/yn")
	public String yn(@CookieValue(value=OauthController.COOKIE_NAME, required=false, defaultValue = "") String cookie, Model model) {
		checkEmployee(cookie);

		List<YNChart> ynChartList = ynChartService.getAll();

		YNChartListParam ynChartListParam = new YNChartListParam();
		ynChartListParam.setYnList(ynChartList);

		model.addAttribute("ynChartListParam", ynChartListParam);

		List<BoardGame> bgList = boardGameService.getAll();

		model.addAttribute("bgList", bgList);
		return "e/yn";
	}

	@GetMapping("/short")
	public String shortUrl(@CookieValue(value=OauthController.COOKIE_NAME, required=false, defaultValue = "") String cookie, Model model) {
		checkEmployee(cookie);

		List<x21u025.web.entity.Short> shortList = shortService.getAll();

		ShortListParam shortListParam = new ShortListParam();
		shortListParam.setShortList(shortList);

		model.addAttribute("shortListParam", shortListParam);
		return "e/short";
	}

	@GetMapping("/tabletype")
	public String tableType(@CookieValue(value=OauthController.COOKIE_NAME, required=false, defaultValue = "") String cookie, Model model) {
		checkEmployee(cookie);

		List<TableType> tableTypeList = tableTypeService.getAll();

		TableTypeListParam tableTypeListParam = new TableTypeListParam();
		tableTypeListParam.setTableTypeList(tableTypeList);

		model.addAttribute("tableTypeListParam", tableTypeListParam);
		return "e/tabletype";
	}

	@GetMapping("/tableboardgame")
	public String tableBoardGame(@CookieValue(value=OauthController.COOKIE_NAME, required=false, defaultValue = "") String cookie, Model model) {
		checkEmployee(cookie);

		List<List<TableBoardGameDto>> tableList = tableBoardGameService.getAllTableList();
		List<BoardGame> bgList = boardGameService.getAll();
		List<TableType> tyList = tableTypeService.getAll();

		TableBoardGameListParam tableListParam = new TableBoardGameListParam();
		tableListParam.setTableByList(tableList);

		model.addAttribute("tyList", tyList);
		model.addAttribute("bgList", bgList);
		model.addAttribute("tableListParam", tableListParam);
		return "e/tableboardgame";
	}

	@PostMapping("/boardgame")
	public String editedBoardgame(@Validated @ModelAttribute BoardGameListParam boardGameListParam, BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				if (!errorList.contains(error.getDefaultMessage())) {
					errorList.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("validationError", errorList);
			return "e/boardgame";
		}

		int lastIndex = boardGameListParam.getBgList().size() - 1;
		if(StringUtils.isEmptyOrWhitespace(boardGameListParam.getBgList().get(lastIndex).getName())) {
			boardGameListParam.getBgList().remove(lastIndex);
		}
		boardGameService.updateByBoardGameListParam(boardGameListParam);
		return "redirect:/e/boardgame";
	}

	@PostMapping("/table")
	public String editedTable(@Validated @ModelAttribute TableListParam tableListParam, BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				if (!errorList.contains(error.getDefaultMessage())) {
					errorList.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("validationError", errorList);
			return "e/table";
		}


		ArrayList<Table> tableList = new ArrayList<Table>();

		TableDto[][] tl = tableListParam.getTableList();
		for(int row = 0; row < tl.length; row++) {
			for(int column = 0; column < tl[row].length; column++) {
				int dx = 0;
				int dy = 0;
				// なにか入ってる場合
				if(tl[row][column].getId() != 0) {
					int id = tl[row][column].getId();
					// 横にどこまで同じのが入っているか(dx)
					for(int dc = 1; column + dc < tl[row].length && tl[row][column + dc].getId() == id; dc++) {
						dx = dc;
					}
					// 縦にどこまで同じのが入っているか(dy)
					b: for(int dr = 1; row + dr < tl.length && tl[row + dr][column + dx].getId() == id; dr++) {
						// dx と同じ分同じのが入っているか
						for(int dc = 0; dc <= dx; dc++) {
							if(tl[row + dr][column + dc].getId() != id) break b;
						}
						dy = dr;
					}

					Table table = new Table(tl[row][column].getId(), tableTypeService);
					table.setRow(row);
					table.setColumn(column);
					table.setDx(dx);
					table.setDy(dy);
					tableList.add(table);

					for(int dc = 0; dc <= dx; dc++) {
						for(int dr = 0; dr <= dy; dr++) {
							tl[row + dr][column + dc].setId(0);
						}
					}
					column += dx;
				}
			}
		}
		tableService.updateAll(tableList);
		return "redirect:/e/table";
	}

	@PostMapping("/menu")
	public String editedMenu(@Validated @ModelAttribute MenuListParam menuListParam, BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				if (!errorList.contains(error.getDefaultMessage())) {
					errorList.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("validationError", errorList);
			return "e/menu";
		}

		int lastIndex = menuListParam.getMenuList().size() - 1;
		if(StringUtils.isEmptyOrWhitespace(menuListParam.getMenuList().get(lastIndex).getName())) {
			menuListParam.getMenuList().remove(lastIndex);
		}

		menuService.updateByBoardGameListParam(menuListParam);
		return "redirect:/e/menu";
	}


	@PostMapping("/yn")
	public String editedYN(@Validated @ModelAttribute YNChartListParam ynChartListParam, BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				if (!errorList.contains(error.getDefaultMessage())) {
					errorList.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("validationError", errorList);
			return "e/yn";
		}

		int lastIndex = ynChartListParam.getYnList().size() - 1;
		if(StringUtils.isEmptyOrWhitespace(ynChartListParam.getYnList().get(lastIndex).getText())) {
			ynChartListParam.getYnList().remove(lastIndex);
		}

		ynChartService.updateByYNChartListParam(ynChartListParam);
		return "redirect:/e/yn";
	}

	@PostMapping("/short")
	public String editedShort(@Validated @ModelAttribute ShortListParam shortListParam, BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				if (!errorList.contains(error.getDefaultMessage())) {
					errorList.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("validationError", errorList);
			return "e/short";
		}

		int lastIndex = shortListParam.getShortList().size() - 1;
		if(StringUtils.isEmptyOrWhitespace(shortListParam.getShortList().get(lastIndex).getPath()) || StringUtils.isEmptyOrWhitespace(shortListParam.getShortList().get(lastIndex).getShortUrl())) {
			shortListParam.getShortList().remove(lastIndex);
		}

		shortService.updateByShortListParam(shortListParam);
		return "redirect:/e/short";
	}

	@PostMapping("/tabletype")
	public String editedTableType(@Validated @ModelAttribute TableTypeListParam tableTypeListParam, BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				if (!errorList.contains(error.getDefaultMessage())) {
					errorList.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("validationError", errorList);
			return "e/tabletype";
		}

		int lastIndex = tableTypeListParam.getTableTypeList().size() - 1;
		if(StringUtils.isEmptyOrWhitespace(tableTypeListParam.getTableTypeList().get(lastIndex).getName())) {
			tableTypeListParam.getTableTypeList().remove(lastIndex);
		}

		tableTypeService.updateByBoardGameListParam(tableTypeListParam);
		return "redirect:/e/tabletype";
	}

	@PostMapping("/tableboardgame")
	public String editedTableBoardgame(@Validated @ModelAttribute TableBoardGameListParam tableBoardGameListParam, BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				if (!errorList.contains(error.getDefaultMessage())) {
					errorList.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("validationError", errorList);
			return "e/tableboardgame";
		}

		List<TableBoardGame> list = new ArrayList<TableBoardGame>();
		for(TableBoardGameDto[] row : tableBoardGameListParam.getTableboardGameList()) {
			for(TableBoardGameDto cell : row) {
				if(cell.getId() != 0) list.add(new TableBoardGame(cell, boardGameService));
			}
		}

		tableBoardGameService.updateAll(list);
		return "redirect:/e/tableboardgame";
	}


	/**
	 * ログインしているか
	 * @throws NotStudentException
	 */
	private void checkEmployee(String cookie) throws NotStudentException {
		boolean isLogin = OauthController.isUser(cookie);
		if(!isLogin) {
			throw new NotStudentException("未従業員ログイン");
		}
	}
	/**
	 * Exception発生時の処理メソッド.
	 */
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, HttpServletRequest request) {
		e.printStackTrace();
		if(e instanceof NotStudentException) {
			return "redirect:/oauth/check_login?url=" + request.getRequestURI();
		}
		return "redirect:/";
	}
}
