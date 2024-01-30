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
import x21u025.web.entity.pk.TablePK;
import x21u025.web.service.TableTypeService;

@Entity
@jakarta.persistence.Table(name = "m_table")
@IdClass(value = TablePK.class)
@Data
@NoArgsConstructor
public class Table extends AbstractEntity {

	@Id
	@Column(name = "table_row")
	private int row;
	@Id
	@Column(name = "table_column")
	private int column;

	@ManyToOne()
	@JoinColumn(name = "ty_id")
	private TableType tableType;
	@Transient
	private BoardGame boardGame;
	@Transient
	private String color;
	@Transient
	private String borderColor;

	@Column(name = "table_dx")
	private int dx;
	@Column(name = "table_dy")
	private int dy;

	public Table(int ty_id, TableTypeService tableTypeService) {
		try {
			this.tableType = tableTypeService.getById(ty_id);
			this.color = tableType.getColor();
		} catch(Exception e) {
			this.tableType = null;
			this.color = "#FFFFFF";
		}
	}

	public void setBoardGame(BoardGame boardGame) {
		if(boardGame == null) return;

		this.boardGame = boardGame;
		this.borderColor = color;
		this.color = boardGame.getColor();
	}

}
