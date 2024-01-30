package x21u025.web.dto;

import lombok.Data;
import x21u025.web.entity.Table;

@Data
public class TableListParam {

	private TableDto[][] tableList;

	public void setTableListByTable(Table[][] table) {
		tableList = new TableDto[table.length][];
		for(int y = 0; y < table.length; y++) {
			tableList[y] = new TableDto[table[y].length];
			for(int x = 0; x < table[y].length; x++) {
				tableList[y][x] = new TableDto();
				tableList[y][x].setId(table[y][x].getTableType() == null ? 0 : table[y][x].getTableType().getId());
				tableList[y][x].setColor(table[y][x].getColor());
			}
		}
	}

}
