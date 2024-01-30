package x21u025.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import x21u025.web.dto.TableBoardGameDto;
import x21u025.web.entity.pk.TablePK;
import x21u025.web.service.BoardGameService;

@Entity
@jakarta.persistence.Table(name = "r_table")
@IdClass(value = TablePK.class)
@Data
@NoArgsConstructor
public class TableBoardGame extends AbstractEntity {
	@Id
	@Column(name = "table_row")
	private int row;
	@Id
	@Column(name = "table_column")
	private int column;

	@ManyToOne()
	@JoinColumn(name = "bg_id")
	private BoardGame boardGame;
	@Transient
	private String color;


	public TableBoardGame(TableBoardGameDto cell, BoardGameService boardGameService) {
		this.row = cell.getRow();
		this.column = cell.getColumn();
		this.boardGame = boardGameService.getById(cell.getId());
		this.color = cell.getColor();
	}

}
