package x21u025.web.dto;

import java.util.List;

import lombok.Data;
import x21u025.web.entity.BoardGame;

@Data
public class BoardGameListParam {

	private List<BoardGame> bgList;

}
