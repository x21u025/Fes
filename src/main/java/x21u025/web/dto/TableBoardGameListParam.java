package x21u025.web.dto;

import java.util.List;

import lombok.Data;

@Data
public class TableBoardGameListParam {

	private TableBoardGameDto[][] tableboardGameList;

	public void setTableByList(List<List<TableBoardGameDto>> list) {

		tableboardGameList = new TableBoardGameDto[list.size()][];

		for (int i = 0; i < tableboardGameList.length; i++) {
			List<TableBoardGameDto> innerList = list.get(i);
			tableboardGameList[i] = new TableBoardGameDto[innerList.size()];
			for (int j = 0; j < innerList.size(); j++) {
				tableboardGameList[i][j] = innerList.get(j);
			}
		}
	}

}
