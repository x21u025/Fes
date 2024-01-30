package x21u025.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import x21u025.web.entity.Table;
import x21u025.web.entity.TableBoardGame;
import x21u025.web.repository.TableBoardGameRepository;
import x21u025.web.repository.TableRepository;

@Service
public class TableService {

	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private TableTypeService tableTypeService;

	@Autowired
	private TableBoardGameRepository tableBoardGameRepository;

	@Value("${app.table.rows}")
	private int rows;
	@Value("${app.table.cols}")
	private int cols;

	public Table[][] getAllTable() {
		List<Table> list = tableRepository.findAll();

		Table[][] tableList = new Table[rows][cols];
		for(int i = 0; i < rows; i++) {
			for(int l = 0; l < cols; l++)
			tableList[i][l] = new Table(0, tableTypeService);
		}
		for(Table t : list) {
			int y = t.getRow();
			int x = t.getColumn();
			int id = t.getTableType().getId();

			for(int a = y; a <= y + t.getDy(); a++) {
				for(int b = x; b <= x + t.getDx(); b++) {
					tableList[a][b] = new Table(id, tableTypeService);
				}
			}
		}
		return tableList;
	}

	public List<List<Table>> getAllTableList() {
		List<Table> list = tableRepository.findAll();

		List<List<Table>> tableList = new ArrayList<List<Table>>();
		for(int a = 0; a < rows; a++) {
			tableList.add(new ArrayList<Table>());
			for(int b = 0; b < cols; b++) {
				tableList.get(a).add(new Table(0, tableTypeService));
			}
		}

		for(Table t : list) {
			int y = t.getRow();
			int x = t.getColumn();
			int id = t.getTableType().getId();

			for(int a = y; a <= y + t.getDy(); a++) {
				for(int b = x; b <= x + t.getDx(); b++) {
					tableList.get(a).get(b).setColor("NONE");
				}
			}
			Table ta = new Table(id, tableTypeService);
			TableBoardGame tableBoardGame = tableBoardGameRepository.findAll()
					.stream().filter(tbg -> tbg.getRow() == t.getRow() && tbg.getColumn() == t.getColumn()).findFirst().orElse(null);

			ta.setRow(t.getRow());
			ta.setColumn(t.getColumn());
			ta.setDx(t.getDx());
			ta.setDy(t.getDy());
			ta.setBoardGame(tableBoardGame != null ? tableBoardGame.getBoardGame() : null);
			tableList.get(y).set(x, ta);
		}

		List<List<Table>> _tableList = new ArrayList<List<Table>>();
		for(int a = 0; a < rows; a++) {
			_tableList.add(new ArrayList<Table>());
			for(int b = 0; b < cols; b++) {
				if(!tableList.get(a).get(b).getColor().equals("NONE")) {
					_tableList.get(a).add(tableList.get(a).get(b));
				}
			}
		}

		return _tableList;
	}

	public void updateAll(List<Table> tableList) {
		tableRepository.deleteAll();
		tableRepository.saveAll(tableList);
	}

}
