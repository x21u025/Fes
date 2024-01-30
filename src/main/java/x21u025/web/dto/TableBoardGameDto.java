package x21u025.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import x21u025.web.entity.BoardGame;
import x21u025.web.entity.Table;
import x21u025.web.entity.TableBoardGame;
import x21u025.web.entity.TableType;


@Data
@NoArgsConstructor
public class TableBoardGameDto {

	private int row;
	private int column;

	private int dx;
	private int dy;

	private int type;
	private int id;
	private String color;

	// 以下SendData
	private BoardGame boardGame;
	private TableType tableType;

	public TableBoardGameDto(Table table) {
		this.row = table.getRow();
		this.column = table.getColumn();
		this.dx = table.getDx();
		this.dy = table.getDy();
		this.tableType = table.getTableType();
		if(tableType != null) {
			this.type = tableType.getId();
			this.color = tableType.getColor();
		} else {
			this.type = 0;
			this.color = table.getColor();
		}
		this.boardGame = null;
		this.id = 0;
	}

	public TableBoardGameDto(Table table, TableBoardGame tableBoardGame) {
		this.row = table.getRow();
		this.column = table.getColumn();
		this.dx = table.getDx();
		this.dy = table.getDy();
		this.tableType = table.getTableType();
		if(tableType != null) {
			this.type = tableType.getId();
		} else {
			this.type = 0;
		}
		this.boardGame = tableBoardGame.getBoardGame();
		this.id = boardGame.getId();
		this.color = boardGame.getColor();
	}


}
