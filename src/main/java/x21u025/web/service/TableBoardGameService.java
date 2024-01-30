package x21u025.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import x21u025.web.dto.TableBoardGameDto;
import x21u025.web.entity.Table;
import x21u025.web.entity.TableBoardGame;
import x21u025.web.repository.TableBoardGameRepository;

@Service
public class TableBoardGameService {

	@Autowired
	private TableBoardGameRepository tableBoardGameRepository;
	@Autowired
	private TableService tableService;

	@Value("${app.table.rows}")
	private int rows;
	@Value("${app.table.cols}")
	private int cols;

	public List<TableBoardGame> getAll() {
		return tableBoardGameRepository.findAll();
	}

	public List<List<TableBoardGameDto>> getAllTableList() {
		// 取得List
		List<TableBoardGame> list = tableBoardGameRepository.findAll();
		List<List<Table>> tableList = tableService.getAllTableList();

		// 登録List
		List<List<TableBoardGameDto>> _list = new ArrayList<List<TableBoardGameDto>>();

		for(List<Table> tList : tableList) {
			List<TableBoardGameDto> tempList = new ArrayList<TableBoardGameDto>();
			for(Table t : tList) {
				TableBoardGame tbg = list.stream().filter(l -> l.getRow() == t.getRow() && l.getColumn() == t.getColumn()).findFirst().orElse(null);
				if(tbg == null) {
					tempList.add(new TableBoardGameDto(t));
					continue;
				}

				tempList.add(new TableBoardGameDto(t, tbg));
			}
			_list.add(tempList);
		}

		return _list;

	}

	public void updateAll(List<TableBoardGame> tableList) {
		tableBoardGameRepository.deleteAll();
		tableBoardGameRepository.saveAll(tableList);
	}

}
